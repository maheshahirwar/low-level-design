package com.parking.lot.repository;

import java.util.List;

import com.parking.lot.entity.Floor;
import com.parking.lot.entity.Spot;
import com.parking.lot.entity.Ticket;

public interface ParkingRepository {

	public void addSpot(Spot spot);

	public void updateSpot(Spot spot);

	public List<Spot> getAllSpots();

	public Spot getSpot(String spotId);

	public void addFloor(Floor floor);

	public Floor getFloor(int floorNumber);

	public List<Floor> getAllFloors();

	public void saveTicket(Ticket ticket);

	public Ticket getTicket(String ticketId);

	public void removeTicket(String ticketId);
}
