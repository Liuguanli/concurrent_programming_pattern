package com.leo.pattern.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

	/**
	 * @see java.util.concurrent.ThreadPoolExecutor.AbortPolicy  and so on
	 */
	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		// 这里实现自己的策略
	}

}
