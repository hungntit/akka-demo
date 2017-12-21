package com.lightbend.akka.sample.sumsquare.actor.router;

import java.util.ArrayList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class RouterActor extends AbstractActor {
    protected List<ActorRef> workers = new ArrayList<ActorRef>();
    protected int counter = 0;

    public RouterActor(int numWorker, Props props) {

        for (int i = 0; i < numWorker; i++) {
            ActorRef workerRef = this.getContext().actorOf(props);
            workers.add(workerRef);
        }
    }

    protected ActorRef getWorker() {
        counter = ++counter % workers.size();
        return workers.get(counter);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(msg -> {
                            ActorRef worker = getWorker();
//                            System.out.println("Receive message " + msg.getClass().getCanonicalName() + ", send to worker" + worker.path());
                            worker.tell(msg, getSender());
                        }
                ).build();
    }
}
