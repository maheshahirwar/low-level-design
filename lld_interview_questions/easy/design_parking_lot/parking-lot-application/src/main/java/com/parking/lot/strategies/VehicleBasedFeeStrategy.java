package com.parking.lot.strategies;

import java.time.Duration;
import java.time.LocalDateTime;

import com.parking.lot.entity.Ticket;

public class VehicleBasedFeeStrategy implements FeeStrategy {

	@Override
	public double calculateFee(Ticket ticket) {
		double fee = 0.0;
		int hoursParked = (int) Duration.between(ticket.getEntryTime(), LocalDateTime.now()).toHours();
		switch (ticket.getSpot().getSpotType()) {
		case CAR:
			fee = hoursParked * 5.0;
			break;
		case BIKE:
			fee = hoursParked * 2.0;
			break;
		case TRUCK:
			fee = hoursParked * 10.0;
			break;
		}
		return fee;
	}

}
