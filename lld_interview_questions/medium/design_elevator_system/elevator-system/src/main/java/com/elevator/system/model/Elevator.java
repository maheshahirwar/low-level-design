package com.elevator.system.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import com.elevator.system.enums.Direction;
import com.elevator.system.observer.ElevatorObserver;
import com.elevator.system.state.ElevatorState;
import com.elevator.system.state.IdleState;

import lombok.Getter;

@Getter
public class Elevator implements Runnable {

	private final Integer id;

	private final AtomicInteger currentFloor;

	private ElevatorState elevatorState;

	private volatile boolean isRunning = true;

	private final TreeSet<Integer> upRequests;

	private final TreeSet<Integer> downRequests;

	private final List<ElevatorObserver> observers = new ArrayList<>();

	private final Integer minFloor;

	private final Integer maxFloor;

	public Elevator(Integer id, Integer minFloor, Integer maxFloor) {

		this.id = id;

		this.minFloor = minFloor;

		this.maxFloor = maxFloor;

		this.currentFloor = new AtomicInteger(minFloor);

		this.upRequests = new TreeSet<>();

		this.downRequests = new TreeSet<>((a, b) -> b - a);

		this.elevatorState = new IdleState();
	}

	// ====== Observer Pattern ========
	public void addObserver(ElevatorObserver observer) {
		observers.add(observer);
	}
	public void notifyObservers(String event) {
		observers.forEach(observer -> observer.update(this, event));
	}

	
	// ========= State Pattern ==========
	private void move() {
		elevatorState.move(this);
	}
	
	public void setElevatorState(ElevatorState elevatorState) {
		notifyObservers("State changed to " + elevatorState.getClass().getSimpleName());
		this.elevatorState = elevatorState;
	}
	
	public String getState() {
		return elevatorState.getClass().getSimpleName();
	}

	public void moveUp() {

		int nextFloor = currentFloor.get() + 1;
		validateFloor(nextFloor);
		currentFloor.incrementAndGet();
		notifyObservers("Moving UP -> Floor " + currentFloor.get());
	}

	public void moveDown() {

		int nextFloor = currentFloor.get() - 1;
		validateFloor(nextFloor);
		currentFloor.decrementAndGet();
		notifyObservers("Moving DOWN -> Floor " + currentFloor.get());
	}

	
	//============ Facade Method ===============
	public synchronized void addRequest(Request request) {

		validateFloor(request.getTargetFloor());

		// Elevator already on same floor
		if (request.getTargetFloor() == currentFloor.get()) {
			notifyObservers("Elevator already at floor " + currentFloor.get());
			return;
		}

		notifyObservers("Received request: " + request);
		elevatorState.addRequest(this, request);
		notifyAll();
	}
	
	public synchronized Integer getRequests() {
		return upRequests.size() + downRequests.size();
	}

	public synchronized void stop() {

		notifyObservers("Stopping Elevator");
		this.isRunning = false;
		notifyAll();
	}

	public Direction getDirection() {
		return elevatorState.getDirection();
	}

	@Override
	public void run() {

		while (isRunning) {

			try {
				synchronized (this) {
					while (upRequests.isEmpty() && downRequests.isEmpty() && isRunning) {
						notifyObservers("Waiting for requests...");
						wait();
					}
				}

				if (!isRunning) {
					break;
				}
				
				move();

			} catch (InterruptedException e) {
				
				Thread.currentThread().interrupt();
				isRunning = false;
				
			} catch (Exception e) {
				notifyObservers("Error: " + e.getMessage());
			}
		}
		notifyObservers("Elevator stopped.");
	}

	
	private void validateFloor(int floor) {
		if (floor < minFloor || floor > maxFloor) {
			throw new IllegalArgumentException("Invalid floor: " + floor + ". Valid range is " + minFloor + " to " + maxFloor);
		}
	}
}