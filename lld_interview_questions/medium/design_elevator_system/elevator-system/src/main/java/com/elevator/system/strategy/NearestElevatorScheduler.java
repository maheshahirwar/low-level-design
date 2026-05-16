package com.elevator.system.strategy;

import java.util.List;

import com.elevator.system.enums.Direction;
import com.elevator.system.model.Elevator;
import com.elevator.system.model.Request;

public class NearestElevatorScheduler implements ElevatorScheduler {

	@Override
	public Elevator selectElevator(List<Elevator> elevators,Request request) {

		int minDistance = Integer.MAX_VALUE;

		Elevator nearestElevator = null;

		for (Elevator elevator : elevators) {

			if (isSuitable(elevator, request)) {

				int distance = Math.abs(elevator.getCurrentFloor().get() - request.getTargetFloor());

				if (nearestElevator == null || distance < minDistance
						|| (distance == minDistance 
						&& elevator.getRequests() < nearestElevator.getRequests())) {

					minDistance = distance;

					nearestElevator = elevator;
				}
			}
		}

		// Fallback strategy
		if (nearestElevator == null) {

			return elevators.stream()
					.min((e1, e2) ->Integer.compare(e1.getRequests(),e2.getRequests()))
					.orElse(null);
		}

		return nearestElevator;
	}

	private boolean isSuitable(Elevator elevator, Request request) {

		// Idle elevators are always suitable
		if (elevator.getDirection() == Direction.IDLE) {
			return true;
		}

		// Same direction optimization
		if (elevator.getDirection() == request.getDirection()) {

			if (request.getDirection() == Direction.UP) {

				return elevator.getCurrentFloor().get() <= request.getTargetFloor();

			} else {

				return elevator.getCurrentFloor().get() >= request.getTargetFloor();
			}
		}

		return false;
	}
}