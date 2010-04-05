package ch.serva.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Tools to work with dates.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Dates {

	/**
	 * dd.MM.yy
	 */
	public static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

	/**
	 * Count the number of full months between the given dates.
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public static int getFullMonths(Date dateFrom, Date dateTo) {

		// ensure meaningful input
		if (dateFrom.after(dateTo)) {
			return -1;
		}

		// get calendars
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(dateFrom);
		cal2.setTime(dateTo);

		// result var
		int count = 0;

		// compare years
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 < year2) {

			// count months until end of year from
			// - month is zero-based (= december is 11)
			int curFrom = cal1.get(Calendar.MONTH) + 1;
			count += 12 - curFrom;

			// count years
			count += 12 * (year2 - (year1 + 1));

			// count months until end date
			// - month is zero-based (= january is 0)
			int curTo = cal2.get(Calendar.MONTH);
			count += curTo;
		} else {

			// equal years

			// count months in between (if any
			int curFrom = cal1.get(Calendar.MONTH);
			int curTo = cal2.get(Calendar.MONTH);
			if (curFrom + 1 < curTo) {
				count += curTo - (curFrom + 1);
			}
		}

		return count;
	}

}
