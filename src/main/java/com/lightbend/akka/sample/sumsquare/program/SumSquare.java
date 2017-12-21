package com.lightbend.akka.sample.sumsquare.program;

import com.lightbend.akka.sample.sumsquare.actor.frontend.FrontEndActor;
import com.lightbend.akka.sample.sumsquare.actor.manager.SumSquareManagerActor;
import com.lightbend.akka.sample.sumsquare.actor.router.RouterActor;
import com.lightbend.akka.sample.sumsquare.actor.worker.SquareActor;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SumSquareReq;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SumSquare {
    public static void main(String[] args) throws InterruptedException {

        ActorSystem system = ActorSystem.create("application", ConfigFactory.load().getConfig("sumsquare"));
        ActorRef frontend = system.actorOf(Props.create(FrontEndActor.class).withDispatcher("examdispatcher"), "frontend");

        ActorRef workers = system.actorOf(Props.create(RouterActor.class, 50,
                Props.create(SquareActor.class).withDispatcher("examdispatcher")).withDispatcher("examdispatcher"), "workers");
        ActorRef manager = system.actorOf(Props.create(SumSquareManagerActor.class, workers).withDispatcher("examdispatcher"), "managers");


        long begin = System.currentTimeMillis();
        for (int i = 1; i <= 1000; i++)
            manager.tell(new SumSquareReq(i), frontend);

        System.out.println("end" + (System.currentTimeMillis() - begin));

    }
}
