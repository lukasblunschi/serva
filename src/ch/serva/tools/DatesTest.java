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
		Date d2 = Dates.dateFormat.parse("10.02.2009");
		Date d3 = Dates.dateFormat.parse("12.12.2009");
		Date d4 = Dates.dateFormat.parse("13.01.2010");
		Date d5 = Dates.dateFormat.parse("01.02.2010");
		Date d6 = Dates.dateFormat.parse("24.03.2010");
		Date d7 = Dates.dateFormat.parse("06.04.2010");
		Date d8 = Dates.dateFormat.parse("05.05.2010");

		if (Dates.getFullMonths(d1, d7) != (1 + 12 + 3)) {
			fail();
		}
		if (Dates.getFullMonths(d2, d8) != 14) {
			fail();
		}
		if (Dates.getFullMonths(d3, d7) != 3) {
			fail();
		}
		if (Dates.getFullMonths(d4, d7) != 2) {
			fail();
		}
		if (Dates.getFullMonths(d5, d7) != 1) {
			fail();
		}
		if (Dates.getFullMonths(d6, d7) != 0) {
			fail();
		}

	}

}
