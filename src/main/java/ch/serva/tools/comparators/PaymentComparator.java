package ch.serva.tools.comparators;

import java.util.Comparator;

import ch.serva.db.Payment;

public class PaymentComparator implements Comparator<Payment> {

	public int compare(Payment payment1, Payment payment2) {
		int result = 0;

		// compare by date
		result = payment1.getDate().compareTo(payment2.getDate());
		if (result == 0) {

			// compare by booking
			result = new BookingComparator().compare(payment1.getBooking(), payment2.getBooking());
		}

		return result;
	}

}
