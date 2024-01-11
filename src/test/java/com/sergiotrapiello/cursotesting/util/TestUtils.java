package com.sergiotrapiello.cursotesting.util;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public final class TestUtils {

	private TestUtils() {
		// private constructor for utility class
	}
	
	public static Clock clock(String isoInstant) {
		return Clock.fixed(Instant.parse(isoInstant), ZoneId.systemDefault());
	}
}
