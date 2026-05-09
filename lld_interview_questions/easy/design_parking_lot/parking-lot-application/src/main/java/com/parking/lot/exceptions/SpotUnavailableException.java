package com.parking.lot.exceptions;

public class SpotUnavailableException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpotUnavailableException(String message) {
		super(message);
	}

}
