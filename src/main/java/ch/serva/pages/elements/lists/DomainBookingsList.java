package ch.serva.pages.elements.lists;

import java.util.Collections;
import java.util.List;

import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;
import ch.serva.tools.comparators.BookingComparator;

/**
 * A list of bookings for a given domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainBookingsList extends AbstractBookingsList implements Element {

	private final Domain domain;

	private final String title;

	private final String pagelink;

	public DomainBookingsList(Domain domain, String title, String pagelink) {
		this.domain = domain;
		this.title = title;
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
		html.append("<h4>").append(title).append("</h4>\n");

		// bookings table
		appendTable(bookings, pagelink, html, config, dict);

		// close element
		html.append("</div>\n\n");
	}

}
