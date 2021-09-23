package com.flightApp.exception;

public class AirlineNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AirlineNotFoundException() {
		super();
	}

	public AirlineNotFoundException(final String message) {
		super(message);
	}

}
