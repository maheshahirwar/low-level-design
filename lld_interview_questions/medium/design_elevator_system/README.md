# Elevator System (LLD)

A scalable and extensible **Elevator Management System** built using core Java, multithreading, and Low-Level Design principles.

The system simulates real-world elevator operations including:

- External elevator requests
- Internal floor selection
- Multiple elevators running concurrently
- Smart nearest elevator scheduling
- Elevator state transitions using State Pattern
- Real-time elevator movement simulation
- Observer-based activity logging
- Configurable minimum and maximum floors
- Concurrent request processing using multithreading

---

# Features

- Support for multiple elevators
- External hall requests (UP/DOWN)
- Internal floor selection inside elevator
- Smart nearest elevator scheduling algorithm
- Dynamic elevator state management
- Real-time floor-by-floor movement simulation
- Concurrent elevator execution using thread pool
- Observer-based elevator activity logging
- Thread-safe request handling
- Configurable building floor range
- Professional menu-driven console interface
- Extensible and modular architecture

---

# Project Structure

```text
src/main/java
└── com.elevator.system
    ├── MainApplication.java
    │
    ├── enums
    │   ├── Direction.java
    │   ├── ElevatorStateType.java
    │   └── RequestSource.java
    │
    ├── model
    │   ├── Elevator.java
    │   └── Request.java
    │
    ├── observer
    │   ├── ElevatorActivityLogger.java
    │   └── ElevatorObserver.java
    │
    ├── repository
    │   ├── ElevatorRepository.java
    │   └── InMemoryElevatorRepository.java
    │
    ├── service
    │   └── ElevatorService.java
    │
    ├── state
    │   ├── ElevatorState.java
    │   ├── IdleState.java
    │   ├── MovingUpState.java
    │   └── MovingDownState.java
    │
    └── strategy
        ├── ElevatorScheduler.java
        └── NearestElevatorScheduler.java
```

---

# Requirements

- Support multiple elevators
- Support external elevator requests
- Support internal floor selection
- Support concurrent request handling
- Support elevator scheduling
- Support real-time elevator movement
- Support state transitions
- Support activity logging
- Support configurable floor ranges
- Easy extensibility for future enhancements

---

# Architecture Overview

The application follows a layered and modular Low-Level Design architecture.

---

# 1. Client Layer

## MainApplication

Acts as the client of the elevator system.

### Responsibilities

- Taking user input
- Displaying menu-driven console UI
- Initializing elevator system configuration
- Calling service layer methods
- Displaying elevator statuses
- Simulating concurrent requests
- Graceful system shutdown

---

# 2. Model Layer

Contains all core domain models.

---

## Elevator

Represents a single elevator running independently in the system.

### Fields

- id
- currentFloor
- elevatorState
- isRunning
- upRequests
- downRequests
- observers
- minFloor
- maxFloor

### Responsibilities

- Maintain elevator current state
- Process upward and downward requests
- Simulate real-time movement
- Notify observers during activities
- Maintain separate request queues
- Run independently using Runnable interface

### Request Handling Strategy

The elevator maintains:

```java
TreeSet<Integer> upRequests;
TreeSet<Integer> downRequests;
```

### Benefits

- Automatically sorted requests
- Efficient request processing
- Optimized elevator movement
- Reduced unnecessary floor traversal

---

## Request

Represents elevator requests.

### Fields

- targetFloor
- direction
- source

### Responsibilities

- Store requested floor
- Maintain request direction
- Identify request source
- Represent internal/external requests

---

# 3. Enum Layer

Contains application constants.

---

## Direction

Represents elevator movement direction.

### Supported Values

```java
UP
DOWN
IDLE
```

---

## ElevatorStateType

Represents current elevator state.

### Supported Values

```java
IDLE
MOVING_UP
MOVING_DOWN
```

---

## RequestSource

Represents request origin.

### Supported Values

