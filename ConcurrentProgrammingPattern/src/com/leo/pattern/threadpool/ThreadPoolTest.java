package com.leo.pattern.threadpool;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	private static final int CORE_POOL_SIZE = 3;
	private static final int MAX_POOL_SIZE = 5;
	private static final int KEEP_ALIVE_TIME = 5;
	private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

	private static final int QUEUE_SIZE = 100;

	private static ThreadPoolExecutor threadPoolExecutor;

	public static void main(String[] args) {

		// BlockingQueue<Runnable> blockingQueue = new
		// LinkedBlockingQueue<Runnable>();
		// 使用比较器 来实现优先级的判断
		Comparator<Runnable> comparator = new CustonComparator();
		BlockingQueue<Runnable> blockingQueue = new PriorityBlockingQueue<>(QUEUE_SIZE, comparator);

		RejectedExecutionHandler handler = new CustomRejectedExecutionHandler();
		ThreadFactory defaultThreadFactory = new TaskThreadFactory();
		threadPoolExecutor = new CustomedThreadPool(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT,
				blockingQueue, defaultThreadFactory, handler);
		// defaultThreadFactory也可以这样设置
		threadPoolExecutor.setThreadFactory(defaultThreadFactory);
		testThreadPool();
	}

	/**
	 * 创建100个任务 任务的优先级 是0101这样交替产生的，且以当前时间作为时间戳。
	 */
	public static void testThreadPool() {
		for (int i = 0; i < 100; i++) {
			threadPoolExecutor.execute(new Task(System.currentTimeMillis(), i % 2) {

				@Override
				public void run() {
					int index = 1;
					while (index < Integer.MAX_VALUE - 1) {
						index++;
					}
				}
			});
		}
	}

}
