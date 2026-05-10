package com.task.management.system.exceptions;

public class TaskNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(Long taskId) {
		super("Task with ID " + taskId + " not found.");
	}

}
