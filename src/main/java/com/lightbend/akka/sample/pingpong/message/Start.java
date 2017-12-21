package com.lightbend.akka.sample.pingpong.message;

import akka.actor.ActorRef;

public class Start{
	ActorRef pongActor;
	
	

	public Start(ActorRef pongActor) {
		super();
		this.pongActor = pongActor;
	}

	public ActorRef getPongActor() {
		return pongActor;
	}

	public void setPongActor(ActorRef pongActor) {
		this.pongActor = pongActor;
	}
	
}
