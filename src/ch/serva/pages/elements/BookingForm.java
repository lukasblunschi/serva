package ch.serva.pages.elements;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import ch.serva.ServaConstants;
import ch.serva.actions.SaveBookingAction;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditBookingPage;
import ch.serva.pages.edit.EditDomainPage;
import ch.serva.pages.edit.EditServicePage;
import ch.serva.pages.list.ListBookingsPage;
import ch.serva.tools.Dates;
import ch.serva.tools.Escape;
import ch.serva.tools.html.Select;

/**
 * Form to edit a booking.
 * 
 * @author Lukas Blunschi
 * 
 */
public class BookingForm implements Element {

	private final boolean isNew;

	private final Booking booking;

	private final EntityManager em;

	public BookingForm(boolean isNew, Booking booking, EntityManager em) {
		this.isNew = isNew;
		this.booking = booking;
		this.em = em;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// element
		html.append("<!-- booking form -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.properties()).append("</h4>\n");

		// form
		String pAction = "action=" + SaveBookingAction.NAME + "&amp;";
		String pPage = "page=" + (isNew ? ListBookingsPage.NAME : EditBookingPage.NAME + "&amp;");
		String pId = isNew ? "" : Booking.F_ID + "=" + booking.getId();
		String action = "?" + pAction + pPage + pId;
		String enc = "multipart/form-data";
		html.append("<form id='booking_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");

		// table
		html.append("<table>\n");

		// id (hidden)
		String idStr = isNew ? ServaConstants.NEW : String.valueOf(booking.getId());
		html.append("<tr class='hidden'>");
		html.append("<td>").append(dict.id() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='hidden' name='" + Booking.F_ID + "' value='" + idStr + "' />");
		html.append(idStr);
		html.append("</td>");
		html.append("</tr>\n");

		// domain
		Map<String, String> options = new TreeMap<String, String>();
		List<Domain> domains = new Instance(em).getDomains();
		for (Domain domain : domains) {
			options.put(domain.toShortString(), domain.getId().toString());
		}
		String selValue = isNew ? null : booking.getDomain().getId().toString();
		html.append("<tr>");
		html.append("<td>").append(dict.domain() + ":").append("</td>");
		html.append("<td>");
		new Select("focus_domain", Booking.F_DOMAIN, options, selValue).appendHtml(html);
		if (!isNew) {
			html.append("<a href='?page=" + EditDomainPage.NAME + "&amp;id=" + booking.getDomain().getId() + "'>");
			html.append(" -> ");
			html.append("</a>");
		}
		html.append("</td>");
		html.append("</tr>\n");

		// service
		options.clear();
		List<Service> services = new Instance(em).getServices();
		for (Service service : services) {
			options.put(service.toShortString(), service.getId().toString());
		}
		selValue = isNew ? null : booking.getService().getId().toString();
		html.append("<tr>");
		html.append("<td>").append(dict.service() + ":").append("</td>");
		html.append("<td>");
		new Select(Booking.F_SERVICE, options, selValue).appendHtml(html);
		if (!isNew) {
			html.append("<a href='?page=" + EditServicePage.NAME + "&amp;id=" + booking.getService().getId() + "'>");
			html.append(" -> ");
			html.append("</a>");
		}
		html.append("</td>");
		html.append("</tr>\n");

		// from
		String fromStr = Dates.dateFormat.format(booking.getFrom());
		html.append("<tr>");
		html.append("<td>").append(dict.from() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' name='" + Booking.F_FROM + "' value='" + fromStr + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// to
		String toStr = booking.getTo() == null ? "" : Dates.dateFormat.format(booking.getTo());
		html.append("<tr>");
		html.append("<td>").append(dict.to() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' name='" + Booking.F_TO + "' value='" + toStr + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// info
		String info = Escape.safeXml(booking.getInfo());
		html.append("<tr>");
		html.append("<td>").append(dict.info() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + Booking.F_INFO + "' value='" + info + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// save
		html.append("<tr>\n");
		html.append("<td />\n");
		html.append("<td>\n");
		html.append("<input type='submit' value='" + dict.save() + "' />\n");
		html.append("</td>\n");
		html.append("</tr>\n");

		// close table
		html.append("</table>\n");

		// close form
		html.append("</form>\n");

		// close element
		html.append("</div>\n\n");
	}

}
