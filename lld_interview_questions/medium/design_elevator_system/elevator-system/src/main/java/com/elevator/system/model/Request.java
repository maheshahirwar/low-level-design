package com.elevator.system.model;

import com.elevator.system.enums.Direction;
import com.elevator.system.enums.RequestSource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Request {
	private Integer targetFloor;
	private Direction direction;
	private RequestSource source;
}
