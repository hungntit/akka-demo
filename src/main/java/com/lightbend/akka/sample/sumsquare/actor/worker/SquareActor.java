package com.lightbend.akka.sample.sumsquare.actor.worker;

import akka.actor.AbstractActor;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SquareReq;

public class SquareActor extends AbstractActor {

    private Long square(int n) {
        return (long) n * n;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SquareReq.class, squareReq -> {
//                    System.out.println("Receive SquareReq with " + squareReq.getNumber() + ", calculate and send back to " + getSender().path());
                    getSender().tell(square(squareReq.getNumber()), getSelf());
                })
                .build();
    }
}
