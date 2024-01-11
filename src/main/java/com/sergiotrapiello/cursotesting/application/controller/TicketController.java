package com.sergiotrapiello.cursotesting.application.controller;

import com.sergiotrapiello.cursotesting.application.ui.Paths;
import com.sergiotrapiello.cursotesting.application.ui.RequestMapping;
import com.sergiotrapiello.cursotesting.application.ui.ResponseEntity;
import com.sergiotrapiello.cursotesting.domain.api.TicketService;
import com.sergiotrapiello.cursotesting.domain.exception.UnexistingTicketException;

public final class TicketController implements Controller {

	private TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@RequestMapping(Paths.Ticket.ISSUE)
	public ResponseEntity issueTicket() {
		return ResponseEntity.ok(ticketService.issueTicket());
	}

	@RequestMapping(Paths.Ticket.CALCULATE_AMOUNT)
	public ResponseEntity calculateAmount(int ticketNumber) {
		try {
			return ResponseEntity.ok(ticketService.calculateAmount(ticketNumber));
		} catch (UnexistingTicketException e) {
			return ResponseEntity.error("Unexisting ticket for number: " + e.getTicketNumber());
		}
	}

}
