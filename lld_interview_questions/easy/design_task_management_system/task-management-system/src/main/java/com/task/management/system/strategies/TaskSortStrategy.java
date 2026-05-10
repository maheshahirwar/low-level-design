package com.task.management.system.strategies;

import java.util.List;

import com.task.management.system.entities.Task;

public interface TaskSortStrategy {
	void sort(List<Task> tasks); 
}
