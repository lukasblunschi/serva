package ch.serva.tools;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Test dates tools.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DatesTest extends TestCase {

	public static void main(String[] args) throws Exception {
		DatesTest test = new DatesTest();
		test.testFullMonths();
	}

	public void testFullMonths() throws Exception {

		Date d1 = Dates.dateFormat.parse("15.11.2008");
		Date d2 = Dates.dateFormat.parse("12.12.2009");
		Date d3 = Dates.dateFormat.parse("13.01.2010");
		Date d4 = Dates.dateFormat.parse("01.02.2010");
		Date d5 = Dates.dateFormat.parse("24.03.2010");
		Date d6 = Dates.dateFormat.parse("06.04.2010");

		if (Dates.getFullMonths(d1, d6) != (1 + 12 + 3)) {
			fail();
		}
		if (Dates.getFullMonths(d2, d6) != 3) {
			fail();
		}
		if (Dates.getFullMonths(d3, d6) != 2) {
			fail();
		}
		if (Dates.getFullMonths(d4, d6) != 1) {
			fail();
		}
		if (Dates.getFullMonths(d5, d6) != 0) {
			fail();
		}

	}

}
