package com.flightApp.exception;

public class AirlineAlreadyFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AirlineAlreadyFoundException() {
		super();
	}

	public AirlineAlreadyFoundException(final String message) {
		super(message);
	}
}
