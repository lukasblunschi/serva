package ch.serva.pages.elements.lists;

import java.util.List;

import ch.serva.actions.RemoveBookingAction;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditBookingPage;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;
import ch.serva.tools.Escape;

/**
 * An abstract bookings list.
 * 
 * @author Lukas Blunschi
 * 
 */
public abstract class AbstractBookingsList {

	protected void appendTable(List<Booking> bookings, String pagelink, StringBuffer html, Config config, Dictionary dict) {

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
	}

}
