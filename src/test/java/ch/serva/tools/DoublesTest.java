package ch.serva.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.NumberFormat;

import org.junit.jupiter.api.Test;

/**
 * Simple JUnit 5 test for Doubles formatter.
 */
public class DoublesTest {

	@Test
	public void testFormatterTwoDecimalPlaces() {
		NumberFormat fmt = Doubles.formatter;

		// Basic cases
		assertEquals("0.00", fmt.format(0.0));
		assertEquals("1.00", fmt.format(1.0));
		assertEquals("1.23", fmt.format(1.234));
		assertEquals("-1.23", fmt.format(-1.234));

		// Rounding behavior
		assertEquals("1.24", fmt.format(1.235));
	}

}
