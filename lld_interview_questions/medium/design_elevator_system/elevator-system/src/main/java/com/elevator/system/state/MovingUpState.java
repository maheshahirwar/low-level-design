package com.elevator.system.state;

import com.elevator.system.enums.Direction;
import com.elevator.system.model.Elevator;
import com.elevator.system.model.Request;

public class MovingUpState implements ElevatorState {

	@Override
	public void move(Elevator elevator) {

		if (elevator.getUpRequests().isEmpty()) {
			if (!elevator.getDownRequests().isEmpty()) {
				elevator.setElevatorState(new MovingDownState());
			} else {
				elevator.setElevatorState(new IdleState());
			}
			return;
		}

		Integer targetFloor = elevator.getUpRequests().first();
		int currentFloor = elevator.getCurrentFloor().get();

		// Reached destination
		if (currentFloor == targetFloor) {

			elevator.notifyObservers("Reached floor " + targetFloor);
			elevator.getUpRequests().remove(targetFloor);

			if (elevator.getUpRequests().isEmpty()) {
				if (!elevator.getDownRequests().isEmpty()) {
					elevator.setElevatorState(new MovingDownState());
				} else {
					elevator.setElevatorState(new IdleState());
				}
			}
			return;
		}

		if (currentFloor == elevator.getMaxFloor()) {

			elevator.notifyObservers("Already at maximum floor.");

			// Remove impossible request
			elevator.getUpRequests().remove(targetFloor);

			// Transition properly
			if (!elevator.getDownRequests().isEmpty()) {
				elevator.setElevatorState(new MovingDownState());
			} else {
				elevator.setElevatorState(new IdleState());
			}
			return;
		}

		elevator.moveUp();
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

		if (request.getTargetFloor() > currentFloor) {
			elevator.getUpRequests().add(request.getTargetFloor());
		} else {
			elevator.getDownRequests().add(request.getTargetFloor());
		}
	}

	@Override
	public Direction getDirection() {

		return Direction.UP;
	}

	private void sleep() {

		try {

			Thread.sleep(1000);

		} catch (InterruptedException e) {

			Thread.currentThread().interrupt();
		}
	}
}