package com.leo.pattern.gs;

import java.util.concurrent.Callable;

public abstract class GuardedAction<V> implements Callable<V> {
	protected final StatusMoitor moitor;
	
	public GuardedAction(StatusMoitor moitor) {
		this.moitor = moitor;
	}

}
