package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.localization.Dictionary;

/**
 * Every page has to implement this interface.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface Page {

	/**
	 * Create HTML for this page. No body tag.
	 * 
	 * @param req
	 * @param em
	 * @param dict
	 * @return
	 */
	String getHtml(HttpServletRequest req, EntityManager em, Dictionary dict);

	/**
	 * Create HTML for this page. No body tag.
	 * <p>
	 * This method generate header, menu and footer.
	 * 
	 * @param req
	 * @param em
	 * @param dict
	 * @return
	 */
	String getContent(HttpServletRequest req, EntityManager em, Dictionary dict);

}
