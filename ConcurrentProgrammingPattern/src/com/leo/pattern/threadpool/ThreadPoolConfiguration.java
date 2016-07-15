package com.leo.pattern.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfiguration {
	/**
	 * 是否是公平的，即当优先级相同的时候是否按照先后顺序来执行
	 */
	public boolean isFair = false;
	
	public int coreSize;
	public int maxSize;
	public long keepAliveTime;
	public TimeUnit unit;
	public BlockingQueue<Runnable> workQueue;
	public ThreadFactory threadFactory;
	public RejectedExecutionHandler handler;
}
