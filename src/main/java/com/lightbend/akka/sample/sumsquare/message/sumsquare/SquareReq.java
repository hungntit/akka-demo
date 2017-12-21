package com.lightbend.akka.sample.sumsquare.message.sumsquare;

import java.io.Serializable;

public class SquareReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4037339668100378149L;
	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public SquareReq(int number) {
		super();
		this.number = number;
	}
	 
}
