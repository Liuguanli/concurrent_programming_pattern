package com.leo.pattern.threadpool;

public abstract class Task implements Runnable {

	private long timeStamp;
	private int priority;

	public Task() {
		super();
	}

	public Task(long timeStamp, int priority) {
		super();
		this.timeStamp = timeStamp;
		this.priority = priority;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
