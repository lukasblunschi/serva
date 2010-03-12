package ch.serva.db;

import java.util.List;

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

	/**
	 * Test if given username-password combination is unique.
	 * 
	 * @param username
	 * @param password
	 * @param ignoreId
	 *            a single ID might be ignored (e.g. nurse ID on update). Might be null.
	 * @param em
	 * @return
	 */
	public static boolean isUniqueUsernameAndPassword(String username, String password, Long ignoreId, EntityManager em) {

		// look for same username
		List<?> users = em.createQuery("select u from User u where u.username='" + username + "'").getResultList();
		for (Object obj : users) {
			User user = (User) obj;

			// ignore given id (e.g. user id on update)
			if (ignoreId != null && user.getId().equals(ignoreId)) {
				continue;
			}

			// look for same password
			String storedPw = user.getPassword();
			if (storedPw.equals(password)) {
				return false;
			}
		}
		return true;
	}

}
