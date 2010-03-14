package ch.serva.pages;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.RemoveServiceAction;
import ch.serva.db.Instance;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Doubles;
import ch.serva.tools.Escape;
import ch.serva.tools.TextToHtml;
import ch.serva.tools.comparators.ServiceComparator;

/**
 * A page which lists all services.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ListServicesPage extends AbstractAdminPage {

	public static final String NAME = "listServices";

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.services()).append("</h3>\n\n");

		// action prefixes
		String pActionRemove = "action=" + RemoveServiceAction.NAME + "&amp;";
		String pPage = "page=" + ListServicesPage.NAME + "&amp;";
		String pServiceIdPrefLong = "service_" + Service.F_ID + "=";
		String removePrefix = "?" + pActionRemove + pPage + pServiceIdPrefLong;

		// services
		html.append("<!-- services -->\n");
		html.append("<table class='tablecontent'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.servicename()).append("</td>");
		html.append("<td>").append(dict.price()).append("</td>");
		html.append("<td>").append(dict.description()).append("</td>");
		html.append("<td>").append(dict.actions()).append("</td>");
		html.append("</tr>\n");
		List<Service> services = new Instance(em).getServices();
		Collections.sort(services, new ServiceComparator());
		for (Service service : services) {
			html.append("<tr>");

			// service name
			html.append("<td>");
			html.append("<a href='?page=" + EditServicePage.NAME + "&amp;id=" + service.getId() + "'>");
			html.append(Escape.safeXml(service.getServicename()));
			html.append("</a>");
			html.append("</td>");

			// price and description
			html.append("<td>").append(Doubles.formatter.format(service.getPrice())).append("</td>");
			html.append("<td>").append(TextToHtml.toHtml(Escape.safeXml(service.getDescription()))).append("</td>");

			// actions
			if (service.isRemovable()) {
				html.append("<td><a href='" + removePrefix + service.getId() + "'>" + dict.remove() + "</a></td>");
			} else {
				html.append("<td />");
			}
			html.append("</tr>\n");
		}
		html.append("</table>\n\n");

		// actions
		html.append("<!-- actions -->\n");
		html.append("<div class='content actions'>\n");
		html.append("<span>");
		html.append("<a href='?page=" + EditServicePage.NAME + "&amp;id=" + ServaConstants.NEW + "'>" + dict.add() + "</a>");
		html.append("</span>\n");
		html.append("</div>\n\n");

		return html.toString();
	}

}
