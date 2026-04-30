package ch.serva.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Instance;
import ch.serva.db.User;
import ch.serva.db.collections.Users;
import ch.serva.localization.Dictionaries;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Mails;
import ch.serva.tools.Props;

/**
 * An action to send login information to a single user.
 * 
 * @author Lukas Blunschi
 * 
 */
public class SendLoginAction implements Action {

	public static final String NAME = "sendLogin";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// user
		User userLoggedIn = Users.getUserFromSession(req, em);
		User user = null;
		final String idStr = req.getParameter(User.F_ID);
		if (idStr == null) {
			return new Failure("id not given.");
		}
		if (idStr.equals(ServaConstants.NEW)) {
			return new Failure("save first!");
		} else {
			Long id = Long.valueOf(idStr);
			user = em.find(User.class, id);

			// access control
			if (user == null) {
				return new Failure("user not found!");
			}
		}

		// send login
		return sendLogin(user, userLoggedIn, em);
	}

	public Result sendLogin(User user, User userLoggedIn, EntityManager em) {

		// properties
		Properties props = Props.load(this.getClass());
		String address = props.getProperty("address");

		// get email address
		List<String> addressList = new ArrayList<String>();
		String emailAddress = user.getEmail().trim();
		if (emailAddress.length() == 0) {
			return new Failure("no email address stored.");
		}
		addressList.add(emailAddress);

		// dictionary of user
		Dictionary dictUser = Dictionaries.getDictionary(user.getLanguage());
		Dictionary dictUserLoggedIn = Dictionaries.getDictionary(userLoggedIn.getLanguage());

		// subject and body
		String subject = dictUser.loginInformationSubject();
		String body = dictUser.loginInformationBody(user.getUsername(), user.getPassword(), address);

		// get reply-to addresses (from admins)
		List<String> replyToAddresses = new ArrayList<String>();
		List<User> admins = new Instance(em).getAdmins();
		for (User admin : admins) {
			String emailAddress2 = admin.getEmail().trim();
			if (emailAddress2.length() > 0) {
				replyToAddresses.add(emailAddress2);
			}
		}

		// send
		String result = Mails.send(props, replyToAddresses, addressList, subject, body, false);
		if (result == null) {
			return new Success(dictUserLoggedIn.emailSent());
		} else {
			return new Failure(user.getUsername() + ": " + result);
		}
	}

}
