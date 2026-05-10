package com.task.management.system.strategies;

import java.util.Comparator;
import java.util.List;

import com.task.management.system.entities.Task;

public class AssigneeTaskSortStrategy implements TaskSortStrategy {

	@Override
	public void sort(List<Task> tasks) {
		tasks.sort(Comparator.comparing(task -> task.getAssignee() != null ? task.getAssignee().getName() : ""));
	}

}
