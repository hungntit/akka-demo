package com.lightbend.akka.sample.pingpong.message;

import java.io.Serializable;

public class Pong implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3087605380308341584L;
	private int order;
	public Pong(int order){
		this.order  = order;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
}
