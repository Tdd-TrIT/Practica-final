package com.sergiotrapiello.cursotesting.boot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Clock;
import java.util.Set;

import com.sergiotrapiello.cursotesting.application.controller.Controller;
import com.sergiotrapiello.cursotesting.application.controller.TicketController;
import com.sergiotrapiello.cursotesting.application.ui.ConsoleUI;
import com.sergiotrapiello.cursotesting.application.ui.RequestDispatcher;
import com.sergiotrapiello.cursotesting.domain.api.TicketServiceImpl;
import com.sergiotrapiello.cursotesting.infrastructure.jdbc.TicketJdbcRepository;

public class CursotestingProyectoBootApplication {

	public static void main(String[] args) throws SQLException {

		Set<Controller> controllers = Set.of(createTicketController());

		RequestDispatcher dispatcher = new RequestDispatcher(controllers);
		new ConsoleUI(dispatcher).run();
	}

	private static TicketController createTicketController() throws SQLException {
		return new TicketController(new TicketServiceImpl(Clock.systemDefaultZone(), 0.034d, 30d,
				new TicketJdbcRepository(getJdbcConnection())));
	}

	private static Connection getJdbcConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:h2:/pon-aqui-tu-path/h2/testdb", "sa", "");
	}

}
