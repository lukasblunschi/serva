package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Result;
import ch.serva.localization.Dictionary;

/**
 * Base class for all admin pages.
 * 
 * @author Lukas Blunschi
 * 
 */
public abstract class AbstractAdminPage extends AbstractPage {

	public final String getHtml(HttpServletRequest req, EntityManager em, Dictionary dict, Result result) {
		StringBuffer html = new StringBuffer();

		// admin menu
		html.append("<!-- admin menu -->\n");
		html.append("<div id='adminmenu'>\n");
		html.append("<ul>\n");
		html.append("<li><a href='?page=" + ListUsersPage.NAME + "'>" + dict.users() + "</a></li>\n");
		html.append("<li><a href='?page=" + ListDomainsPage.NAME + "'>" + dict.domains() + "</a></li>\n");
		html.append("<li><a href='?page=" + ListServicesPage.NAME + "'>" + dict.services() + "</a></li>\n");
		html.append("<li><a href='?page=" + ListBookingsPage.NAME + "'>" + dict.bookings() + "</a></li>\n");
		html.append("</ul>\n");
		html.append("</div>\n\n");

		// open admin container
		html.append("<!-- open admin container -->\n");
		html.append("<div id='admincontainer'>\n\n");

		// result
		if (result != null) {
			html.append("<!-- result -->\n");
			if (result.success) {
				html.append("<p class='success content'>" + result.message + "</p>\n\n");
			} else {
				html.append("<p class='error content'>" + result.message + "</p>\n\n");
			}
		}

		// content
		html.append(getAdminContent(req, em, dict));

		// close admin container
		html.append("<!-- close admin container -->\n");
		html.append("</div>\n\n");

		return html.toString();
	}

	public abstract String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict);

}
