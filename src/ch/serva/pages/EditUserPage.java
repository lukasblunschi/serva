package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.config.Config;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.SendLoginButton;
import ch.serva.pages.elements.UserForm;

/**
 * A page to edit a user.
 * 
 * @author Lukas Blunschi
 * 
 */
public class EditUserPage extends AbstractAdminPage {

	public static final String NAME = "editUser";

	@Override
	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"focus_username\").focus();";
	}

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {

		// config
		Config config = new Config();

		// get entity
		User user = null;
		final String idStr = req.getParameter(User.F_ID);
		if (idStr == null) {
			return "id not given.";
		}
		final boolean isNew = idStr.equals(ServaConstants.NEW);
		if (isNew) {
			user = new User();
		} else {
			try {
				Long id = Long.valueOf(idStr);
				user = em.find(User.class, id);

				// access control
				if (user == null) {
					return "<p class='error content'>user does not exist!</p>";
				}
			} catch (Exception e) {
				return "id not parsable.";
			}
		}

		// create html
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>" + dict.editUser() + "</h3>\n\n");

		// user form
		new UserForm(isNew, user).appendHtml(html, config, dict);

		// send login button
		if (!isNew) {
			new SendLoginButton(user).appendHtml(html, config, dict);
		}

		return html.toString();
	}

}
