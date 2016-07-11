package com.leo.pattern.threadpool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Threadpool implements Executor {

	private static volatile Threadpool threadpool;

	private ThreadPoolExecutor threadPoolExecutor;
	private static final int CORE_POOL_SIZE = 3;
	private static final int MAX_POOL_SIZE = 5;
	private static final int KEEP_ALIVE_TIME = 5;
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
	private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<Runnable>();

	private ThreadFactory defaultThreadFactory;

	private Threadpool() {
		defaultThreadFactory = new TaskThreadFactory();
		threadPoolExecutor = new CustomedThreadPool(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT,
				WORK_QUEUE);
		threadPoolExecutor.setThreadFactory(defaultThreadFactory);
	}

	public void setThreadFactory(ThreadFactory threadFactory) {
		if (threadPoolExecutor != null && threadFactory != null) {
			threadPoolExecutor.setThreadFactory(threadFactory);
		}
	}
	
	public static Threadpool getInstance() {
		if (threadpool == null) {
			threadpool = new Threadpool();
		}
		return threadpool;
	}

	public boolean removeTask(Runnable task) {
		return threadPoolExecutor.remove(task);
	}

	public void closeThreadPool() {
		threadPoolExecutor.shutdown();
	}

	public List<Runnable> closeThreadPoolImmediately() {
		return threadPoolExecutor.shutdownNow();
	}

	public void adjustThreadPool() {
		if (threadPoolExecutor.getTaskCount() > threadPoolExecutor.getActiveCount()) {
			threadPoolExecutor.setCorePoolSize(threadPoolExecutor.getCorePoolSize() + 1);
		}
	}

	@Override
	public void execute(Runnable command) {
		threadPoolExecutor.submit(command);
	}

	public Object execute(Callable<?> callable) {
		Future<?> future = threadPoolExecutor.submit(callable);
		Object object = null;
		try {
			if (future.isDone()) {
				System.out.println("is done");
			} else {
				System.out.println("is not done");
			}
			object = future.get();
			// 下面的代码在return 之后执行？！
			if (future.isDone()) {
				System.out.println("is done");
			} else {
				System.out.println("is not done");
			}
		} catch (InterruptedException e) {
			System.out.println("interrupted！");
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public void execute(Collection tasks) {
		try {
			threadPoolExecutor.invokeAll(tasks);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
