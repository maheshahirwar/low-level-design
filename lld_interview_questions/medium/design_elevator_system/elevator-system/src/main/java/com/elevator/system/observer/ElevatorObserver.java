package com.elevator.system.observer;

import com.elevator.system.model.Elevator;

public interface ElevatorObserver {
	void update(Elevator elevator,String event);
}
