package com.lightbend.akka.sample.sumsquare.message.sumsquare;

import java.io.Serializable;

public class SumSquareResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 414276213049177703L;
	private long result;
	private int input;
	public SumSquareResp(int input, long result) {
		super();
		this.input = input;
		this.result = result;
	}
	public long getResult() {
		return result;
	}
	public void setResult(long result) {
		this.result = result;
	}
	public int getInput() {
		return input;
	}
	public void setInput(int input) {
		this.input = input;
	}
	
	
}
