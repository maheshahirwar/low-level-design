package com.task.management.system.observer;

public class ActivityLogger implements TaskObserver {

	@Override
	public void update(String message) {
		System.out.println("[LOGGER]: <TaskObserver> : " + message);
	}

}
