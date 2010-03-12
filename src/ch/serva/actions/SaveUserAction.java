package ch.serva.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Instance;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.tools.Mails;
import ch.serva.tools.PostRequest;

/**
 * An action to save a user.
 * 
 * @author Lukas Blunschi
 * 
 */
public class SaveUserAction implements Action {

	public static final String NAME = "saveUser";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		PostRequest postReq = new PostRequest();
		try {
			postReq.parse(req, null, false);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}
		String idStr = postReq.getFormField(User.F_ID);
		String username = postReq.getFormField(User.F_USERNAME);
		String password = postReq.getFormField(User.F_PASSWORD);
		String isAdminStr = postReq.getFormField(User.F_ISADMIN);
		String language = postReq.getFormField(User.F_LANGUAGE);
		String nickname = postReq.getFormField(User.F_NICKNAME);
		String firstname = postReq.getFormField(User.F_FIRSTNAME);
		String lastname = postReq.getFormField(User.F_LASTNAME);
		String address = postReq.getFormField(User.F_ADDRESS);
		String phone = postReq.getFormField(User.F_PHONE);
		String email = postReq.getFormField(User.F_EMAIL);
		if (username == null || username.length() > 100 || username.trim().length() == 0) {
			return new Failure("No or too long username entered!");
		}
		if (password == null || password.length() > 100 || password.trim().length() == 0) {
			return new Failure("No or too long password entered!");
		}
		if (language == null || language.length() > 100 || language.trim().length() == 0) {
			return new Failure("No or too long language entered!");
		}
		if (nickname == null || nickname.length() > 100) {
			return new Failure("No or too long nickname entered!");
		}
		if (firstname == null || firstname.length() > 100) {
			return new Failure("No or too long firstname entered!");
		}
		if (lastname == null || lastname.length() > 100) {
			return new Failure("No or too long lastname entered!");
		}
		if (address == null || address.length() > 100) {
			return new Failure("No or too long address entered!");
		}
		if (phone == null || phone.length() > 100) {
			return new Failure("No or too long phone entered!");
		}
		if (email == null || email.length() > 100 || email.trim().length() == 0 || !email.matches(Mails.EMAIL_REGEX)) {
			return new Failure("No, too long or invalid email address entered!");
		}

		// parse parameters
		boolean isAdmin = isAdminStr == null ? false : true;

		// get instance
		Instance instance = new Instance(em);

		// get entitiy
		User user = null;
		if (idStr.equals(ServaConstants.NEW)) {
			if (Users.isUniqueUsernameAndPassword(username, password, null, em)) {
				user = new User(username, password, isAdmin, language, nickname, firstname, lastname, address, phone, email);
			} else {
				return new Failure("username-password combination is already in use!");
			}
		} else {
			Long id = null;
			try {
				id = Long.valueOf(idStr);
				user = em.find(User.class, id);
			} catch (Exception e) {
				// ignore parsing problem
			}

			// access control
			if (user == null) {
				return new Failure("user not found!");
			}

			// do not allow to remove last admin
			boolean hasOtherAdmins = false;
			List<User> users = instance.getUsers();
			for (User curUser : users) {
				if (!curUser.getId().equals(user.getId())) {
					if (curUser.getIsAdmin()) {
						hasOtherAdmins = true;
						break;
					}
				}
			}
			if (!hasOtherAdmins && !isAdmin) {
				return new Failure("you should not remove the last admin!");
			}

			if (Users.isUniqueUsernameAndPassword(username, password, id, em)) {
				user.set(username, password, isAdmin, language, nickname, firstname, lastname, address, phone, email);
			} else {
				return new Failure("username-password combination is already in use!");
			}
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(user);
		tx.commit();

		// result
		return new Success();
	}

}
