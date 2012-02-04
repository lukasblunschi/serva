package ch.serva.pages.elements.adders;

import java.util.Date;
import java.util.List;

import ch.serva.actions.AddBookingsAction;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;
import ch.serva.tools.Dates;

/**
 * A bookings adder which allows to add several services to a single domain in one operation.
 * 
 * @author Lukas Blunschi
 * 
 */
public class BookingsAdder implements Element {

	private final Domain domain;

	private final List<Service> services;

	private final String pagelink;

	public BookingsAdder(Domain domain, List<Service> services, String pagelink) {
		this.domain = domain;
		this.services = services;
		this.pagelink = pagelink;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// open
		String pAction = "action=" + AddBookingsAction.NAME + "&amp;";
		String action = "?" + pAction + pagelink;
		String enc = "multipart/form-data";
		html.append("<!-- bookings adder -->\n");
		html.append("<div class='content floatleft module'>\n");
		html.append("<form id='bookings_adder_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");
		html.append("<table>\n");

		// hidden row
		// - domain
		html.append("<tr>");
		html.append("<td class='hidden'>");
		html.append("<input type='hidden' name='" + Booking.F_DOMAIN + "' value='" + domain.getId() + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// header row
		html.append("<tr>");
		html.append("<td>" + dict.services() + "</td>");
		html.append("<td>" + dict.from() + "</td>");
		html.append("<td>" + dict.actions() + "</td>");
		html.append("</tr>");

		// content row
		// - services
		// - from
		// - submit
		String fromStr = Dates.dateFormat.format(new Date());
		html.append("<tr>\n");
		html.append("<td>\n");
		for (Service service : services) {
			String serviceId = Booking.F_SERVICE + "_" + service.getId();
			html.append("<input type='checkbox' name='" + serviceId + "' />");
			html.append(service.getServicename());
			html.append("<br/>\n");
		}
		html.append("</td>\n");
		html.append("<td>\n");
		html.append(dict.from() + ":");
		html.append("<br/>");
		html.append("<input type='text' name='" + Booking.F_FROM + "' value='" + fromStr + "' />\n");
		html.append("</td>\n");
		html.append("<td>\n");
		html.append("<input type='submit' value='" + dict.add() + "' />\n");
		html.append("</td>\n");
		html.append("</tr>\n");

		// close
		html.append("</table>\n");
		html.append("</form>\n");
		html.append("</div>\n\n");
	}

}
