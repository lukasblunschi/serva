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

		// open admin container
		html.append("<!-- open admin container -->\n");
		html.append("<div id='admincontainer'>\n\n");

		// content
		html.append(getAdminContent(req, em, dict));

		// close admin container
		html.append("<!-- close admin container -->\n");
		html.append("</div>\n\n");

		return html.toString();
	}

	public abstract String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict);

}
