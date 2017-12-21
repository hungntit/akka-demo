package com.lightbend.akka.sample.sumsquare.message.sumsquare;

import java.io.Serializable;

public class SumSquareReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5548258301538396485L;
	private int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public SumSquareReq(int number) {
		super();
		this.number = number;
	}
}
