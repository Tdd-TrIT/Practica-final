package com.sergiotrapiello.cursotesting.domain.api;

import static com.sergiotrapiello.cursotesting.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sergiotrapiello.cursotesting.domain.exception.UnexistingTicketException;
import com.sergiotrapiello.cursotesting.domain.model.Ticket;
import com.sergiotrapiello.cursotesting.domain.spi.TicketRepositoryPort;

class TicketServiceImplTest {

	private TicketRepositoryPort ticketRepositoryPortMock;
	
	@BeforeEach
	void setup() {
		ticketRepositoryPortMock = mock(TicketRepositoryPort.class);
	}
	
	@ParameterizedTest
	@MethodSource({"tickedIssued1430_currentTime1632", "tickedIssued0930_currentTime2227_exceedsDailyMaximum", 
		"ticketIssued2330_currenTime0030nextDay", "moreThan24hours"})
	void shouldCalculateAmount(Clock clockIssuedTicket, Clock clockCalculateTotal, double expectedAmount) {

		// GIVEN
		int ticketNumber = 526;
		double pricePerMin = 0.033d;
		double dailyMaximum = 25d;

		TicketService ticketService = new TicketServiceImpl(clockCalculateTotal, pricePerMin, dailyMaximum, ticketRepositoryPortMock);
		
		when(ticketRepositoryPortMock.get(ticketNumber)).thenReturn(Optional.of(new Ticket(clockIssuedTicket)));
		
		// WHEN
		double amount = ticketService.calculateAmount(ticketNumber);

		// THEN
		assertEquals(expectedAmount, amount, 0.01);
	}
	
	private static Stream<Arguments> tickedIssued1430_currentTime1632(){
		return Stream.of(Arguments.of(clock("2023-09-19T14:30:00.00Z"), clock("2023-09-19T16:32:15.00Z"), 4.03d)); // 122 mins * 0.033€/min
	}
	
	private static Stream<Arguments> tickedIssued0930_currentTime2227_exceedsDailyMaximum(){
		return Stream.of(Arguments.of(clock("2023-09-19T09:15:00.00Z"), clock("2023-09-19T22:27:00.00Z"), 25d));
	}
	
	private static Stream<Arguments> ticketIssued2330_currenTime0030nextDay(){
		return Stream.of(Arguments.of(clock("2023-09-19T23:30:00.00Z"), clock("2023-09-20T00:30:00.00Z"), 60 * 0.033d));
	}

	private static Stream<Arguments> moreThan24hours(){
		return Stream.of(Arguments.of(clock("2023-09-10T21:15:00.00Z"), clock("2023-09-12T08:27:00.00Z"), 25 + 22.18d),
				Arguments.of(clock("2023-09-10T09:00:20.00Z"), clock("2023-09-12T15:00:05.00Z"), 25 + 25 + 11.85),
				Arguments.of(clock("2023-09-10T09:00:00.00Z"), clock("2023-09-15T09:01:00.00Z"), 25 * 5 + 0.033));
	}
	
	@Test
	void shouldFailCalculating_unexistingTicket() {

		// GIVEN
		int unexistingTicketNumber = 666;
		
		TicketService ticketService = new TicketServiceImpl(Clock.systemDefaultZone(), 0.02d, 22d, ticketRepositoryPortMock);

		when(ticketRepositoryPortMock.get(anyInt())).thenReturn(Optional.empty());
		
		// WHEN
		Executable executable = () -> ticketService.calculateAmount(unexistingTicketNumber);

		// THEN
		UnexistingTicketException e = assertThrows(UnexistingTicketException.class, executable);
		assertEquals(unexistingTicketNumber, e.getTicketNumber());
	}
	

}
