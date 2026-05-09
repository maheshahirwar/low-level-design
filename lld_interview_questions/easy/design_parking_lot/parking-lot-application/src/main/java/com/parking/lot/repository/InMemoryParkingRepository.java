package com.parking.lot.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.parking.lot.entity.Floor;
import com.parking.lot.entity.Spot;
import com.parking.lot.entity.Ticket;

public class InMemoryParkingRepository implements ParkingRepository {

	private Map<Integer, Floor> parkingLot = new ConcurrentHashMap<>();
	private Map<String, Spot> spotMap = new ConcurrentHashMap<>();
	private Map<String, Ticket> ticketMap = new ConcurrentHashMap<>();

	public void addSpot(Spot spot) {
		spotMap.computeIfAbsent(spot.getSpotId(), k -> spot);
	}

	public void updateSpot(Spot spot) {
		spotMap.computeIfPresent(spot.getSpotId(), (k, v) -> spot);
	}

	@Override
	public void addFloor(Floor floor) {
		parkingLot.computeIfAbsent(floor.getFloorId(), k -> floor);
	}

	@Override
	public Floor getFloor(int floorNumber) {
		return parkingLot.get(floorNumber);
	}

	@Override
	public List<Floor> getAllFloors() {
		return parkingLot.values().stream().toList();
	}

	public List<Spot> getAllSpots() {
		return spotMap.values().stream().toList();
	}

	@Override
	public void saveTicket(Ticket ticket) {
		ticketMap.put(ticket.getTicketId(), ticket);
	}

	@Override
	public Ticket getTicket(String ticketId) {
		return ticketMap.get(ticketId);
	}

	@Override
	public void removeTicket(String ticketId) {
		ticketMap.remove(ticketId);
	}

	@Override
	public Spot getSpot(String spotId) {
		return spotMap.get(spotId);
	}

}
