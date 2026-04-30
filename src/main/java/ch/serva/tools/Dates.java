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
	 * yyyy-MM-dd
	 */
	public static DateFormat dateFormatTechnical = new SimpleDateFormat("yyyy-MM-dd");

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

	/**
	 * Get next start of month.
	 * 
	 * @param date
	 * @return start of next month.
	 */
	public static Date getNextStartOfMonth(Date date) {

		// test if already at start of month
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day == 1) {
			return date;
		} else {

			// forward to next start of month
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			return cal.getTime();
		}
	}

	/**
	 * Get previous end of month.
	 * 
	 * @param date
	 * @return end of previous month.
	 */
	public static Date getPreviousEndOfMonth(Date date) {

		// test if already at end of month
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int dayMax = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (day == dayMax) {
			return date;
		} else {

			// return to previous end of month
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			return cal.getTime();
		}
	}

	/**
	 * Get today without time components.
	 * 
	 * @return today.
	 */
	public static Date getToday() {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.setTime(new Date());
		cal2.clear();
		cal2.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));

		return cal2.getTime();
	}

}
