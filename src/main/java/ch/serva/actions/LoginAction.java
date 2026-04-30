package ch.serva.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.User;
import ch.serva.localization.English;
import ch.serva.tools.PostRequest;

/**
 * An action to login to this application.
 * 
 * @author Lukas Blunschi
 */
public class LoginAction implements Action {

	public static final String NAME = "login";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get required parameters
		PostRequest postReq = new PostRequest();
		try {
			postReq.parse(req, null, false);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}
		String username = postReq.getFormField(User.F_USERNAME);
		String enteredPw = postReq.getFormField(User.F_PASSWORD);
		if (username == null) {
			return new Failure("No username given.");
		}
		if (username.length() > 40) {
			return new Failure("Too long username entered.");
		}
		if (enteredPw == null) {
			return new Failure("No password given.");
		}

		// switch on no users
		List<?> users = em.createQuery("select u from User u").getResultList();
		if (users.isEmpty()) {

			// use the first login attempt to create a user with the given
			// username and password
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			User user = new User(username, enteredPw, true, English.LANGCODE, username, "", "", "", "", "");
			em.persist(user);
			tx.commit();

			// store login in session and return success (null)
			req.getSession().setAttribute(ServaConstants.A_USER_ID, (Long) user.getId());
			return new Success();

		} else {

			// query db
			users = em.createQuery("select u from User u where u.username='" + username + "'").getResultList();
			if (users.size() > 0) {
				User user = (User) users.iterator().next();
				String storedPw = user.getPassword();
				if (storedPw.equals(enteredPw)) {

					// store id and langcode in session and return success
					req.getSession().setAttribute(ServaConstants.A_USER_ID, (Long) user.getId());
					req.getSession().setAttribute(ServaConstants.A_LANGUAGE, user.getLanguage());
					return new Success();
				} else {
					return new Failure("Wrong password for user " + username + " entered.");
				}
			} else {
				return new Failure("Username does not exist.");
			}
		}
	}

}
