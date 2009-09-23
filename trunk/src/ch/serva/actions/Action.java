package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Result;

/**
 * Every action has to implement this interface.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface Action {

	/**
	 * Execute this action.
	 * 
	 * @param req
	 * @param em
	 * @return
	 */
	Result execute(HttpServletRequest req, EntityManager em);

}
