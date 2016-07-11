package com.leo.pattern.threadpool;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomedThreadPool extends ThreadPoolExecutor {

	public CustomedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	public CustomedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	public void execute(Runnable command) {
		super.execute(command);
	}

	@Override
	public void shutdown() {
		super.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return super.shutdownNow();
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		System.out.println("------------beforeExecute------------");
		System.err.println(((Task) r).getPriority());
		// 每次执行之前查看线程池的运行状态
		// this.collectThreadPoolInfo();
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		System.out.println("------------afterExecute------------");
		// 每次执行之后查看线程池的运行状态
		// this.collectThreadPoolInfo();
	}

	@Override
	protected void terminated() {
		super.terminated();
	}

	public void collectThreadPoolInfo() {
		// the approximate number of threads that are actively executing tasks.
		System.out.println();
		System.out.println("ActiveCount->" + getActiveCount());
		// the current number of threads in the pool
		System.out.println("PoolSize->" + getPoolSize());
		// the largest number of threads that have ever simultaneously been in
		// the pool
		System.out.println("LargestPoolSize->" + getLargestPoolSize());
		// the core number of threads
		System.out.println("CorePoolSize->" + getCorePoolSize());
		// the maximum allowed number of threads
		System.out.println("MaximumPoolSize->" + getMaximumPoolSize());
		// the approximate total number of tasks that have ever been scheduled
		// for execution
		System.out.println("TaskCount->" + getTaskCount());
		System.out.println();
	}

	public void addCoreSize() {
		if (getCorePoolSize() < getActiveCount() && getCorePoolSize() < getMaximumPoolSize()) {
			setCorePoolSize(getCorePoolSize() + 1);
		}
	}

	public int threshold = 1;

	private void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public void shrinkCoreSize() {
		if (getCorePoolSize() > getActiveCount() + threshold) {
			setCorePoolSize(getActiveCount() + threshold);
		}
	}

}
