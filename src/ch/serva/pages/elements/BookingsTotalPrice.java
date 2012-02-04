package ch.serva.pages.elements;

import java.util.List;

import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Doubles;

/**
 * An element to show the total price for all given active bookings.
 * 
 * @author Lukas Blunschi
 * 
 */
public class BookingsTotalPrice implements Element {

	private List<Booking> bookings;

	public BookingsTotalPrice(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// compute sum
		double sum = 0.0;
		for (Booking booking : bookings) {
			if (booking.isActive()) {
				sum += booking.getService().getPrice();
			}
		}

		// html
		html.append("<!-- bookings total price -->\n");
		html.append("<div class='content'>\n");
		html.append("<h4>" + dict.totalPrice() + "</h4>\n");
		html.append(dict.sumActive() + " = " + Doubles.formatter.format(sum));
		html.append("</div>\n\n");
	}

}
