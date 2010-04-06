package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.localization.Dictionary;

/**
 * Main page for the users which are not admins.
 * 
 * @author Lukas Blunschi
 * 
 */
public class HomePage extends AbstractUserPage {

	public static final String NAME = "home";

	public String getUserContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// user [not null here]
		// User userLoggedIn = Users.getUserFromSession(req, em);

		// config
		// Config config = new Config();

		// domains
		// List<Domain> domainsAll = userLoggedIn.getDomains();

		return html.toString();
	}

}
