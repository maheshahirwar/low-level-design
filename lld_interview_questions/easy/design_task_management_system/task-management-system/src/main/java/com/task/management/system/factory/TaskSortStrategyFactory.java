package com.task.management.system.factory;

import com.task.management.system.enums.SortBy;
import com.task.management.system.strategies.AssigneeTaskSortStrategy;
import com.task.management.system.strategies.PriorityTaskSortStrategy;
import com.task.management.system.strategies.StatusTaskSortStrategy;
import com.task.management.system.strategies.TaskSortStrategy;

public class TaskSortStrategyFactory {

	public static TaskSortStrategy getStrategy(SortBy criteria) {
		switch (criteria) {
			case PRIORITY:
				return new PriorityTaskSortStrategy();
			case ASSIGNEE:
				return new AssigneeTaskSortStrategy();
			case STATUS:
				return new StatusTaskSortStrategy();
			default:
				throw new IllegalArgumentException("Invalid sorting criteria: " + criteria);
		}
	}
}
