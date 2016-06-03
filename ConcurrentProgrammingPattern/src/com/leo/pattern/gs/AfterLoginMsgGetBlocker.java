package com.leo.pattern.gs;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AfterLoginMsgGetBlocker implements Blocker {

	private final Lock lock;

	private final Condition condition;

	private final boolean allowAccess2Lock;

	public AfterLoginMsgGetBlocker(Lock lock) {
		this(lock, true);
	}

	private AfterLoginMsgGetBlocker(Lock lock, boolean allowAccess2Lock) {
		this.lock = lock;
		this.allowAccess2Lock = allowAccess2Lock;
		this.condition = lock.newCondition();
	}

	public AfterLoginMsgGetBlocker() {
		this(false);
	}

	private AfterLoginMsgGetBlocker(boolean allowAccess2Lock) {
		this(new ReentrantLock(), allowAccess2Lock);
	}

	public Lock getLock() {
		if (allowAccess2Lock) {
			return this.lock;
		}
		throw new IllegalStateException("Access to the lock disallowed!");
	}

	@Override
	public <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception {
		lock.lockInterruptibly();
		V result;
		try {
			final StatusMoitor moitor = guardedAction.moitor;
			while (moitor.getStatus() != Contants.LoginStatus.LOGIN_SUCCESS) {
				System.out.println("waiting ");
				condition.await();
			}
			result = guardedAction.call();
			return result;
		} finally {
			lock.lock();
		}
	}

	@Override
	public void signalAfter(Callable<Boolean> stateOperation) throws Exception {
		lock.lockInterruptibly();
		try {
			if (stateOperation.call()) {
				condition.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void signal() throws InterruptedException {
		lock.lockInterruptibly();
		try {
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void broadcastAfter(Callable<Boolean> stateOperation) throws Exception {
		lock.lockInterruptibly();
		try {
			if (stateOperation.call()) {
				condition.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

}
