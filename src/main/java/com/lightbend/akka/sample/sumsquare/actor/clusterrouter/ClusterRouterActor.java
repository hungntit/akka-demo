package com.lightbend.akka.sample.sumsquare.actor.clusterrouter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import akka.actor.*;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberUp;
import com.lightbend.akka.sample.sumsquare.actor.router.RouterActor;
import com.lightbend.akka.sample.sumsquare.message.cluster.RouterUp;
import scala.concurrent.duration.Duration;

public class ClusterRouterActor extends RouterActor {
    List<ActorRef> clusterWorkers = new ArrayList<ActorRef>();

    Set<ActorRef> anotherRouters = new HashSet<ActorRef>();

    public ClusterRouterActor(int numWorker, Props props) {
        super(numWorker, props);
        clusterWorkers.addAll(workers);

    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        ActorSystem system = getContext().getSystem();
        Cluster cluster = Cluster.get(system);
        cluster.subscribe(getSelf(), MemberUp.class);

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(MemberUp.class, memberUp -> {
            System.out.println("MemberUp:" + memberUp.member().address());
            this.context().system().scheduler().scheduleOnce(Duration.create(2, TimeUnit.SECONDS),
                    new Runnable() {
                        public void run() {
                            String memberRouterAddress = getSelf().path().toStringWithAddress(memberUp.member().address());
                            context().actorSelection(memberRouterAddress).tell(new RouterUp(getSelf(), workers), getSelf());
                        }
                    }, this.context().system().dispatcher());
            })
          .match(Terminated.class, terminated -> this.clusterWorkers.remove(terminated.actor())).match(RouterUp.class, routerUp -> {

                    if (!routerUp.getRouter().equals(this.getSelf()) || routerUp.getWorkers().size() == 0) {
                        anotherRouters.add(routerUp.getRouter());
                        clusterWorkers.addAll(routerUp.getWorkers());
                        for (ActorRef worker : routerUp.getWorkers()) {
                            this.getContext().watch(worker);
                        }

                        System.out.println("New Worker Size:" + clusterWorkers.size());
                    }
                }).build().orElse(super.createReceive());
    }

    @Override
    protected ActorRef getWorker() {
        counter = ++counter % clusterWorkers.size();
        return clusterWorkers.get(counter);
    }

}
