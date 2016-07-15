package com.leo.pattern.threadpool;

public class ThreadProxy extends Task {

	private Runnable runnable;

	public ThreadProxy(Runnable runnable) {
		super();
		this.runnable = runnable;
	}

	public ThreadProxy(int timeStamp, int priority, Runnable runnable) {
		super(timeStamp, priority);
		this.runnable = runnable;
	}

	@Override
	public void run() {
//		Looper looper = Looper.myLooper();
//        if (looper == null) {
//            Looper.prepare();
//        }
//        Looper.loop();
		System.out.println("变成Looper线程");
		runnable.run();
	}

}
