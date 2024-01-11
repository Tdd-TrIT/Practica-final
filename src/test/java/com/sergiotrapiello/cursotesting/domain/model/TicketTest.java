package com.sergiotrapiello.cursotesting.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Clock;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.sergiotrapiello.cursotesting.util.TestUtils;

class TicketTest {

	@Test
	void shouldCreate() {

		// GIVEN
		Clock clock = TestUtils.clock("2023-11-06T08:00:00.00Z");

		// WHEN
		Ticket ticket = new Ticket(clock);

		// THEN
		assertEquals(LocalDateTime.now(clock), ticket.getIssuedDateTime());
		assertNull(ticket.getId());
	}

}
