package com.parking.lot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vehicle {
	private String vehicleNumber;
	private VehicleType vehicleType;
}