```java
EXTERNAL
INTERNAL
```

---

# 4. Repository Layer

Responsible for elevator persistence and retrieval.

---

## ElevatorRepository

Defines elevator storage operations.

### Responsibilities

- Save elevators
- Fetch elevators
- Retrieve all elevators

---

## InMemoryElevatorRepository

Thread-safe in-memory implementation of `ElevatorRepository`.

### Uses

```java
ConcurrentHashMap<Integer, Elevator>
```

### Benefits

- Fast lookup
- Concurrent-safe operations
- Easy testing
- No database dependency

---

# 5. Service Layer

Contains the complete business logic.

---

# ElevatorService

Main service class responsible for:

- Elevator initialization
- Elevator scheduling
- Elevator request processing
- Thread management
- Elevator lifecycle management

---

## Internal Components

```java
private final ElevatorRepository elevatorRepository;
private final ElevatorScheduler elevatorScheduler;
private ExecutorService executorService;
```

---

## Responsibilities

### Request Operations

- Request elevator externally
- Select floor inside elevator
- Assign requests to nearest elevator
- Validate floor ranges

---

### Elevator Operations

- Start elevator threads
- Stop elevator system
- View elevator status
- Simulate concurrent requests

---

### Scheduling Operations

- Select nearest available elevator
- Reduce waiting time
- Optimize request allocation

---

# Design Patterns Used

---

# 1. Strategy Pattern

Implemented for elevator scheduling.

### Strategy Interface

```java
ElevatorScheduler
```

### Concrete Strategy

```java
NearestElevatorScheduler
```

### Purpose

Allows dynamic elevator scheduling algorithms.

### Benefits

- Open for extension
- Easy to add new scheduling algorithms
- Cleaner business logic

---

# 2. State Pattern

Implemented for elevator state transitions.

### State Interface

```java
ElevatorState
```

### Concrete States

- `IdleState`
- `MovingUpState`
- `MovingDownState`

### Purpose

Encapsulates elevator behavior based on its current state.

### Benefits

- Cleaner state management
- Better maintainability
- Removes complex conditional logic

---

# 3. Observer Pattern

Implemented for activity logging.

### Observer Interface

```java
ElevatorObserver
```

### Concrete Observer

```java
ElevatorActivityLogger
```

### Activities Logged

- Elevator movement
- Floor arrival
- Request assignment
- Direction changes
- Idle waiting status

### Benefits

- Loose coupling
- Event-driven notifications
- Better extensibility

---

# 4. Repository Pattern

Implemented for elevator data management.

### Repository Interface

```java
ElevatorRepository
```

### Concrete Repository

```java
InMemoryElevatorRepository
```

### Purpose

Separates persistence logic from business logic.

### Benefits

- Cleaner architecture
- Easier testing
- Future database integration support

---

# 5. Singleton Pattern

Implemented in:

```java
ElevatorService
```

### Purpose

Ensures only one elevator system instance exists.

### Benefits

- Centralized elevator management
- Shared resource control
- Prevents duplicate system initialization

### Implementation

```java
public static synchronized ElevatorService getInstance(
        int numElevators,
        int minFloor,
        int maxFloor
) {
    if(instance == null) {
        instance = new ElevatorService(
                numElevators,
                minFloor,
                maxFloor
        );
    }

    return instance;
}
```

---

# Thread-Safe Design

Uses:

```java
ConcurrentHashMap
ExecutorService
AtomicInteger
volatile boolean
TreeSet
```

### Benefits

- Safe concurrent execution
- Multiple elevators running independently
- Optimized request processing
- Better scalability

---

# Elevator Execution Flow

## External Request Flow

```text
Client -> MainApplication
       -> ElevatorService
       -> ElevatorScheduler
       -> Nearest Elevator
       -> Elevator Request Queue
```

### Steps

1. User enters current floor
2. User selects direction
3. Scheduler selects nearest elevator
4. Request assigned to elevator
5. Elevator processes request
6. Observer logs activities

