package com.elevator.system.state;

import com.elevator.system.enums.Direction;
import com.elevator.system.model.Elevator;
import com.elevator.system.model.Request;

public class MovingDownState implements ElevatorState {

	@Override
	public void move(Elevator elevator) {

		if (elevator.getDownRequests().isEmpty()) {

			if (!elevator.getUpRequests().isEmpty()) {
				elevator.setElevatorState(new MovingUpState());
			} else {
				elevator.setElevatorState(new IdleState());
			}
			return;
		}

		Integer targetFloor = elevator.getDownRequests().first();
		int currentFloor = elevator.getCurrentFloor().get();

		// Reached destination
		if (currentFloor == targetFloor) {

			elevator.notifyObservers("Reached floor " + targetFloor);
			elevator.getDownRequests().remove(targetFloor);

			if (elevator.getDownRequests().isEmpty()) {
				if (!elevator.getUpRequests().isEmpty()) {
					elevator.setElevatorState(new MovingUpState());
				} else {
					elevator.setElevatorState(new IdleState());
				}
			}
			return;
		}

		if (currentFloor == elevator.getMinFloor()) {

			elevator.notifyObservers("Already at minimum floor.");
			// Remove impossible request
			elevator.getDownRequests().remove(targetFloor);

			// Transition properly
			if (!elevator.getUpRequests().isEmpty()) {
				elevator.setElevatorState(new MovingUpState());
			} else {
				elevator.setElevatorState(new IdleState());
			}
			return;
		}

		elevator.moveDown();

		sleep();
	}

	@Override
	public void addRequest(Elevator elevator, Request request) {

		int currentFloor = elevator.getCurrentFloor().get();

		// Same floor request
		if (request.getTargetFloor() == currentFloor) {
			elevator.notifyObservers("Elevator already at floor " + currentFloor);
			return;
		}
		if (request.getTargetFloor() < currentFloor) {
			elevator.getDownRequests().add(request.getTargetFloor());
		} else {
			elevator.getUpRequests().add(request.getTargetFloor());
		}
	}

	@Override
	public Direction getDirection() {

		return Direction.DOWN;
	}

	private void sleep() {

		try {

			Thread.sleep(1000);

		} catch (InterruptedException e) {

			Thread.currentThread().interrupt();
		}
	}
}