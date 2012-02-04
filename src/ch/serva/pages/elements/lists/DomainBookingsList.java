package ch.serva.pages.elements.lists;

import java.util.Collections;
import java.util.List;

import ch.serva.actions.RemoveBookingAction;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditBookingPage;
import ch.serva.pages.elements.Element;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;
import ch.serva.tools.Escape;
import ch.serva.tools.comparators.BookingComparator;

/**
 * A list of bookings for a given domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainBookingsList implements Element {

	private final Domain domain;

	private final String pagelink;

	public DomainBookingsList(Domain domain, String pagelink) {
		this.domain = domain;
		this.pagelink = pagelink;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// bookings
		List<Booking> bookings = domain.getBookings();
		Collections.sort(bookings, new BookingComparator());

		// element
		html.append("<!-- domain bookings list -->\n");
		html.append("<div class='content'>\n");

		// element title
		html.append("<h4>").append(dict.bookings()).append("</h4>\n");

		// action prefixes
		String pActionRemove = "action=" + RemoveBookingAction.NAME + "&amp;";
		String pBookingIdPrefLong = "booking_" + Booking.F_ID + "=";
		String removePrefix = "?" + pActionRemove + pagelink + "&amp;" + pBookingIdPrefLong;

		// table
		html.append("<table>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.domain()).append("</td>");
		html.append("<td>").append(dict.service()).append("</td>");
		html.append("<td>").append(dict.price()).append("</td>");
		html.append("<td>").append(dict.from()).append("</td>");
		html.append("<td>").append(dict.to()).append("</td>");
		html.append("<td>").append(dict.info()).append("</td>");
		html.append("<td>").append(dict.payments()).append("</td>");
		html.append("<td>").append(dict.actions()).append("</td>");
		html.append("</tr>\n");
		for (Booking booking : bookings) {
			html.append("<tr>");

			// domain and service
			Domain domain = booking.getDomain();
			Service service = booking.getService();

			// domain, service and price
			html.append("<td>").append(Escape.safeXml(domain.toShortString())).append("</td>");
			html.append("<td>").append(Escape.safeXml(service.toShortString())).append("</td>");
			html.append("<td>").append(Doubles.formatter.format(service.getPrice())).append("</td>");

			// from, to and info
			String fromStr = Dates.dateFormat.format(booking.getFrom());
			String toStr = booking.getTo() == null ? "" : Dates.dateFormat.format(booking.getTo());
			html.append("<td>").append(fromStr).append("</td>");
			html.append("<td>").append(toStr).append("</td>");
			html.append("<td>").append(Escape.safeXml(booking.getInfo())).append("</td>");

			// payments
			boolean hasPayments = booking.getPayments().size() > 0;
			html.append("<td>").append(hasPayments ? dict.yes() : "").append("</td>");

			// actions
			html.append("<td>");
			html.append("<a href='?page=" + EditBookingPage.NAME + "&amp;id=" + booking.getId() + "'>");
			html.append(dict.edit());
			html.append("</a>");
			if (booking.isRemovable()) {
				html.append(" <a href='" + removePrefix + booking.getId() + "'>" + dict.remove() + "</a>");
			}
			html.append("</td>");

			html.append("</tr>\n");
		}

		// close table
		html.append("</table>\n");

		// close element
		html.append("</div>\n\n");
	}

}
