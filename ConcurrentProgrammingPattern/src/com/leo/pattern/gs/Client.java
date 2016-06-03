package com.leo.pattern.gs;

public class Client {
	public static void main(String[] args) {
		LoginAgent agent = new LoginAgent();
		agent.init();
		agent.sendAlarmToBlocker();
	}
}
