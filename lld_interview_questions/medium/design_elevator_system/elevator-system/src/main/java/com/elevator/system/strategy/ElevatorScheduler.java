package com.elevator.system.strategy;

import java.util.List;

import com.elevator.system.model.Elevator;
import com.elevator.system.model.Request;

public interface ElevatorScheduler {

	Elevator selectElevator(List<Elevator>elevators, Request request);
}
