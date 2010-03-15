package ch.serva.pages;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.RemoveBookingAction;
import ch.serva.db.Booking;
import ch.serva.db.Instance;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Dates;
import ch.serva.tools.Escape;
import ch.serva.tools.comparators.BookingComparator;

/**
 * A page which lists all bookings.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ListBookingsPage extends AbstractAdminPage {

	public static final String NAME = "listBookings";

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.bookings()).append("</h3>\n\n");

		// action prefixes
		String pActionRemove = "action=" + RemoveBookingAction.NAME + "&amp;";
		String pPage = "page=" + ListBookingsPage.NAME + "&amp;";
		String pBookingIdPrefLong = "booking_" + Booking.F_ID + "=";
		String removePrefix = "?" + pActionRemove + pPage + pBookingIdPrefLong;

		// bookings
		html.append("<!-- bookings -->\n");
		html.append("<table class='tablecontent'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.domain()).append("</td>");
		html.append("<td>").append(dict.service()).append("</td>");
		html.append("<td>").append(dict.from()).append("</td>");
		html.append("<td>").append(dict.to()).append("</td>");
		html.append("<td>").append(dict.info()).append("</td>");
		html.append("<td>").append(dict.actions()).append("</td>");
		html.append("</tr>\n");
		List<Booking> bookings = new Instance(em).getBookings();
		Collections.sort(bookings, new BookingComparator());
		for (Booking booking : bookings) {
			html.append("<tr>");

			// domain and service
			html.append("<td>").append(Escape.safeXml(booking.getDomain().toShortString())).append("</td>");
			html.append("<td>").append(Escape.safeXml(booking.getService().toShortString())).append("</td>");

			// from, to and info
			String fromStr = Dates.dateFormat.format(booking.getFrom());
			String toStr = booking.getTo() == null ? "" : Dates.dateFormat.format(booking.getTo());
			html.append("<td>").append(fromStr).append("</td>");
			html.append("<td>").append(toStr).append("</td>");
			html.append("<td>").append(Escape.safeXml(booking.getInfo())).append("</td>");

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
		html.append("</table>\n\n");

		// actions
		html.append("<!-- actions -->\n");
		html.append("<div class='content actions'>\n");
		html.append("<span>");
		html.append("<a href='?page=" + EditBookingPage.NAME + "&amp;id=" + ServaConstants.NEW + "'>" + dict.add() + "</a>");
		html.append("</span>\n");
		html.append("</div>\n\n");

		return html.toString();
	}

}
