package ch.serva.pages.edit;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.config.Config;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.elements.ServiceForm;

/**
 * A page to edit a service.
 * 
 * @author Lukas Blunschi
 * 
 */
public class EditServicePage extends AbstractAdminPage {

	public static final String NAME = "editService";

	@Override
	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"focus_servicename\").focus();";
	}

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {

		// config
		Config config = new Config();

		// get entity
		Service service = null;
		final String idStr = req.getParameter(Service.F_ID);
		if (idStr == null) {
			return "id not given.";
		}
		final boolean isNew = idStr.equals(ServaConstants.NEW);
		if (isNew) {
			service = new Service();
		} else {
			try {
				Long id = Long.valueOf(idStr);
				service = em.find(Service.class, id);

				// access control
				if (service == null) {
					return "<p class='error content'>service does not exist!</p>";
				}
			} catch (Exception e) {
				return "id not parsable.";
			}
		}

		// create html
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>" + dict.editService() + "</h3>\n\n");

		// service form
		new ServiceForm(isNew, service).appendHtml(html, config, dict);

		return html.toString();
	}

}
