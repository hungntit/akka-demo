package com.lightbend.akka.sample.sumsquare.actor.manager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SumSquareReq;

public class SumSquareManagerActor extends AbstractActor {

    private ActorRef routerWorker;

    public SumSquareManagerActor(ActorRef routerWorker) {
        this.routerWorker = routerWorker;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SumSquareReq.class, msg -> {
            ActorRef taskActor = this.context().actorOf(Props.create(SumSquareTaskActor.class, this.routerWorker));
            taskActor.tell(msg, getSender());
        }).build();
    }
}
