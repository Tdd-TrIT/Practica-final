package com.sergiotrapiello.cursotesting.domain.model;

import java.time.Clock;
import java.time.LocalDateTime;

public final class Ticket {

	private Integer id;
	
	private LocalDateTime issuedDateTime;
	
	public Ticket(Clock clock) {
		this.issuedDateTime = LocalDateTime.now(clock);
	}

	public Integer getId() {
		return id;
	}

	public LocalDateTime getIssuedDateTime() {
		return issuedDateTime;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
