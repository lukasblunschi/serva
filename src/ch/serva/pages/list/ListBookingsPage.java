package ch.serva.pages.list;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.elements.common.Clearer;
import ch.serva.pages.elements.lists.DomainBookingsList;
import ch.serva.pages.elements.lists.ServiceBookingsList;
import ch.serva.pages.elements.selectors.DomainsSelector;
import ch.serva.pages.elements.selectors.ServicesSelector;

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

		// domains selector
		List<Domain> domainsSel = DomainsSelector.getSelectedDomains(req, em, dict, NAME, html);

		// services selector
		List<Service> servicesSel = ServicesSelector.getSelectedServices(req, em, dict, NAME, html);

		// clearer
		new Clearer().appendHtml(html, config, dict);

		// pagelink
		String pPage = "page=" + NAME + "&amp;";
		String pDomainIds = DomainsSelector.getParameterStr(req) + "&amp;";
		String pagelink = pPage + pDomainIds;

		// bookings
		// (from domains or services)
		if (domainsSel.size() > 0) {
			for (Domain domain : domainsSel) {
				String title = domain.toShortString();
				new DomainBookingsList(domain, title, pagelink).appendHtml(html, config, dict);
			}
		} else {
			for (Service service : servicesSel) {
				String title = service.toShortString();
				new ServiceBookingsList(service, title, pagelink).appendHtml(html, config, dict);
			}
		}

		return html.toString();
	}

}
