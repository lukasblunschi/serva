package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Result;
import ch.serva.localization.Dictionary;

/**
 * Base class for all user pages.
 * 
 * @author Lukas Blunschi
 * 
 */
public abstract class AbstractUserPage extends AbstractPage {

	public final String getHtml(HttpServletRequest req, EntityManager em, Dictionary dict, Result result) {
		StringBuffer html = new StringBuffer();

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
		html.append(getUserContent(req, em, dict));

		return html.toString();
	}

	public abstract String getUserContent(HttpServletRequest req, EntityManager em, Dictionary dict);

}
