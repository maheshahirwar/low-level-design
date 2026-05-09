package com.parking.lot.strategies;

import java.time.Duration;

import com.parking.lot.entity.Ticket;

public class FlatRateFeeStrategy implements FeeStrategy {

	private static final double FLAT_RATE = 10.0;

	@Override
	public double calculateFee(Ticket ticket) {
//		int hoursParked = (int) Duration.between(ticket.getEntryTime(), java.time.LocalDateTime.now()).toHours();
		long milliSecods = (int) Duration.between(ticket.getEntryTime(), java.time.LocalDateTime.now()).toMillis();
		return milliSecods * FLAT_RATE;
	}

}
