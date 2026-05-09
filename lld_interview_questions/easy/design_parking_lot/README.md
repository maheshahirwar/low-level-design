# Parking Lot System (LLD)

A scalable and extensible **Parking Lot Management System** built using core Java and Low-Level Design principles.

The system supports:
- Parking and unparking vehicles
- Ticket generation
- Parking fee calculation
- Multiple floors and parking spots
- Spot allocation strategies
- Different fee calculation strategies
- In-memory repository implementation

---

# Features

- Multiple parking floors support
- Different vehicle types support
- Parking spot allocation
- Ticket generation while parking
- Vehicle unparking with fee calculation
- Strategy-based fee calculation
- In-memory repository implementation
- Custom exception handling
- Extensible and modular design

---

# Project Structure

```text
src/main/java
└── com.parking.lot
    ├── MainApplication.java
    │
    ├── entity
    │   ├── Floor.java
    │   ├── Spot.java
    │   ├── Ticket.java
    │   ├── Vehicle.java
    │   └── VehicleType.java
    │
    ├── exceptions
    │   ├── FloorAlreadyExistsException.java
    │   ├── InvalidFloorException.java
    │   ├── InvalidFloorNumber.java
    │   ├── InvalidTicketException.java
    │   └── SpotUnavailableException.java
    │
    ├── repository
    │   ├── ParkingRepository.java
    │   └── InMemoryParkingRepository.java
    │
    ├── service
    │   ├── ParkingLot.java
    │   └── ParkingService.java
    │
    └── strategies
        ├── AllocationStrategy.java
        ├── AllocateNearestSpot.java
        ├── FeeStrategy.java
        ├── FlatRateFeeStrategy.java
        └── VehicleBasedFeeStrategy.java
```

---

# Requirements

- Support multiple floors
- Support different vehicle types
- Allocate nearest available parking spot
- Generate parking ticket during entry
- Calculate parking fee during exit
- Support multiple fee strategies
- Easy extensibility for future enhancements

---

# Architecture Overview

The application follows a layered design:

## 1. Client Layer

### MainApplication

Acts as the client of the system.

Responsible for:
- Initializing parking floors and spots
- Configuring allocation strategy
- Configuring fee strategy
- Parking and unparking vehicles
- Running the complete parking lot workflow

---

## 2. Entity Layer

Contains all core domain models.

### Vehicle
Represents a vehicle entering the parking lot.

### VehicleType
Enum representing vehicle types.

Example:
- CAR
- BIKE
- TRUCK

### Spot
Represents a parking spot.

Contains:
- Spot ID
- Vehicle Type
- Occupied status
- Assigned vehicle

### Floor
Represents a parking floor containing parking spots.

### Ticket
Generated when a vehicle is parked.

Contains:
- Ticket ID
- Entry Time
- Spot Information

---

## 3. Repository Layer

Responsible for data persistence operations.

### ParkingRepository

Interface defining CRUD operations for:
- Floors
- Spots
- Tickets

### InMemoryParkingRepository

In-memory implementation of `ParkingRepository`.

Uses Java collections internally for storing:
- Floors
- Spots
- Tickets

---

## 4. Service Layer

Contains the business logic of the system.

---

## ParkingService

Handles CRUD operations and business logic for:
- Floors
- Spots
- Tickets

### Responsibilities

### Floor Operations
- Add floor
- Get floor
- Validate floor existence

### Spot Operations
- Add spot
- Update spot
- Fetch available spots

### Ticket Operations
- Save ticket
- Fetch ticket
- Remove ticket

### Other Responsibilities
- Parking lot validations
- Spot availability handling

---

## ParkingLot

Main orchestration class for parking and unparking flow.

Uses:
- `ParkingService`
- `AllocationStrategy`
- `FeeStrategy`

### Responsibilities

### Park Vehicle Flow
1. Find available spot using allocation strategy
2. Assign vehicle to spot
3. Mark spot occupied
4. Save updated spot
5. Generate ticket
6. Save ticket

### Unpark Vehicle Flow
1. Fetch ticket
2. Calculate parking fee
3. Free parking spot
4. Remove ticket

---

# Strategy Layer

Implements Strategy Design Pattern.

---

## AllocationStrategy

Interface for parking spot allocation.

### AllocateNearestSpot

Finds nearest available parking spot based on vehicle type.

---

## FeeStrategy

Interface for parking fee calculation.

### FlatRateFeeStrategy

Calculates parking fee using flat rate pricing.

### VehicleBasedFeeStrategy

Calculates fee based on vehicle type.

---

# Design Patterns Used

## Strategy Pattern

Used for:
- Spot allocation
- Fee calculation

Benefits:
- Flexible design
- Easy extensibility
- Open for extension, closed for modification

---

# Exception Handling

Custom exceptions are used for handling invalid operations gracefully.

Examples:
- `FloorAlreadyExistsException`
- `InvalidFloorException`
- `InvalidFloorNumber`
- `InvalidTicketException`
- `SpotUnavailableException`

---

# Parking Flow

## Vehicle Parking Flow

```text
Client -> ParkingLot
        -> AllocationStrategy
        -> ParkingService
        -> Repository
```

Steps:
1. Client requests parking
2. Allocation strategy finds nearest spot
3. Spot gets occupied
4. Ticket gets generated
5. Ticket stored in repository

---

## Vehicle Unparking Flow

```text
Client -> ParkingLot
        -> ParkingService
        -> FeeStrategy
        -> Repository
```

Steps:
1. Client provides ticket ID
2. Ticket fetched
3. Fee calculated
4. Spot freed
5. Ticket removed

---

# UML Class Diagram

![](../../../../uml-diagrams/class-diagrams/parkinglot-class-diagram.png)

---

# Example Usage

## Initialize Parking Lot

```java
ParkingRepository parkingRepository = new InMemoryParkingRepository();

ParkingService parkingService = new ParkingService(parkingRepository, 3);

AllocationStrategy allocationStrategy =
        new AllocateNearestSpot(parkingService);

FeeStrategy feeStrategy =
        new FlatRateFeeStrategy();

ParkingLot parkingLot =
        new ParkingLot(
                parkingService,
                allocationStrategy,
                feeStrategy
        );
```

---

## Park Vehicle

```java
Ticket ticket =
    parkingLot.parkVehicle(
        new Vehicle(
            "KA-01-HH-1234",
            VehicleType.CAR
        )
    );
```

---

## Unpark Vehicle

```java
double fee =
    parkingLot.unparkVehicle(
        ticket.getTicketId()
    );
```

---

# Sample Workflow

1. Initialize parking service
2. Add floors
3. Add parking spots
4. Configure strategies
5. Park vehicles
6. Generate tickets
7. Unpark vehicles
8. Calculate fees
9. Free parking spots

---

# Extensibility

The system is designed for easy future enhancements.

---

## Add New Vehicle Type

Update:
```java
VehicleType.java
```

Example:
```java
BUS
ELECTRIC_CAR
```

---

## Add New Fee Strategy

Implement:
```java
FeeStrategy
```

Example:
```java
class HourlyFeeStrategy implements FeeStrategy
```

---

## Add New Allocation Strategy

Implement:
```java
AllocationStrategy
```

Example:
```java
class RandomSpotAllocationStrategy
        implements AllocationStrategy
```

---

# Technologies Used

- Java
- Object-Oriented Programming
- Collections Framework
- Strategy Design Pattern
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

- Multi-entry and exit gates
- Real-time dashboard
- Database integration
- Slot reservation
- Hourly pricing support
- Payment integration
- Concurrency handling
- Parking analytics
- Notification support

---

# Author

Mahesh Ahirwar
