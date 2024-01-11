package com.sergiotrapiello.cursotesting.domain.api;

import com.sergiotrapiello.cursotesting.domain.model.Ticket;

public interface TicketService {

	Ticket issueTicket();

	double calculateAmount(int ticketNumber);

}
