package ch.serva.db;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;

/**
 * Tools to work with users.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Users {

	/**
	 * Get logged in user.
	 * 
	 * @param req
	 * @param em
	 * @return user if logged in, null otherwise.
	 */
	public static User getUserFromSession(HttpServletRequest req, EntityManager em) {
		Integer userId = (Integer) req.getSession().getAttribute(ServaConstants.A_USER_ID);
		if (userId == null) {
			return null;
		} else {
			return em.find(User.class, userId);
		}
	}

}
