package com.lightbend.akka.sample.pingpong.actor;

import akka.actor.AbstractActor;
import com.lightbend.akka.sample.pingpong.message.Ping;
import com.lightbend.akka.sample.pingpong.message.Pong;
import com.lightbend.akka.sample.pingpong.message.Start;


public class PongActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Ping.class, ping -> {
            getSender().tell(new Pong(ping.getOrder()), getSelf());
            System.out.println("Received ping " + ping.getOrder());
        }).match(Start.class, start -> start.getPongActor().tell(new Ping(1), getSelf())).build();
    }
}
