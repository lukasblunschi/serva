package ch.serva.pages.list;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.RemoveBookingAction;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.db.collections.Services;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.edit.EditBookingPage;
import ch.serva.pages.elements.adders.BookingsAdder;
import ch.serva.pages.elements.common.Clearer;
import ch.serva.pages.elements.selectors.DomainsSelector;
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

		// config
		Config config = new Config();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.bookings()).append("</h3>\n\n");

		// TODO to work with booking, you can either select a domain OR a service to start with...

		// domains
		List<Domain> domainsSel = DomainsSelector.getSelectedDomains(req, em, dict, ListBookingsPage.NAME, html);

		// multiple service adder
		if (domainsSel.size() == 1) {
			Domain domain = domainsSel.get(0);
			List<Service> servicesNotActive = Services.getNotActive(domain, em);
			String pagelink = "page=" + NAME + "&amp;" + DomainsSelector.P_DOMAIN_IDS + "=" + domain.getId();
			new BookingsAdder(domain, servicesNotActive, pagelink).appendHtml(html, config, dict);
		}

		// clearer
		new Clearer().appendHtml(html, config, dict);

		// action prefixes
		String pActionRemove = "action=" + RemoveBookingAction.NAME + "&amp;";
		String pPage = "page=" + ListBookingsPage.NAME + "&amp;";
		String pDomainIds = DomainsSelector.getParameterStr(req) + "&amp;";
		String pBookingIdPrefLong = "booking_" + Booking.F_ID + "=";
		String removePrefix = "?" + pActionRemove + pPage + pDomainIds + pBookingIdPrefLong;

		// bookings
		html.append("<!-- bookings -->\n");
		html.append("<table class='content'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.domain()).append("</td>");
		html.append("<td>").append(dict.service()).append("</td>");
		html.append("<td>").append(dict.from()).append("</td>");
		html.append("<td>").append(dict.to()).append("</td>");
		html.append("<td>").append(dict.info()).append("</td>");
		html.append("<td>").append(dict.actions()).append("</td>");
		html.append("</tr>\n");
		for (Domain domain : domainsSel) {
			List<Booking> bookings = domain.getBookings();
			Collections.sort(bookings, new BookingComparator());

			// TODO use DomainBookingsList here!

			html.append("<tr><td colspan='6'>&#160;</td></tr>\n");
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
		}
		html.append("</table>\n\n");

		return html.toString();
	}

}
