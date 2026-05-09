package com.parking.lot.strategies;

import com.parking.lot.entity.Ticket;

public interface FeeStrategy {
	public double calculateFee(Ticket ticket);
}
