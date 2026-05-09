package com.parking.lot.exceptions;

public class FloorAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FloorAlreadyExistsException(String message) {
		super(message);
	}

}
