package com.lightbend.akka.sample.sumsquare.program;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.lightbend.akka.sample.sumsquare.actor.clusterrouter.ClusterRouterActor;
import com.lightbend.akka.sample.sumsquare.actor.frontend.FrontEndActor;
import com.lightbend.akka.sample.sumsquare.actor.manager.SumSquareManagerActor;
import com.lightbend.akka.sample.sumsquare.actor.worker.SquareActor;
import com.lightbend.akka.sample.sumsquare.message.sumsquare.SumSquareReq;
import com.typesafe.config.ConfigFactory;

public class ClusterFrontend {
	 public static void main(String[] args) throws InterruptedException {
		
		ActorSystem system = ActorSystem.create("application",ConfigFactory.load().getConfig("clustersumsquare"));
		ActorRef frontend = system.actorOf(Props.create(FrontEndActor.class).withDispatcher("examdispatcher"), "frontend");
		
		ActorRef workers = system.actorOf(Props.create(ClusterRouterActor.class,0,Props.create(SquareActor.class).withDispatcher("examdispatcher")).withDispatcher("examdispatcher"),"workers");
		ActorRef manager = system.actorOf(Props.create(SumSquareManagerActor.class,workers).withDispatcher("examdispatcher"),"managers");
		Thread.sleep(10000);
		
		long begin   = System.currentTimeMillis();
		for(int i =1;i <= 1000;i++)
			manager.tell(new SumSquareReq(i), frontend);
		
		System.out.println("end"+ (System.currentTimeMillis() - begin));
		
	}
}
