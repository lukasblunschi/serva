package ch.serva.pages.edit;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.db.collections.Services;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.elements.DomainForm;
import ch.serva.pages.elements.adders.BookingsAdder;
import ch.serva.pages.elements.lists.DomainBookingsList;

/**
 * A page to edit a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class EditDomainPage extends AbstractAdminPage {

	public static final String NAME = "editDomain";

	@Override
	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"focus_domainname\").focus();";
	}

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {

		// config
		Config config = new Config();

		// get entity
		Domain domain = null;
		final String idStr = req.getParameter(Domain.F_ID);
		if (idStr == null) {
			return "id not given.";
		}
		final boolean isNew = idStr.equals(ServaConstants.NEW);
		if (isNew) {
			domain = new Domain();
		} else {
			try {
				Long id = Long.valueOf(idStr);
				domain = em.find(Domain.class, id);

				// access control
				if (domain == null) {
					return "<p class='error content'>domain does not exist!</p>";
				}
			} catch (Exception e) {
				return "id not parsable.";
			}
		}

		// create html
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>" + dict.editDomain() + "</h3>\n\n");

		// domain form
		new DomainForm(isNew, domain, em).appendHtml(html, config, dict);

		// switch on new
		if (!isNew) {

			// pagelink
			String pagelink = "page=" + NAME + "&amp;" + Domain.F_ID + "=" + domain.getId();

			// list related bookings
			new DomainBookingsList(domain, pagelink).appendHtml(html, config, dict);

			// show bookings adder
			List<Service> servicesNotActive = Services.getNotActive(domain, em);
			new BookingsAdder(domain, servicesNotActive, pagelink).appendHtml(html, config, dict);
		}

		return html.toString();
	}

}
