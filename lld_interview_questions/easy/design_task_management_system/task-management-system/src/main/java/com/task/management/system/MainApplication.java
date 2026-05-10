package com.task.management.system;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.task.management.system.entities.Comment;
import com.task.management.system.entities.Task;
import com.task.management.system.entities.User;
import com.task.management.system.enums.TaskPriority;
import com.task.management.system.enums.TaskStatus;
import com.task.management.system.exceptions.TaskNotFoundException;
import com.task.management.system.service.TaskManager;

public class MainApplication {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		String welcomeMessage = """

				=====================================================
				             TASK MANAGEMENT SYSTEM
				=====================================================

				1.  Create New Task
				2.  View All Tasks
				3.  Create New User
				4.  View All Users
				5.  Assign Task To User
				6.  Update Task Status
				7.  Update Task Priority
				8.  Add Comment To Task
				9.  Filter Tasks By Status
				10. Filter Tasks By Priority
				11. Filter Tasks By Assignee
				12. Search Task By ID
				13. Exit

				=====================================================

				""";

		TaskManager taskManager = new TaskManager();

		System.out.println(welcomeMessage);

		while (true) {

			try {

				System.out.print("Please select an option (1-13): ");

				int option = scanner.nextInt();
				scanner.nextLine();

				switch (option) {

				case 1:
					createNewTask(taskManager, scanner);
					break;

				case 2:
					viewAllTasks(taskManager);
					break;

				case 3:
					createNewUser(taskManager, scanner);
					break;

				case 4:
					viewAllUsers(taskManager);
					break;

				case 5:
					assignTaskToUser(taskManager, scanner);
					break;

				case 6:
					updateTaskStatus(taskManager, scanner);
					break;

				case 7:
					updateTaskPriority(taskManager, scanner);
					break;

				case 8:
					addCommentToTask(taskManager, scanner);
					break;

				case 9:
					filterTasksByStatus(taskManager, scanner);
					break;

				case 10:
					filterTasksByPriority(taskManager, scanner);
					break;

				case 11:
					filterTasksByAssignee(taskManager, scanner);
					break;

				case 12:
					searchTaskById(taskManager, scanner);
					break;

				case 13:
					System.out.println("""
							
							=========================================
							Exiting Task Management System...
							Goodbye!
							=========================================
							
							""");
					scanner.close();
					return;

				default:
					System.out.println("Invalid option selected.");
				}

			} catch (InputMismatchException e) {

				System.out.println("Invalid input. Please enter numeric value only.");
				scanner.nextLine();

			} catch (Exception e) {

				System.out.println(e.getMessage());
			}
		}
	}

	private static void createNewTask(TaskManager taskManager, Scanner scanner) {

		System.out.println("\n========== CREATE NEW TASK ==========");

		System.out.print("Enter task title: ");
		String title = scanner.nextLine();

		System.out.print("Enter task description: ");
		String description = scanner.nextLine();

		Task task = Task.builder()
				.title(title)
				.description(description)
				.status(TaskStatus.TODO)
				.priority(TaskPriority.LOW)
				.build();

		Task createdTask = taskManager.createTask(task);

		System.out.println("""
				
				=========================================
				Task Created Successfully
				=========================================
				""");

		printTask(createdTask);
	}

	private static void viewAllTasks(TaskManager taskManager) {

		System.out.println("\n========== ALL TASKS ==========");

		List<Task> tasks = taskManager.getAllTasks();

		if (tasks.isEmpty()) {
			System.out.println("No tasks available.");
			return;
		}

		tasks.forEach(MainApplication::printTask);
	}

	private static void createNewUser(TaskManager taskManager, Scanner scanner) {

		try {

			System.out.println("\n========== CREATE NEW USER ==========");

			System.out.print("Enter user name: ");
			String name = scanner.nextLine();

			System.out.print("Enter user email: ");
			String email = scanner.nextLine();

			User user = taskManager.createUser(name, email);

			System.out.println("""
					
					=========================================
					User Created Successfully
					=========================================
					""");

			System.out.println("""
					Name  : %s
					Email : %s
					""".formatted(user.getName(), user.getEmail()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void viewAllUsers(TaskManager taskManager) {

		System.out.println("\n========== ALL USERS ==========");

		List<User> users = taskManager.getAllUsers();

		if (users.isEmpty()) {
			System.out.println("No users found.");
			return;
		}

		users.forEach(user -> {

			System.out.println("""
					
					-----------------------------------------
					Name  : %s
					Email : %s
					-----------------------------------------
					""".formatted(user.getName(), user.getEmail()));
		});
	}

	private static void assignTaskToUser(TaskManager taskManager, Scanner scanner) {

		try {

			System.out.println("\n========== ASSIGN TASK ==========");

			System.out.print("Enter task ID: ");
			long taskId = scanner.nextLong();
			scanner.nextLine();

			System.out.print("Enter user email: ");
			String email = scanner.nextLine();

			User user = taskManager.getUserByEmail(email);

			if (user == null) {
				System.out.println("User not found.");
				return;
			}

			String response = taskManager.assignTask(taskId, user);

			System.out.println(response);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void updateTaskStatus(TaskManager taskManager, Scanner scanner) {

		try {

			System.out.println("\n========== UPDATE TASK STATUS ==========");

			System.out.print("Enter task ID: ");
			long taskId = scanner.nextLong();
			scanner.nextLine();

			System.out.print("Enter new status (TODO, IN_PROGRESS, DONE): ");
			String statusInput = scanner.nextLine();

			TaskStatus status =
					TaskStatus.valueOf(statusInput.toUpperCase());

			String response =
					taskManager.updateTaskStatus(taskId, status);

			System.out.println(response);

		} catch (TaskNotFoundException e) {

			System.out.println(e.getMessage());

		} catch (IllegalArgumentException e) {

			System.out.println(
					"Invalid status. Valid values: TODO, IN_PROGRESS, DONE");

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	private static void updateTaskPriority(TaskManager taskManager,
			Scanner scanner) {

		try {

			System.out.println("\n========== UPDATE TASK PRIORITY ==========");

			System.out.print("Enter task ID: ");
			long taskId = scanner.nextLong();
			scanner.nextLine();

			System.out.print("Enter new priority (LOW, MEDIUM, HIGH): ");
			String priorityInput = scanner.nextLine();

			TaskPriority priority =
					TaskPriority.valueOf(priorityInput.toUpperCase());

			String response =
					taskManager.updateTaskPriority(taskId, priority);

			System.out.println(response);

		} catch (IllegalArgumentException e) {

			System.out.println(
					"Invalid priority. Valid values: LOW, MEDIUM, HIGH");

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	private static void addCommentToTask(TaskManager taskManager,
			Scanner scanner) {

		try {

			System.out.println("\n========== ADD COMMENT ==========");

			System.out.print("Enter task ID: ");
			long taskId = scanner.nextLong();
			scanner.nextLine();

			System.out.print("Enter your email: ");
			String email = scanner.nextLine();

			User user = taskManager.getUserByEmail(email);

			if (user == null) {
				System.out.println("User not found.");
				return;
			}

			System.out.print("Enter comment: ");
			String commentText = scanner.nextLine();

			Comment comment = new Comment(
					commentText,
					user,
					LocalDateTime.now());

			String response =
					taskManager.addCommentToTask(taskId, comment);

			System.out.println(response);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	private static void filterTasksByStatus(TaskManager taskManager,
			Scanner scanner) {

		try {

			System.out.println("\n========== FILTER TASKS BY STATUS ==========");

			System.out.print("Enter status (TODO, IN_PROGRESS, DONE): ");

			String statusInput = scanner.nextLine();

			TaskStatus status =
					TaskStatus.valueOf(statusInput.toUpperCase());

			List<Task> tasks =
					taskManager.getTasksByStatus(status);

			if (tasks.isEmpty()) {
				System.out.println("No tasks found.");
				return;
			}

			tasks.forEach(MainApplication::printTask);

		} catch (Exception e) {

			System.out.println("Invalid status entered.");
		}
	}

	private static void filterTasksByPriority(TaskManager taskManager,
			Scanner scanner) {

		try {

			System.out.println("\n========== FILTER TASKS BY PRIORITY ==========");

			System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");

			String priorityInput = scanner.nextLine();

			TaskPriority priority =
					TaskPriority.valueOf(priorityInput.toUpperCase());

			List<Task> tasks =
					taskManager.getTasksByPriority(priority);

			if (tasks.isEmpty()) {
				System.out.println("No tasks found.");
				return;
			}

			tasks.forEach(MainApplication::printTask);

		} catch (Exception e) {

			System.out.println("Invalid priority entered.");
		}
	}

	private static void filterTasksByAssignee(TaskManager taskManager,
			Scanner scanner) {

		System.out.println("\n========== FILTER TASKS BY ASSIGNEE ==========");

		System.out.print("Enter assignee email: ");

		String email = scanner.nextLine();

		User user = taskManager.getUserByEmail(email);

		if (user == null) {
			System.out.println("User not found.");
			return;
		}

		List<Task> tasks =
				taskManager.getTasksByAssignee(user);

		if (tasks.isEmpty()) {
			System.out.println("No tasks assigned.");
			return;
		}

		tasks.forEach(MainApplication::printTask);
	}

	private static void searchTaskById(TaskManager taskManager,
			Scanner scanner) {

		System.out.println("\n========== SEARCH TASK ==========");

		System.out.print("Enter task ID: ");

		long taskId = scanner.nextLong();
		scanner.nextLine();

		Task task = taskManager.getTaskById(taskId);

		if (task == null) {
			System.out.println("Task not found.");
			return;
		}

		printTask(task);
	}

	private static void printTask(Task task) {

		System.out.println("""
				
				--------------------------------------------------
				Task ID      : %d
				Title        : %s
				Description  : %s
				Status       : %s
				Priority     : %s
				Assignee     : %s
				--------------------------------------------------
				"""
				.formatted(
						task.getTaskId(),
						task.getTitle(),
						task.getDescription(),
						task.getStatus(),
						task.getPriority(),
						task.getAssignee() != null
								? task.getAssignee().getName()
								: "Unassigned"));

		if (task.getComments() != null
				&& !task.getComments().isEmpty()) {

			System.out.println("Comments:");

			task.getComments().forEach(comment -> {

				System.out.println("""
						-> [%s]
						   %s : %s
						"""
						.formatted(
								comment.getTimestamp(),
								comment.getAuthor().getName(),
								comment.getContent()));
			});
		}
	}
}