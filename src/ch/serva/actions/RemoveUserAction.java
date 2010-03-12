package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.User;
import ch.serva.db.Users;

/**
 * An action to remove a user.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RemoveUserAction implements Action {

	public static final String NAME = "removeUser";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		String idUserStr = req.getParameter("user_" + User.F_ID);

		// parse parameters
		Long idUser = null;
		try {
			idUser = Long.valueOf(idUserStr);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}

		// entity
		User user = em.find(User.class, idUser);

		// access control
		User userLoggedIn = Users.getUserFromSession(req, em);
		if (user == null) {
			return new Failure("user not found!");
		}

		// check if removable
		if (!user.isRemovable(userLoggedIn)) {
			return new Failure("user not removable!");
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(user);
		tx.commit();

		// result
		return new Success();
	}

}
