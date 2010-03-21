package ch.serva.pages.list;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.RemoveUserAction;
import ch.serva.db.Instance;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.localization.Dictionaries;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.edit.EditUserPage;
import ch.serva.tools.Escape;
import ch.serva.tools.comparators.UserComparator;

/**
 * A page which lists all users.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ListUsersPage extends AbstractAdminPage {

	public static final String NAME = "listUsers";

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// logged in user
		User userLoggedIn = Users.getUserFromSession(req, em);

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.users()).append("</h3>\n\n");

		// action prefixes
		String pActionRemove = "action=" + RemoveUserAction.NAME + "&amp;";
		String pPage = "page=" + ListUsersPage.NAME + "&amp;";
		String pUserIdPrefLong = "user_" + User.F_ID + "=";
		String removePrefix = "?" + pActionRemove + pPage + pUserIdPrefLong;

		// users
		html.append("<!-- user -->\n");
		html.append("<table class='tablecontent'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.username()).append("</td>");
		html.append("<td>").append(dict.password()).append("</td>");
		html.append("<td>").append(dict.isAdmin()).append("</td>");
		html.append("<td>").append(dict.language()).append("</td>");
		html.append("<td>").append(dict.nickname()).append("</td>");
		html.append("<td>").append(dict.firstname()).append("</td>");
		html.append("<td>").append(dict.lastname()).append("</td>");
		html.append("<td>").append(dict.address()).append("</td>");
		html.append("<td>").append(dict.phone()).append("</td>");
		html.append("<td>").append(dict.email()).append("</td>");
		html.append("<td>").append(dict.actions()).append("</td>");
		html.append("</tr>\n");
		List<User> users = new Instance(em).getUsers();
		Collections.sort(users, new UserComparator());
		for (User user : users) {
			html.append("<tr>");

			// username
			html.append("<td>");
			html.append("<a href='?page=" + EditUserPage.NAME + "&amp;id=" + user.getId() + "'>");
			html.append(Escape.safeXml(user.getUsername()));
			html.append("</a>");
			html.append("</td>");

			// password, is admin, ...
			html.append("<td>").append(Escape.safeXml(user.getPassword())).append("</td>");
			html.append("<td>").append(user.getIsAdmin() ? dict.yes() : dict.no()).append("</td>");
			html.append("<td>").append(Dictionaries.getDictionary(user.getLanguage()).getLanguageName()).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getNickname())).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getFirstname())).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getLastname())).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getAddress())).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getPhone())).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getEmail())).append("</td>");
			if (user.isRemovable(userLoggedIn)) {
				html.append("<td><a href='" + removePrefix + user.getId() + "'>" + dict.remove() + "</a></td>");
			} else {
				html.append("<td />");
			}
			html.append("</tr>\n");
		}
		html.append("</table>\n\n");

		// actions
		html.append("<!-- actions -->\n");
		html.append("<div class='content actions'>\n");
		html.append("<span><a href='?page=" + EditUserPage.NAME + "&amp;id=" + ServaConstants.NEW + "'>" + dict.add()
				+ "</a></span>\n");
		html.append("</div>\n\n");

		return html.toString();
	}

}
