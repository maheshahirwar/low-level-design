package com.parking.lot.entity;

import lombok.Data;

@Data
public class Spot {
	private String spotId;
	private VehicleType spotType;
	private Vehicle vehicle;
	private boolean isOccupied;

	public Spot(String spotId, VehicleType spotType) {
		this.spotId = spotId;
		this.spotType = spotType;
		this.isOccupied = false;
	}
}
