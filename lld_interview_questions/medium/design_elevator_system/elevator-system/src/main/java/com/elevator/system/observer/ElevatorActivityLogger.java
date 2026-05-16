package com.elevator.system.observer;

import java.time.LocalDateTime;

import com.elevator.system.model.Elevator;

public class ElevatorActivityLogger implements ElevatorObserver {

	@Override
	public void update(Elevator elevator, String event) {
		
		String logMessage = """
				[%s]:[ELEVATOR-%d]: %s
				""".formatted(LocalDateTime.now()
						.toString().substring(0, 22), 
						elevator.getId(),
						event);
		
		System.out.println(logMessage);
	}

}
