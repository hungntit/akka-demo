package com.lightbend.akka.sample.sumsquare.message.cluster;

import java.io.Serializable;
import java.util.List;

import akka.actor.ActorRef;

public class RouterUp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4365012321861515705L;
	ActorRef router;
	List<ActorRef> workers;
	public RouterUp(ActorRef router, List<ActorRef> workers) {
		super();
		this.router = router;
		this.workers = workers;
	}
	public ActorRef getRouter() {
		return router;
	}
	public void setRouter(ActorRef router) {
		this.router = router;
	}
	public List<ActorRef> getWorkers() {
		return workers;
	}
	public void setWorkers(List<ActorRef> workers) {
		this.workers = workers;
	}
	
}
