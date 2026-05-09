package com.parking.lot.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.parking.lot.entity.Spot;
import com.parking.lot.entity.Ticket;
import com.parking.lot.entity.Vehicle;
import com.parking.lot.strategies.AllocationStrategy;
import com.parking.lot.strategies.FeeStrategy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParkingLot {

	private final ParkingService parkingService;
	private final AllocationStrategy allocationStrategy;
	private final FeeStrategy feeStrategy;

	public Ticket parkVehicle(Vehicle vehicle) {

		Spot spot = allocationStrategy.findSpot(vehicle.getVehicleType());

		spot.setVehicle(vehicle);
		spot.setOccupied(true);

		parkingService.updateSpot(spot);

		Ticket ticket = new Ticket();
		ticket.setTicketId(generateTicketId());
		ticket.setSpot(spot);
		ticket.setEntryTime(LocalDateTime.now());
		parkingService.saveTicket(ticket);
		System.out.println("Parked vehicle " + vehicle.getVehicleNumber() + " at spot " + spot.getSpotId()
				+ ", Ticket ID: " + ticket.getTicketId());
		return ticket;

	}

	private String generateTicketId() {
		return UUID.randomUUID().toString().substring(0, 8); // Shorten the UUID for simplicity
	}

	public double unparkVehicle(String ticketId) {

		Ticket ticket = parkingService.getTicket(ticketId);

		double price = feeStrategy.calculateFee(ticket);

		Spot spot = ticket.getSpot();
		spot.setVehicle(null);
		spot.setOccupied(false);
		parkingService.updateSpot(spot);
		parkingService.removeTicket(ticketId);

		System.out.println(
				"Unparked vehicle from spot " + spot.getSpotId() + ", Ticket ID: " + ticketId + ", Price: " + price);
		return price;
	}
}