---

## Internal Floor Selection Flow

```text
Client -> MainApplication
       -> ElevatorService
       -> Elevator
       -> Request Queue
```

### Steps

1. User selects elevator ID
2. User enters destination floor
3. Floor added to request queue
4. Elevator processes request
5. Elevator reaches destination

---

## Elevator State Transition Flow

```text
IdleState
    ↓
MovingUpState / MovingDownState
    ↓
IdleState
```

---

# Menu Driven Console Interface

```text
=====================================================
                SMART ELEVATOR SYSTEM
=====================================================

1. Request Elevator
2. Select Floor Inside Elevator
3. View All Elevators Status
4. Simulate Concurrent Requests
5. Stop Elevator System

=====================================================
```
---

# Low-Level Design Diagram

```text
                 +----------------------+
                 |   MainApplication    |
                 +----------+-----------+
                            |
                            v
                 +----------------------+
                 |   ElevatorService    |
                 +----------+-----------+
                            |
          +----------------+----------------+
          |                                 |
          v                                 v
+----------------------+       +--------------------------+
| ElevatorScheduler    |       | ElevatorRepository       |
+----------------------+       +--------------------------+
          |                                 |
          v                                 v
+----------------------+       +--------------------------+
| NearestElevatorSched.|       | InMemoryElevatorRepo     |
+----------------------+       +--------------------------+

                            |
                            v

                 +----------------------+
                 |      Elevator        |
                 +----------+-----------+
                            |
          +----------------+----------------+
          |                                 |
          v                                 v
+----------------------+       +----------------------+
| ElevatorState        |       | ElevatorObserver     |
+----------------------+       +----------------------+
          |                                 |
   +------+------+                   +-------------+
   |             |                   | ActivityLog |
   v             v                   +-------------+
IdleState   MovingStates
```

---

# Example Usage

## Initialize Elevator System

```java
ElevatorService elevatorService =
        ElevatorService.getInstance(
                4,
                -2,
                15
        );
```

---

## Request Elevator

```java
elevatorService.requestElevator(
        3,
        Direction.UP
);
```

---

## Select Floor Inside Elevator

```java
elevatorService.selectFloor(
        0,
        10
);
```

---

## View Elevator Status

```java
elevatorService.viewAllElevatorsStatus();
```

---

# Sample Console Output

```text
=========== ELEVATOR SYSTEM INITIALIZATION ===========

Enter number of elevators: 4
Enter minimum floor: -2
Enter maximum floor: 15

=====================================================
                SMART ELEVATOR SYSTEM
=====================================================

1. Request Elevator
2. Select Floor Inside Elevator
3. View All Elevators Status
4. Simulate Concurrent Requests
5. Stop Elevator System

=====================================================

[ELEVATOR-0]: Waiting for requests...
[ELEVATOR-1]: Waiting for requests...
[ELEVATOR-2]: Waiting for requests...
[ELEVATOR-3]: Waiting for requests...

Received request at floor 3 to go UP

[ELEVATOR-0]:
Received request:
Request(targetFloor=3,
direction=UP,
source=EXTERNAL)
```

---

# Technologies Used

- Java
- Object-Oriented Programming
- Java Collections Framework
- ConcurrentHashMap
- ExecutorService
- AtomicInteger
- Multithreading
- Strategy Pattern
- State Pattern
- Observer Pattern
- Repository Pattern
- Singleton Pattern
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

## Planned Enhancements

- Priority-based scheduling
- AI-based smart scheduling
- Maintenance mode
- Emergency stop handling
- Weight and overload detection
- Door open/close simulation
- Real-time dashboard UI
- REST API support
- Spring Boot integration
- Database integration
- WebSocket live monitoring
- Authentication and authorization
- Energy optimization algorithms
- Distributed elevator clusters

---

# Author

Mahesh Ahirwar
