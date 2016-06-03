package com.leo.pattern.gs;

import java.util.concurrent.Callable;

public class LoginAgent {
	private int loginStatus = Contants.LoginStatus.LOGIN_INIT;
	
	StatusMoitor moitor = new StatusMoitor() {
		
		@Override
		public int getStatus() {
			return loginStatus;
		}
	};
	
	private final Blocker blocker = new AfterLoginMsgGetBlocker();
	
	public void init() {
		Thread LoginThread = new Thread(new LoginTask());
		LoginThread.start();
	}
	
	public void beginMsgGet() {
		System.out.println("begin to get msg...");
	}
	
	public void sendAlarmToBlocker() {
		try {
			blocker.callWithGuard(createGuardedAction());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GuardedAction<Void> createGuardedAction() {
		GuardedAction<Void> guardedAction = new GuardedAction<Void>(moitor) {
			@Override
			public Void call() throws Exception {
				beginMsgGet();
				return null;
			}
		};
		return guardedAction;
	}
	
	private class LoginTask implements Runnable {
		@Override
		public void run() {
			loginStatus = Contants.LoginStatus.LOGIN_ING;
			// 
			System.out.println("login ing……");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				;
			}

			onLogined();
		}
	}
	
	private void onLogined() {
		loginStatus = Contants.LoginStatus.LOGIN_SUCCESS;
		try {
			blocker.signalAfter(new Callable<Boolean>() {
				
				@Override
				public Boolean call() throws Exception {
					return true;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
}
