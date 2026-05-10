package com.task.management.system.entities;

import java.util.ArrayList;
import java.util.List;

import com.task.management.system.enums.TaskPriority;
import com.task.management.system.enums.TaskStatus;
import com.task.management.system.observer.TaskObserver;

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
	
	private List<TaskObserver> observers;

	public Task(String title, String description) {
		this.title = title;
		this.description = description;
		this.status = TaskStatus.TODO; // Default status
	}
	
	 // --- Observer Pattern Methods ---
	public void addObserver(TaskObserver observer) {
		if(observers == null) {
			observers = new ArrayList<>();
		}
		observers.add(observer);
	}
    public void removeObserver(TaskObserver observer) { observers.remove(observer); }
    public void notifyObservers(String message) {
        for (TaskObserver observer : observers) {
            observer.update(message);
        }
    }

}
