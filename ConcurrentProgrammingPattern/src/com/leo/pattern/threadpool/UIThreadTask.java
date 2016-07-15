package com.leo.pattern.threadpool;

public abstract class UIThreadTask extends Task{

	public UIThreadTask() {
		super();
	}
	
	public UIThreadTask(int timeStamp, int priority) {
		super(timeStamp,priority);
	}

}
