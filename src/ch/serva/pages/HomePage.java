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
public class HomePage extends AbstractPage {

	public static final String NAME = "home";

	public String getHtml(HttpServletRequest req, EntityManager em, Dictionary dict) {

		StringBuffer html = new StringBuffer();
		return html.toString();
	}

}
