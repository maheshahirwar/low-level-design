package com.parking.lot.service;

import java.util.List;

import com.parking.lot.entity.Floor;
import com.parking.lot.entity.Spot;
import com.parking.lot.entity.Ticket;
import com.parking.lot.entity.VehicleType;
import com.parking.lot.exceptions.FloorAlreadyExistsException;
import com.parking.lot.exceptions.InvalidFloorException;
import com.parking.lot.exceptions.InvalidFloorNumber;
import com.parking.lot.exceptions.InvalidTicketException;
import com.parking.lot.repository.ParkingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParkingService {

	private final ParkingRepository parkingRepository;

	private final int totalFloors;

	public String addSpot(int floorNumber, Spot spot) {

		Floor floor = parkingRepository.getFloor(floorNumber);
		if (floor == null) {
			throw new InvalidFloorException("Floor number " + floorNumber + " does not exist.");
		}
		parkingRepository.addSpot(spot);
		floor.getSpotIds().add(spot.getSpotId());
		return "Spot " + spot + " added successfully to floor " + floorNumber;
	}

	public String updateSpot(Spot spot) {
		parkingRepository.updateSpot(spot);
		return "Spot updated successfully with spot id " + spot.getSpotId();
	}

	public String addFloor(Floor floor) {

		if (floor.getFloorId() < 0 || floor.getFloorId() >= totalFloors) {
			throw new InvalidFloorNumber("Floor number must be between 0 and " + (totalFloors - 1));
		}
		if (parkingRepository.getFloor(floor.getFloorId()) != null) {
			throw new FloorAlreadyExistsException("Floor number " + floor.getFloorId() + " already exists.");
		}
		parkingRepository.addFloor(floor);
		return "Floor added successfully with floor number " + floor.getFloorId();
	}

	public Floor getFloor(int floorNumber) {

		Floor floor = parkingRepository.getFloor(floorNumber);
		if (floor == null) {
			throw new InvalidFloorException("Floor number " + floorNumber + " does not exist.");
		}
		return floor;
	}

	public List<Floor> getAllFloors() {
		return parkingRepository.getAllFloors();
	}

	public List<Spot> findAllAvailableSpotsByType(VehicleType vehicleType) {
		return parkingRepository.getAllSpots().stream()
				.filter(spot -> spot.getSpotType() == vehicleType && !spot.isOccupied()).toList();
	}

	public void saveTicket(Ticket ticket) throws InvalidTicketException {
		parkingRepository.saveTicket(ticket);
	}

	public Ticket getTicket(String ticketId) {
		Ticket ticket = parkingRepository.getTicket(ticketId);
		if (ticket == null) {
			throw new InvalidTicketException("Invalid ticket ID: " + ticketId);
		}
		return ticket;
	}

	public void removeTicket(String ticketId) {
		parkingRepository.removeTicket(ticketId);
	}

}
