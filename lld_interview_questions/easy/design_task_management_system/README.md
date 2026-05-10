# Task Management System (LLD)

A scalable and extensible **Task Management System** built using core Java and Low-Level Design principles.

The system supports:
- Task creation and management
- User management
- Task assignment
- Task status tracking
- Task priority management
- Task comments
- Task filtering and searching
- In-memory data management

---

# Features

- Create and manage tasks
- Create and manage users
- Assign tasks to users
- Update task status
- Update task priority
- Add comments to tasks
- Filter tasks by:
  - Status
  - Priority
  - Assignee
- Search task by task ID
- Professional console-based workflow
- Custom exception handling
- Thread-safe in-memory storage
- Extensible and modular design

---

# Project Structure

```text
src/main/java
└── com.task.management.system
    ├── MainApplication.java
    │
    ├── entities
    │   ├── Comment.java
    │   ├── Task.java
    │   └── User.java
    │
    ├── enums
    │   ├── TaskPriority.java
    │   └── TaskStatus.java
    │
    ├── exceptions
    │   └── TaskNotFoundException.java
    │
    └── service
        └── TaskManager.java
```

---

# Requirements

- Support task creation
- Support user creation
- Support task assignment
- Support task status updates
- Support task priority updates
- Support task comments
- Support task filtering
- Support task searching
- Support concurrent-safe task storage
- Easy extensibility for future enhancements

---

# Architecture Overview

The application follows a layered and modular design.

---

# 1. Client Layer

## MainApplication

Acts as the client of the system.

### Responsibilities

- Taking user input
- Displaying menu-driven console UI
- Calling service layer methods
- Showing formatted task and user details
- Handling exceptions gracefully

---

# 2. Entity Layer

Contains all core domain models.

---

## Task

Represents a task in the system.

### Fields

- taskId
- title
- description
- status
- priority
- assignee
- comments

### Responsibilities

- Store task details
- Maintain task state
- Maintain comments and assignment details

---

## User

Represents a user in the system.

### Fields

- name
- email

### Responsibilities

- Represents task assignee
- Represents comment author

---

## Comment

Represents comments added on tasks.

### Fields

- content
- author
- timestamp

### Responsibilities

- Maintain task discussion history
- Track comment author and creation time

---

# 3. Enum Layer

Contains application constants.

---

## TaskStatus

Represents task workflow state.

### Supported Values

```java
TODO
IN_PROGRESS
DONE
```

---

## TaskPriority

Represents task urgency level.

### Supported Values

```java
LOW
MEDIUM
HIGH
```

---

# 4. Exception Layer

Contains custom exceptions.

---

## TaskNotFoundException

Thrown when:
- Invalid task ID is provided
- Task does not exist in the system

Provides cleaner exception handling and better debugging.

---

# 5. Service Layer

Contains the complete business logic of the application.

---

# TaskManager

Main service class responsible for task and user management.

Uses:
- `ConcurrentHashMap`
- `AtomicLong`
- Java Collections Framework

---

## Responsibilities

### Task Operations

- Create task
- Get task by ID
- Get all tasks
- Assign task
- Update task status
- Update task priority
- Add comments to task

---

### User Operations

- Create user
- Get user by email
- Get all users

---

### Filter Operations

- Get tasks by status
- Get tasks by priority
- Get tasks by assignee

---

# Design Principles Used

---

## Separation of Concerns

Each class has a dedicated responsibility:

- Entity classes represent domain models
- Service layer contains business logic
- MainApplication handles user interaction

---

## Single Responsibility Principle (SRP)

Every class focuses on one responsibility only.

### Examples

- `Task` → task data
- `User` → user data
- `Comment` → comment data
- `TaskManager` → task operations

---

## Manager Pattern

`TaskManager` acts as a centralized manager/service layer for all operations.

### Benefits

- Cleaner code organization
- Better maintainability
- Easy extensibility

---

## Thread-Safe Collections

Uses:

```java
ConcurrentHashMap
AtomicLong
```

### Benefits

- Safer concurrent operations
- Better scalability
- Unique task ID generation

---

# Console Workflow

---

## Task Creation Flow

```text
Client -> MainApplication
       -> TaskManager
       -> Task Stored In Memory
```

### Steps

1. User enters task details
2. TaskManager generates unique task ID
3. Task stored in memory
4. Success response displayed

---

## Task Assignment Flow

```text
Client -> MainApplication
       -> TaskManager
       -> Task Updated
```

### Steps

1. User provides task ID
2. User provides assignee email
3. Task fetched and updated
4. Assignment confirmation displayed

---

## Task Comment Flow

```text
Client -> MainApplication
       -> TaskManager
       -> Comment Added
```

### Steps

1. User selects task
2. User enters comment
3. Comment stored with timestamp
4. Updated task displayed

---

# Menu Driven Console Interface

The application provides a professional console-based interface.

```text
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
```

---

# UML Class Diagram

![](../../../../uml-diagrams/class-diagrams/taskmanagementsystem-class-diagram.png)

---

# Example Usage

---

## Create Users

```java
TaskManager taskManager = new TaskManager();

User mahesh =
        taskManager.createUser(
                "Mahesh",
                "mahesh@gmail.com"
        );

User john =
        taskManager.createUser(
                "John",
                "john@gmail.com"
        );
```

---

## Create Task

```java
Task task = Task.builder()
        .title("Implement Login API")
        .description("Develop authentication module")
        .status(TaskStatus.TODO)
        .priority(TaskPriority.HIGH)
        .build();

taskManager.createTask(task);
```

---

## Assign Task

```java
taskManager.assignTask(
        task.getTaskId(),
        john
);
```

---

## Update Task Status

```java
taskManager.updateTaskStatus(
        task.getTaskId(),
        TaskStatus.IN_PROGRESS
);
```

---

## Update Task Priority

```java
taskManager.updateTaskPriority(
        task.getTaskId(),
        TaskPriority.HIGH
);
```

---

## Add Comment

```java
taskManager.addCommentToTask(
        task.getTaskId(),
        new Comment(
                "Started implementation",
                john,
                LocalDateTime.now()
        )
);
```

---

## Filter Tasks By Status

```java
List<Task> tasks =
        taskManager.getTasksByStatus(
                TaskStatus.IN_PROGRESS
        );
```

---

## Filter Tasks By Priority

```java
List<Task> tasks =
        taskManager.getTasksByPriority(
                TaskPriority.HIGH
        );
```

---

## Filter Tasks By Assignee

```java
List<Task> tasks =
        taskManager.getTasksByAssignee(
                john
        );
```

---

# Sample Workflow

1. Create users
2. Create tasks
3. Assign tasks
4. Update task status
5. Update task priority
6. Add comments
7. Filter tasks
8. Search tasks
9. View all tasks

---

# Technologies Used

- Java
- Object-Oriented Programming
- Java Collections Framework
- ConcurrentHashMap
- AtomicLong
- Builder Pattern
- Lombok

---

# How to Run

## Compile Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn exec:java
```

Or directly run:

```text
MainApplication.java
```

from your IDE.

---

# Future Improvements

The system is designed for easy future enhancements.

---

## Planned Enhancements

- Due dates and deadlines
- Task notifications
- Task history tracking
- Role-based access control
- Database integration
- REST API support
- Spring Boot integration
- Authentication and authorization
- Task attachments
- Task labels/tags
- Pagination support
- Dashboard and analytics
- Concurrent task updates
- Email notifications

---

# Author

Mahesh Ahirwar
