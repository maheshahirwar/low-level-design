package com.elevator.system.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.elevator.system.model.Elevator;

public class InMemoryElevatorRepository implements ElevatorRepository {

	private final Map<Integer, Elevator> elevatorMap = new ConcurrentHashMap<>();
	
	@Override
	public void save(Elevator elevator) {
		elevatorMap.computeIfAbsent(elevator.getId(), k->elevator);
	}

	@Override
	public Elevator findById(Integer id) {
		return elevatorMap.get(id);
	}

	@Override
	public void update(Elevator elevator) {
		elevatorMap.computeIfPresent(elevator.getId(), (k,v)->elevator);
	}

	@Override
	public List<Elevator> findAll() {
		return List.copyOf(elevatorMap.values());
	}

}
