package com.parking.lot.strategies;

import com.parking.lot.entity.Spot;
import com.parking.lot.entity.VehicleType;

public interface AllocationStrategy {
	public Spot findSpot(VehicleType vehicleType);
}
