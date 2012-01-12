package ch.serva.pages.list;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.RemoveUserAction;
import ch.serva.db.Instance;
import ch.serva.db.User;
import ch.serva.db.collections.Users;
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
		// - do not show password
		html.append("<!-- user -->\n");
		html.append("<table class='content'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.username()).append("</td>");
		html.append("<td>").append(Escape.nonBreakingHtml(dict.isAdmin())).append("</td>");
		html.append("<td>").append(dict.language()).append("</td>");
		html.append("<td>").append(dict.nickname()).append("</td>");
		html.append("<td>").append(dict.firstname()).append("</td>");
		html.append("<td>").append(dict.lastname()).append("</td>");
		html.append("<td>").append(dict.address()).append("</td>");
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

			// is admin, language, nickname, firstname, lastname
			html.append("<td>").append(user.getIsAdmin() ? dict.yes() : dict.no()).append("</td>");
			html.append("<td>").append(Dictionaries.getDictionary(user.getLanguage()).getLanguageName()).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getNickname())).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getFirstname())).append("</td>");
			html.append("<td>").append(Escape.safeXml(user.getLastname())).append("</td>");

			// address
			String aDivId = "a_" + user.getId();
			String addressDivId = "address_" + user.getId();
			String address = user.getAddressAndPhoneAsHtml().trim();
			html.append("<td>");
			if (address.length() > 0) {
				html.append("<a id='" + aDivId + "' href='javascript:hideDiv(\"" + aDivId + "\");showDiv(\"" + addressDivId + "\")'>");
				html.append(dict.show());
				html.append("</a>");
				html.append("<div id='" + addressDivId + "' class='hidden'>");
				html.append(address);
				html.append("</div>");
			}
			html.append("</td>");

			// email, actions
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
		html.append("<span><a href='?page=" + EditUserPage.NAME + "&amp;id=" + ServaConstants.NEW + "'>" + dict.add() + "</a></span>\n");
		html.append("</div>\n\n");

		return html.toString();
	}

}
