package com.lightbend.akka.sample.pingpong.actor;

import akka.actor.AbstractActor;
import com.lightbend.akka.sample.pingpong.message.Ping;
import com.lightbend.akka.sample.pingpong.message.Pong;
import com.lightbend.akka.sample.pingpong.message.Start;


public class PingActor extends AbstractActor{


	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Pong.class, pong -> {
			if(pong.getOrder() < 100000){
				getSender().tell(new Ping(pong.getOrder() +1), getSelf());
			}
			System.out.println("Received pong "+ pong.getOrder());
		}).match(Start.class, start -> start.getPongActor().tell(new Ping(1), getSelf())).build();
	}
}
