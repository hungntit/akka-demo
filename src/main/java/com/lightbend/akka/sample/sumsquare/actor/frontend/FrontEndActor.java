package com.lightbend.akka.sample.sumsquare.actor.frontend;

import akka.actor.AbstractActor;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SumSquareResp;

public class FrontEndActor extends AbstractActor {


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SumSquareResp.class, rs -> System.out.println(rs.getInput() + "=> " + rs.getResult())).build();
    }
}
