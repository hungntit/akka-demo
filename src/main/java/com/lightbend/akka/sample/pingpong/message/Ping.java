package com.lightbend.akka.sample.pingpong.message;

import java.io.Serializable;

public class Ping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2418125570444701716L;
	int order;
	
	public Ping(int order) {
		super();
		this.order = order;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
}
