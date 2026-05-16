package com.elevator.system.state;

import com.elevator.system.enums.Direction;
import com.elevator.system.model.Elevator;
import com.elevator.system.model.Request;

public interface ElevatorState {

	void move(Elevator elevator);
	
	void addRequest(Elevator elevator, Request request);
	
	Direction getDirection();
}
