package com.parking.lot.strategies;

import java.util.List;

import com.parking.lot.entity.Spot;
import com.parking.lot.entity.VehicleType;
import com.parking.lot.exceptions.SpotUnavailableException;
import com.parking.lot.service.ParkingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllocateNearestSpot implements AllocationStrategy {

	private final ParkingService parkingService;

	@Override
	public Spot findSpot(VehicleType vehicleType) throws SpotUnavailableException {
		List<Spot> spots = parkingService.findAllAvailableSpotsByType(vehicleType);
		if (spots.isEmpty()) {
			throw new SpotUnavailableException("No available spot for vehicle type: " + vehicleType);
		}
		return spots.getFirst(); // Assuming the first spot in the list is the nearest one.
		// this will change based on strategy
	}

}
