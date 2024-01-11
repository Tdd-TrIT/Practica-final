package com.sergiotrapiello.cursotesting.infrastructure.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import com.sergiotrapiello.cursotesting.domain.model.Ticket;
import com.sergiotrapiello.cursotesting.domain.spi.TicketRepositoryPort;

public class TicketJdbcRepository implements TicketRepositoryPort {

	private Connection conn;

	public TicketJdbcRepository(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Ticket save(Ticket ticket) {
		String sql = "INSERT INTO TICKET (ISSUED_DATETIME) VALUES (?)";

		try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setObject(1, ticket.getIssuedDateTime());
			statement.executeUpdate();
			ticket.setId(getGeneratedId(statement));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return ticket;
	}

	private int getGeneratedId(PreparedStatement statement) throws SQLException {
		ResultSet generatedKeys = statement.getGeneratedKeys();
		generatedKeys.next();
		return generatedKeys.getInt(1);
	}

	@Override
	public Optional<Ticket> get(int ticketNumber) {
		String sql = "SELECT * FROM TICKET WHERE ID = ?";

		try (PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setInt(1, ticketNumber);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return populateTicket(resultSet);
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private Optional<Ticket> populateTicket(ResultSet resultSet) throws SQLException {
		LocalDateTime issuedDateTime = resultSet.getObject("ISSUED_DATETIME", LocalDateTime.class);
		Clock clock = Clock.fixed(ZonedDateTime.of(issuedDateTime, ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
		Ticket ticket = new Ticket(clock);
		ticket.setId(resultSet.getInt("ID"));
		return Optional.of(ticket);
	}

}
