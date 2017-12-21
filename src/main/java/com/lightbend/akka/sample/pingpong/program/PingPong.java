package com.lightbend.akka.sample.pingpong.program;

import com.lightbend.akka.sample.pingpong.actor.PingActor;
import com.lightbend.akka.sample.pingpong.actor.PongActor;
import com.lightbend.akka.sample.pingpong.message.Start;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class PingPong {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("application", ConfigFactory.load().getConfig("sumsquare"));
        ActorRef pingActor = system.actorOf(Props.create(PingActor.class).withDispatcher("examdispatcher"), "pingActor");
        ActorRef pongActor = system.actorOf(Props.create(PongActor.class).withDispatcher("examdispatcher"), "pongActor");
        pingActor.tell(new Start(pongActor), ActorRef.noSender());


    }
}
