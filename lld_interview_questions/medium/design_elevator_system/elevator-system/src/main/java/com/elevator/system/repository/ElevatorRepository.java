package com.elevator.system.repository;

import java.util.List;

import com.elevator.system.model.Elevator;

public interface ElevatorRepository {

	void save(Elevator elevator);

	Elevator findById(Integer id);

	void update(Elevator elevator);

	List<Elevator> findAll();
}
