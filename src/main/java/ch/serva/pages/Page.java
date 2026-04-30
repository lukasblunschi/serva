package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Result;
import ch.serva.localization.Dictionary;

/**
 * Every page has to implement this interface.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface Page {

	/**
	 * Javascript code to execute upon page loading.
	 * 
	 * @param req
	 * @param em
	 * @return javascript code (no single quotes) or null.
	 */
	String getOnloadJs(HttpServletRequest req, EntityManager em);

	/**
	 * Create HTML for this page. No body tag.
	 * <p>
	 * This method generates header, menu and footer.
	 * 
	 * @param req
	 * @param em
	 * @param dict
	 * @param result
	 *            result of previous action (may be null).
	 * @return
	 */
	String getContent(HttpServletRequest req, EntityManager em, Dictionary dict, Result result);

}
