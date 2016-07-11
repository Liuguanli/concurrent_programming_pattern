package com.leo.pattern.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这里new的线程与我们 提交的任务是没有多大关系的。
 * 
 * @author liuguanli
 *
 */
public class TaskThreadFactory implements ThreadFactory {

	private final ThreadGroup group;
	private final String namePrefix;
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	public TaskThreadFactory() {
		SecurityManager manager = System.getSecurityManager();
		group = (manager != null) ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);

		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				// 这里的输出异常信息
			}
		});

		return t;
	}

}
