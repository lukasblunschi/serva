package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;

/**
 * An action to change the application language.
 * 
 * @author Lukas Blunschi
 */
public class ChangeLanguageAction implements Action {

	public static final String NAME = "changeLanguage";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get language
		String langCodeStr = req.getParameter(ServaConstants.A_LANGUAGE);
		if (langCodeStr == null) {
			return new Failure("No language selected.");
		} else {
			req.getSession().setAttribute(ServaConstants.A_LANGUAGE, langCodeStr);

			// return success
			return new Success();
		}

	}

}
