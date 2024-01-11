package com.sergiotrapiello.cursotesting.domain.spi;

import java.util.Optional;

import com.sergiotrapiello.cursotesting.domain.model.Ticket;

public interface TicketRepositoryPort {

	Ticket save(Ticket ticket);

	Optional<Ticket> get(int ticketNumber);
	
}
