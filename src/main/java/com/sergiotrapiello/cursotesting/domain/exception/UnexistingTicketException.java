package com.sergiotrapiello.cursotesting.domain.exception;

public final class UnexistingTicketException extends RuntimeException {

	private static final long serialVersionUID = 6801525481969571036L;

	private final int ticketNumber;
	
	public UnexistingTicketException(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	public int getTicketNumber() {
		return ticketNumber;
	}

}
