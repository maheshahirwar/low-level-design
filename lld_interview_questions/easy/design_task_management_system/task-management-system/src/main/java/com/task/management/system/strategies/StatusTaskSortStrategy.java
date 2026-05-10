package com.task.management.system.strategies;

import java.util.List;

import com.task.management.system.entities.Task;

public class StatusTaskSortStrategy implements TaskSortStrategy {

	@Override
	public void sort(List<Task> tasks) {
		tasks.sort((t1, t2) -> t1.getStatus().compareTo(t2.getStatus()));
	}

}
