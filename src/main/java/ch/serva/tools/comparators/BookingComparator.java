package ch.serva.tools.comparators;

import java.util.Comparator;

import ch.serva.db.Booking;

public class BookingComparator implements Comparator<Booking> {

	public int compare(Booking booking1, Booking booking2) {
		int result = 0;

		// compare by domain
		result = new DomainComparator().compare(booking1.getDomain(), booking2.getDomain());
		if (result == 0) {

			// compare by service
			result = new ServiceComparator().compare(booking1.getService(), booking2.getService());
			if (result == 0) {

				// compare by from
				result = booking1.getFrom().compareTo(booking2.getFrom());
				if (result == 0) {

					// fallback to id
					result = booking1.getId().compareTo(booking2.getId());
				}
			}
		}

		return result;
	}

}
