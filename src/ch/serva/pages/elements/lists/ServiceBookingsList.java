package ch.serva.pages.elements.lists;

import java.util.Collections;
import java.util.List;

import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditBookingPage;
import ch.serva.pages.elements.Element;
import ch.serva.tools.Dates;
import ch.serva.tools.Escape;
import ch.serva.tools.comparators.BookingComparator;

/**
 * A list of bookings for a given service.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ServiceBookingsList implements Element {

	private final Service service;

	public ServiceBookingsList(Service service) {
		this.service = service;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// bookings
		List<Booking> bookings = service.getBookings();
		Collections.sort(bookings, new BookingComparator());

		// element
		html.append("<!-- service bookings list -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.bookings()).append("</h4>\n");

		// table
		html.append("<table>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.domain()).append("</td>");
		html.append("<td>").append(dict.from()).append("</td>");
		html.append("<td>").append(dict.to()).append("</td>");
		html.append("<td>").append(dict.info()).append("</td>");
		html.append("<td>").append(dict.payments()).append("</td>");
		html.append("</tr>\n");
		for (Booking booking : bookings) {
			html.append("<tr>");

			// domain
			Domain domain = booking.getDomain();
			html.append("<td>");
			html.append("<a href='?page=" + EditBookingPage.NAME + "&amp;id=" + booking.getId() + "'>");
			html.append(Escape.safeXml(domain.toShortString()));
			html.append("</a>");
			html.append("</td>");

			// from, to and info
			String fromStr = Dates.dateFormat.format(booking.getFrom());
			String toStr = booking.getTo() == null ? "" : Dates.dateFormat.format(booking.getTo());
			html.append("<td>").append(fromStr).append("</td>");
			html.append("<td>").append(toStr).append("</td>");
			html.append("<td>").append(Escape.safeXml(booking.getInfo())).append("</td>");

			// payments
			boolean hasPayments = booking.getPayments().size() > 0;
			html.append("<td>").append(hasPayments ? dict.yes() : "").append("</td>");

			html.append("</tr>\n");
		}

		// close table
		html.append("</table>\n");

		// close element
		html.append("</div>\n\n");
	}

}
