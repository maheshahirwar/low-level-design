package com.task.management.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.task.management.system.entities.Comment;
import com.task.management.system.entities.Task;
import com.task.management.system.entities.User;
import com.task.management.system.enums.TaskPriority;
import com.task.management.system.enums.TaskStatus;
import com.task.management.system.exceptions.TaskNotFoundException;

public class TaskManager {

	private Map<Long, Task> tasks = new ConcurrentHashMap<>();
	
	private Map<String, User> users = new ConcurrentHashMap<>();

	AtomicLong taskIdGenerator = new AtomicLong(1);

	public Task createTask(Task task) {
		task.setTaskId(taskIdGenerator.getAndIncrement());
		tasks.computeIfAbsent(task.getTaskId(), id -> task);
		return task;
	}
	
	public User createUser(String name, String email) {
		User user = User.builder()
		.name(name)
		.email(email)
		.build();
		users.computeIfAbsent(email, e -> user);
		return user;
	}
	
	public User getUserByEmail(String email) {
		return users.get(email);
	}

	public List<Task> getAllTasks() {
		return tasks.values().stream().toList();
	}

	public String assignTask(long taskId, User assignee) throws TaskNotFoundException {
		Task task = tasks.get(taskId);
		if (task == null) {
			throw new TaskNotFoundException(taskId);
		}
		task.setAssignee(assignee);
		tasks.put(taskId, task);
		return "Task assigned to " + assignee.getName() + " successfully.";
	}

	public String updateTaskStatus(Long taskId, TaskStatus newStatus) throws TaskNotFoundException {
		Task task = tasks.get(taskId);
		if (task == null) {
			throw new TaskNotFoundException(taskId);
		}
		task.setStatus(newStatus);
		tasks.put(taskId, task);
		return "Task status updated successfully.";
	}

	public String addCommentToTask(Long taskId, Comment comment) throws TaskNotFoundException {
		Task task = tasks.get(taskId);
		if (task == null) {
			throw new TaskNotFoundException(taskId);
		}
		List<Comment> comments = task.getComments();
		comments = comments == null ? new ArrayList<>() : comments;
		comments.add(comment);
		task.setComments(comments);
		tasks.put(taskId, task);
		return "Comment added to task successfully.";
	}

	public String updateTaskPriority(Long taskId, TaskPriority newPriority) throws TaskNotFoundException {
		Task task = tasks.get(taskId);
		if (task == null) {
			throw new TaskNotFoundException(taskId);
		}
		task.setPriority(newPriority);
		tasks.put(taskId, task);
		return "Task priority updated successfully.";
	}

	public List<Task> getTasksByAssignee(User assignee) {
		return tasks.values().stream()
				.filter(task -> task.getAssignee() != null && task.getAssignee().getEmail().equals(assignee.getEmail()))
				.toList();
	}

	public List<Task> getTasksByStatus(TaskStatus status) {
		return tasks.values().stream().filter(task -> task.getStatus() != null && task.getStatus().equals(status))
				.toList();
	}

	public List<Task> getTasksByPriority(TaskPriority priority) {
		return tasks.values().stream().filter(task -> task.getPriority() != null && task.getPriority().equals(priority))
				.toList();
	}

	public List<User> getAllUsers() {
		return users.values().stream().toList();
	}

	public Task getTaskById(long taskId) {
		return tasks.get(taskId);
	}
}
