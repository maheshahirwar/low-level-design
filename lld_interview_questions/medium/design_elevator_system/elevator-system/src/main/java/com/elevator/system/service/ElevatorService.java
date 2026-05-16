package com.elevator.system.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.elevator.system.enums.Direction;
import com.elevator.system.enums.RequestSource;
import com.elevator.system.model.Elevator;
import com.elevator.system.model.Request;
import com.elevator.system.observer.ElevatorActivityLogger;
import com.elevator.system.observer.ElevatorObserver;
import com.elevator.system.repository.ElevatorRepository;
import com.elevator.system.repository.InMemoryElevatorRepository;
import com.elevator.system.strategy.ElevatorScheduler;
import com.elevator.system.strategy.NearestElevatorScheduler;

public class ElevatorService {

	private static ElevatorService instance;
	private final ElevatorRepository elevatorRepository;
	private final ElevatorScheduler elevatorScheduler;

	private ExecutorService executorService;

	private final Integer minFloor;
	private final Integer maxFloor;

	private ElevatorService(int numElevators, int minFloor, int maxFloor) {
		
		executorService = Executors.newFixedThreadPool(numElevators);
		elevatorRepository = new InMemoryElevatorRepository();
		elevatorScheduler = new NearestElevatorScheduler();

		ElevatorObserver elevatorObserver = new ElevatorActivityLogger();
		
		for (int i = 0; i < numElevators; i++) {
			Elevator elevator = new Elevator(i, minFloor, maxFloor);
			elevator.addObserver(elevatorObserver);
			elevatorRepository.save(elevator);
		}
		
		this.minFloor = minFloor;
		this.maxFloor = maxFloor;
	}

	public static synchronized ElevatorService getInstance(int numElevators, int minFloor, int maxFloor) {
		
		if (instance == null) {
			instance = new ElevatorService(numElevators, minFloor, maxFloor);
		}
		return instance;
	}

	public void start() {
		for (Elevator elevator : elevatorRepository.findAll()) {
			executorService.submit(elevator);
		}
	}

	public void stop() {

		System.out.println("Stopping elevator system...");

		for (Elevator elevator : elevatorRepository.findAll()) {

			elevator.stop();
		}

		executorService.shutdown();

		System.out.println("Elevator system stopped.");
	}

	// EXTERNAL Request (Hall Call)
	public Elevator handleRequest(int floor, Direction direction) {
		
		validateFloor(floor);
		System.out.println("Received request at floor " + floor + " to go " + direction);
		
		Request request = Request.builder()
				.targetFloor(floor)
				.direction(direction)
				.source(RequestSource.EXTERNAL)
				.build();

		Elevator elevator = elevatorScheduler.selectElevator(elevatorRepository.findAll(), request);
		
		if (elevator != null) {
			elevator.addRequest(request);
		}
		System.out.println("Assigned Elevator " + (elevator != null ? elevator.getId() : "None")
				+ " to request at floor " + floor);
		return elevator;
	}

	// INTERNAL Request (Cabin Call)
	public void selectFloor(int elevatorId, int targetFloor) {

		validateFloor(targetFloor);

		Elevator elevator = elevatorRepository.findById(elevatorId);

		if (elevator == null) {
			return;
		}

		Direction direction = targetFloor >= elevator.getCurrentFloor().get() ? Direction.UP : Direction.DOWN;

		Request request = Request.builder()
				.targetFloor(targetFloor)
				.direction(direction)
				.source(RequestSource.INTERNAL)
				.build();

		elevator.addRequest(request);

		System.out.println("Added internal request to Elevator " + elevatorId + " for floor " + targetFloor);
	}

	public List<Elevator> getElevators() {
		return elevatorRepository.findAll();
	}

	private void validateFloor(int floor) {
		if (floor < minFloor || floor > maxFloor) {
			throw new IllegalArgumentException("Invalid floor: " + floor + ". Valid range is " + minFloor + " to " + maxFloor);
		}
	}

}
