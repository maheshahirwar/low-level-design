package com.elevator.system.state;

import com.elevator.system.enums.Direction;
import com.elevator.system.model.Elevator;
import com.elevator.system.model.Request;

public class IdleState implements ElevatorState {

	@Override
	public void move(Elevator elevator) {

		if (!elevator.getUpRequests().isEmpty()) {
			elevator.setElevatorState(new MovingUpState());
		} else if (!elevator.getDownRequests().isEmpty()) {
			elevator.setElevatorState(new MovingDownState());
		}
	}

	@Override
	public void addRequest(Elevator elevator, Request request) {

		if (request.getDirection() == Direction.UP) {

			elevator.getUpRequests().add(request.getTargetFloor());
			elevator.setElevatorState(new MovingUpState());

		} else {

			elevator.getDownRequests().add(request.getTargetFloor());
			elevator.setElevatorState(new MovingDownState());
		}
	}

	@Override
	public Direction getDirection() {

		return Direction.IDLE;
	}
}