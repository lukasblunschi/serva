package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.localization.Dictionary;

/**
 * Entry page for the admin area.
 * 
 * @author Lukas Blunschi
 * 
 */
public class AdminPage extends AbstractAdminPage {

	public static final String NAME = "admin";

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.admin()).append("</h3>\n\n");

		return html.toString();
	}

}
