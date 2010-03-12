package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.localization.Dictionary;

/**
 * Base class for all admin pages.
 * 
 * @author Lukas Blunschi
 * 
 */
public abstract class AbstractAdminPage extends AbstractPage {

	public final String getHtml(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// admin menu
		html.append("<!-- admin menu -->\n");
		html.append("<div id='adminmenu'>\n");
		html.append("<ul>\n");
		html.append("<li><a href='?page=" + ListUsersPage.NAME + "'>" + dict.users() + "</a></li>\n");
		html.append("</ul>\n");
		html.append("</div>\n\n");

		// content
		html.append(getAdminContent(req, em, dict));

		return html.toString();
	}

	public abstract String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict);

}
