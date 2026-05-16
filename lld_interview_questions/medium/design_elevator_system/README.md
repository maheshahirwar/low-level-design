# Elevator System (LLD)

A scalable and extensible **Elevator Management System** built using core Java, multithreading, and Low-Level Design principles.

The system simulates real-world elevator operations including:

- External elevator requests
- Internal floor selection
- Multiple elevators running concurrently
- Smart elevator scheduling
- Elevator state transitions
- Real-time elevator movement
- Observer-based activity logging
- Configurable floor limits

---

# Features

- Multiple elevators support
- External hall requests
- Internal elevator floor selection
- Smart nearest elevator scheduling
- Real-time elevator movement simulation
- Elevator state management
- Multithreading support
- Observer-based activity logging
- Configurable minimum and maximum floors
- Thread-safe request processing
- Console-based interactive workflow
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
- Support elevator movement simulation
- Support elevator state transitions
- Support activity logging
- Support configurable floor ranges
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
- Initializing elevator system configuration
- Calling service layer methods
- Displaying elevator statuses
- Simulating concurrent requests
- Handling exceptions gracefully

---

# 2. Model Layer

Contains all core domain models.

## Elevator

Represents an elevator in the system.

### Fields

- elevatorId
- currentFloor
- currentDirection
- currentState
- pendingRequests
- observers

### Responsibilities

- Maintain elevator state
- Process floor requests
- Simulate elevator movement
- Notify observers on activities
- Manage internal request queue
- Handle state transitions

---

## Request

Represents elevator requests.

### Fields

- targetFloor
- direction
- source

### Responsibilities

- Store requested floor details
- Maintain request direction
- Track request source
- Represent internal and external requests

---

# 3. Enum Layer

Contains application constants.

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

Represents elevator operational state.

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

Responsible for elevator storage and retrieval.

## ElevatorRepository

Defines elevator persistence operations.

### Responsibilities

- Store elevators
- Fetch elevators
- Update elevator details

---

## InMemoryElevatorRepository

Thread-safe in-memory implementation of `ElevatorRepository`.

### Uses

```java
ConcurrentHashMap
```

### Benefits

- Fast access
- Concurrent-safe operations
- Easy testing
- No external database dependency

---

# 5. Service Layer

Contains complete business logic.

## ElevatorService

Main service class responsible for:

- Elevator request handling
- Elevator scheduling
- Elevator movement simulation
- Elevator status management

### Responsibilities

#### Request Operations

- Request elevator externally
- Select floor inside elevator
- Assign requests to elevators
- Process pending requests

#### Elevator Operations

- Start elevator threads
- Stop elevators
- Display elevator status
- Simulate concurrent requests

#### Scheduling Operations

- Find nearest elevator
- Optimize request allocation
- Reduce waiting time

---

# Design Patterns Used

## 1. Strategy Pattern

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
- Removes scheduling complexity from service layer

---

## 2. State Pattern

Implemented for elevator behavior management.

### State Interface

```java
ElevatorState
```

### Concrete States

- `IdleState`
- `MovingUpState`
- `MovingDownState`

### Purpose

Encapsulates elevator behavior based on current state.

### Benefits

- Cleaner state transitions
- Better maintainability
- Removes large conditional logic

---

## 3. Observer Pattern

Implemented for activity logging.

### Observer Interface

```java
ElevatorObserver
```

### Concrete Observer

```java
ElevatorActivityLogger
```

### Purpose

Automatically logs elevator activities whenever state changes occur.

### Activities Logged

- Elevator movement
- Request assignment
- Floor arrival
- State transitions
- Idle waiting status

### Benefits

- Loose coupling
- Event-driven architecture
- Better extensibility

---

## 4. Repository Pattern

Implemented for data management.

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
- Easier future database integration
- Improved testability

---

# Thread-Safe Design

Uses:

```java
ConcurrentHashMap
BlockingQueue
ExecutorService
```

### Benefits

- Safe concurrent operations
- Parallel elevator execution
- Better scalability
- Real-time request handling

---

# Console Workflow

## External Elevator Request Flow

```text
Client -> MainApplication
       -> ElevatorService
       -> ElevatorScheduler
       -> Selected Elevator
       -> Elevator Processes Request
```

### Steps

1. User enters current floor
2. User selects direction
3. Scheduler selects nearest elevator
4. Request assigned to elevator
5. Elevator starts movement
6. Observer logs activity

---

## Internal Floor Selection Flow

```text
Client -> MainApplication
       -> ElevatorService
       -> Elevator Request Queue
```

### Steps

1. User selects elevator ID
2. User enters destination floor
3. Request added to queue
4. Elevator processes request
5. Elevator reaches destination

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

# UML Class Diagram

![](../../../../uml-diagrams/class-diagrams/elevatorsystem-class-diagram.png)

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
ElevatorSystemConfig config =
        new ElevatorSystemConfig(
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

Select an option (1-5):

Received request at floor 3 to go UP

[ELEVATOR-0]:
Received request: Request(targetFloor=3,
direction=UP,
source=EXTERNAL)

Elevator moving from floor 0 to floor 3...
```

---

# Technologies Used

- Java
- Object-Oriented Programming
- Java Collections Framework
- ConcurrentHashMap
- BlockingQueue
- ExecutorService
- Multithreading
- Strategy Pattern
- State Pattern
- Observer Pattern
- Repository Pattern
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
