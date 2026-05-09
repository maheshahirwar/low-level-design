package com.parking.lot.exceptions;

public class InvalidFloorNumber extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFloorNumber(String message) {
		super(message);
	}

}
