package com.parking.lot.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Ticket {
	private String ticketId;
	private Spot spot;
	private LocalDateTime entryTime;
}
