package com.task.management.system.entities;

import java.util.List;

import com.task.management.system.enums.TaskPriority;
import com.task.management.system.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Task {
	private Long taskId;
	private String title;
	private String description;
	private TaskStatus status;
	private TaskPriority priority;
	private User assignee;
	
	private List<Comment> comments;

	public Task(String title, String description) {
		this.title = title;
		this.description = description;
		this.status = TaskStatus.TODO; // Default status
	}

}
