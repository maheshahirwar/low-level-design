package com.parking.lot;

import java.util.ArrayList;
import java.util.List;

import com.parking.lot.entity.Floor;
import com.parking.lot.entity.Spot;
import com.parking.lot.entity.Ticket;
import com.parking.lot.entity.Vehicle;
import com.parking.lot.entity.VehicleType;
import com.parking.lot.repository.InMemoryParkingRepository;
import com.parking.lot.repository.ParkingRepository;
import com.parking.lot.service.ParkingLot;
import com.parking.lot.service.ParkingService;
import com.parking.lot.strategies.AllocateNearestSpot;
import com.parking.lot.strategies.AllocationStrategy;
import com.parking.lot.strategies.FeeStrategy;
import com.parking.lot.strategies.FlatRateFeeStrategy;

public class MainApplication {

	public static void main(String[] args) {
		System.out.println("========Welcome to the Parking Lot System!=============\n");
		ParkingService parkingService = initializeParkingService();
		AllocationStrategy allocationStrategy = new AllocateNearestSpot(parkingService);
		FeeStrategy feeStrategy = new FlatRateFeeStrategy();
		ParkingLot parkingLot = new ParkingLot(parkingService, allocationStrategy, feeStrategy);

		System.out.println("\nparking vehicles...\n");
		Ticket ticket1 = parkingLot.parkVehicle(new Vehicle("KA-01-HH-1234", VehicleType.CAR));
		Ticket ticket2 = parkingLot.parkVehicle(new Vehicle("KA-01-HH-9999", VehicleType.CAR));
		Ticket ticket3 = parkingLot.parkVehicle(new Vehicle("KA-01-BB-0001", VehicleType.BIKE));
		Ticket ticket4 = parkingLot.parkVehicle(new Vehicle("KA-01-HH-7777", VehicleType.TRUCK));
		Ticket ticket5 = parkingLot.parkVehicle(new Vehicle("KA-01-HH-2701", VehicleType.BIKE));
		Ticket ticket6 = parkingLot.parkVehicle(new Vehicle("KA-01-HH-3141", VehicleType.TRUCK));
		try {
			parkingLot.parkVehicle(new Vehicle("KA-01-HH-9999", VehicleType.CAR));
		} catch (Exception e) {
			System.out.println("Error while parking: " + e.getMessage());
		}

		System.out.println("Unparking vehicles...\n");
		parkingLot.unparkVehicle(ticket1.getTicketId());
		parkingLot.unparkVehicle(ticket3.getTicketId());
		try {
			parkingLot.unparkVehicle("invalid-ticket-id");
		} catch (Exception e) {
			System.out.println("Error while unparking: " + e.getMessage());
		}
		System.out.println("\n========Thank you for using the Parking Lot System!=============");
	}

	private static ParkingService initializeParkingService() {

		// Spot instances
		Spot spot1 = new Spot("F0S1", VehicleType.CAR);
		Spot spot2 = new Spot("F0S2", VehicleType.CAR);
		Spot spot3 = new Spot("F1S1", VehicleType.BIKE);
		Spot spot4 = new Spot("F1S2", VehicleType.BIKE);
		Spot spot5 = new Spot("F2S1", VehicleType.TRUCK);
		Spot spot6 = new Spot("F2S2", VehicleType.TRUCK);

		List<String> spotIds0 = new ArrayList<>();
		spotIds0.add(spot1.getSpotId());
		spotIds0.add(spot2.getSpotId());

		List<String> spotIds1 = new ArrayList<>();
		spotIds1.add(spot3.getSpotId());
		spotIds1.add(spot4.getSpotId());

		List<String> spotIds2 = new ArrayList<>();
		spotIds2.add(spot5.getSpotId());
		spotIds2.add(spot6.getSpotId());
		// Floor instances
		Floor floor0 = new Floor(0, new ArrayList<>());
		Floor floor1 = new Floor(1, new ArrayList<>());
		Floor floor2 = new Floor(2, new ArrayList<>());

		// ParkingRepository and ParkingService instances
		ParkingRepository parkingRepository = new InMemoryParkingRepository();

		ParkingService parkingService = new ParkingService(parkingRepository, 3);

		// Adding floors to the parking lot
		System.out.println(parkingService.addFloor(floor0));
		System.out.println(parkingService.addFloor(floor1));
		System.out.println(parkingService.addFloor(floor2));

		// Adding spots to the parking lot
		System.out.println(parkingService.addSpot(0, spot1));
		System.out.println(parkingService.addSpot(0, spot2));
		System.out.println(parkingService.addSpot(1, spot3));
		System.out.println(parkingService.addSpot(1, spot4));
		System.out.println(parkingService.addSpot(2, spot5));
		System.out.println(parkingService.addSpot(2, spot6));

		return parkingService;
	}

}
