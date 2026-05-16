package com.elevator.system;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.elevator.system.enums.Direction;
import com.elevator.system.model.Elevator;
import com.elevator.system.service.ElevatorService;

public class MainApplication {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("\n========== ELEVATOR SYSTEM INITIALIZATION ==========");
		System.out.print("Enter number of elevators: ");

		int elevatorCount = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter minimum floor: ");
		int minFloor = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Enter maximum floor: ");
		int maxFloor = scanner.nextInt();
		scanner.nextLine();

		ElevatorService elevatorSystem = ElevatorService.getInstance(elevatorCount, minFloor, maxFloor);

		elevatorSystem.start();

		String welcomeMessage = """

				=====================================================
				            SMART ELEVATOR SYSTEM
				=====================================================

				1. Request Elevator
				2. Select Floor Inside Elevator
				3. View All Elevators Status
				4. Simulate Concurrent Requests
				5. Stop Elevator System

				=====================================================
				""";

		System.out.println(welcomeMessage);

		while (true) {

			try {

				System.out.print("Select an option (1-5): \n");

				int option = scanner.nextInt();
				scanner.nextLine();

				switch (option) {

				case 1:
					requestElevator(elevatorSystem, scanner);
					break;
				case 2:
					selectFloorInsideElevator(elevatorSystem, scanner);
					break;
				case 3:
					viewElevatorStatus(elevatorSystem);
					break;
				case 4:
					simulateConcurrentRequests(elevatorSystem, scanner, maxFloor);
					break;
				case 5:

					System.out.println("""

							=========================================
							Stopping Elevator System...
							Goodbye!
							=========================================
							""");

					elevatorSystem.stop();
					scanner.close();
					return;

				default:
					System.out.println("Invalid option selected.");
				}

			} catch (InputMismatchException e) {

				System.out.println("Invalid input. Please enter numeric values only.");
				scanner.nextLine();

			} catch (Exception e) {

				System.out.println(e.getMessage());
			}
		}
	}

	private static void requestElevator(ElevatorService elevatorSystem, Scanner scanner) {

		try {

			System.out.println("\n========== REQUEST ELEVATOR ==========");

			System.out.print("Enter current floor: ");
			int floor = scanner.nextInt();
			scanner.nextLine();

			System.out.print("Enter direction (UP/DOWN): ");
			String directionInput = scanner.nextLine();

			Direction direction = Direction.valueOf(directionInput.toUpperCase());

			Elevator elevator = elevatorSystem.handleRequest(floor, direction);

			System.out.println("""

					=========================================
					Elevator Assigned Successfully
					=========================================
					""");

			printElevator(elevator);

		} catch (Exception e) {

			System.out.println("Error while requesting elevator: " + e.getMessage());
		}
	}

	private static void selectFloorInsideElevator(ElevatorService elevatorSystem, Scanner scanner) {

		try {

			System.out.println("\n========== SELECT FLOOR ==========");

			System.out.print("Enter elevator ID: ");
			int elevatorId = scanner.nextInt();

			System.out.print("Enter destination floor: ");
			int destinationFloor = scanner.nextInt();
			scanner.nextLine();

			elevatorSystem.selectFloor(elevatorId, destinationFloor);

			System.out.println("Destination floor added successfully.");

		} catch (Exception e) {

			System.out.println("Error selecting floor: " + e.getMessage());
		}
	}

	private static void viewElevatorStatus(ElevatorService elevatorSystem) {

		System.out.println("\n========== ELEVATORS STATUS ==========");

		elevatorSystem.getElevators().forEach(MainApplication::printElevator);
	}

	private static void simulateConcurrentRequests(ElevatorService elevatorSystem, Scanner scanner, int maxFloor) {

		System.out.println("\n========== SIMULATING CONCURRENT REQUESTS ==========");

		System.out.println("Enter number of concurrent requests to simulate: ");
		
		int numThreads = scanner.nextInt();

		for (int i = 0; i < numThreads; i++) {
			
			int floor = (int) (Math.random() * maxFloor) + 1;
			
			Direction direction = Math.random() > 0.5 ? Direction.UP : Direction.DOWN;

			new Thread(() -> {
				elevatorSystem.handleRequest(floor, direction);
			}).start();
		}
		
		System.out.println("Concurrent requests submitted successfully.");
	}

	private static void printElevator(Elevator elevator) {

		System.out.println("""

				--------------------------------------------------
				Elevator ID      : %d
				Current Floor    : %d
				Current Direction: %s
				Current State    : %s
				Pending Requests : %s
				--------------------------------------------------
				""".formatted(elevator.getId(), 
						elevator.getCurrentFloor().get(), 
						elevator.getDirection(),
						elevator.getState(), 
						elevator.getRequests()));
	}
}
