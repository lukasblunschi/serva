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
import ch.serva.pages.elements.selectors.DomainsSelector;
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

	private final String pagename;

	public BookingsAdder(Domain domain, List<Service> services, String pagename) {
		this.domain = domain;
		this.services = services;
		this.pagename = pagename;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// open
		String pAction = "action=" + AddBookingsAction.NAME + "&amp;";
		String pPage = "page=" + pagename + "&amp;";
		String pDomainIds = DomainsSelector.P_DOMAIN_IDS + "=" + domain.getId();
		String action = "?" + pAction + pPage + pDomainIds;
		String enc = "multipart/form-data";
		html.append("<!-- bookings adder -->\n");
		html.append("<div class='content floatleft module'>\n");
		html.append("<form id='bookings_adder_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");
		html.append("<table>\n");
		html.append("<tr>\n");

		// domain (hidden)
		html.append("<td class='hidden'>\n");
		html.append("<input type='hidden' name='" + Booking.F_DOMAIN + "' value='" + domain.getId() + "' />\n");
		html.append("</td>\n");

		// header
		html.append("<td>\n");
		html.append(dict.services() + ":\n");
		html.append("</td>\n");

		// services
		html.append("<td>\n");
		for (Service service : services) {
			String serviceId = Booking.F_SERVICE + "_" + service.getId();
			html.append("<input type='checkbox' name='" + serviceId + "' />");
			html.append(service.getServicename());
			html.append("<br/>\n");
		}
		html.append("</td>\n");

		// from
		String fromStr = Dates.dateFormat.format(new Date());
		html.append("<td>\n");
		html.append(dict.from() + ":");
		html.append("<br/>");
		html.append("<input type='text' name='" + Booking.F_FROM + "' value='" + fromStr + "' />\n");
		html.append("</td>\n");

		// submit
		html.append("<td>\n");
		html.append("<input type='submit' value='" + dict.add() + "' />\n");
		html.append("</td>\n");

		// close
		html.append("</tr>\n");
		html.append("</table>\n");
		html.append("</form>\n");
		html.append("</div>\n\n");
	}

}
