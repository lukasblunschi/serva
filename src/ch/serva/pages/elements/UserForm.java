package ch.serva.pages.elements;

import java.util.Map;
import java.util.TreeMap;

import ch.serva.ServaConstants;
import ch.serva.actions.SaveUserAction;
import ch.serva.config.Config;
import ch.serva.db.User;
import ch.serva.localization.Dictionaries;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditUserPage;
import ch.serva.pages.list.ListUsersPage;
import ch.serva.tools.Escape;

/**
 * Form to edit a user.
 * 
 * @author Lukas Blunschi
 * 
 */
public class UserForm implements Element {

	private final boolean isNew;

	private final User user;

	public UserForm(boolean isNew, User user) {
		this.isNew = isNew;
		this.user = user;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// element
		html.append("<!-- user form -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.properties()).append("</h4>\n");

		// form
		String pAction = "action=" + SaveUserAction.NAME + "&amp;";
		String pPage = "page=" + (isNew ? ListUsersPage.NAME : EditUserPage.NAME + "&amp;");
		String pId = isNew ? "" : User.F_ID + "=" + user.getId();
		String action = "?" + pAction + pPage + pId;
		String enc = "multipart/form-data";
		html.append("<form id='user_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");

		// table
		html.append("<table>\n");

		// id (hidden)
		String idStr = isNew ? ServaConstants.NEW : String.valueOf(user.getId());
		html.append("<tr class='hidden'>");
		html.append("<td>").append(dict.id() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='hidden' name='" + User.F_ID + "' value='" + idStr + "' />");
		html.append(idStr);
		html.append("</td>");
		html.append("</tr>\n");

		// username
		String username = Escape.safeXml(user.getUsername());
		html.append("<tr>");
		html.append("<td>").append(dict.username() + ":").append("</td>");
		html.append("<td>");
		html.append("<input id='focus_username' type='text' class='text' name='" + User.F_USERNAME + "' value='" + username
				+ "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// password
		String password = Escape.safeXml(user.getPassword());
		html.append("<tr>");
		html.append("<td>").append(dict.password() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + User.F_PASSWORD + "' value='" + password + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// isadmin
		String isAdminStr = user.getIsAdmin() ? " checked='checked'" : "";
		html.append("<tr>");
		html.append("<td>").append(dict.isAdmin() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='checkbox' name='" + User.F_ISADMIN + "'" + isAdminStr + " />");
		html.append("</td>");
		html.append("</tr>\n");

		// language
		String selValue = user.getLanguage();
		Map<String, String> options = new TreeMap<String, String>();
		for (String langCode : Dictionaries.getLanguageCodes()) {
			options.put(Dictionaries.getDictionary(langCode).getLanguageName(), langCode);
		}
		html.append("<tr>");
		html.append("<td>").append(dict.language() + ":").append("</td>");
		html.append("<td>");
		html.append("<select name='" + User.F_LANGUAGE + "' size='1'>");
		for (Map.Entry<String, String> entry : options.entrySet()) {
			String value = entry.getValue();
			if (value.equals(selValue)) {
				html.append("<option value='" + value + "' selected='selected'>" + entry.getKey() + "</option>");
			} else {
				html.append("<option value='" + value + "'>" + entry.getKey() + "</option>");
			}
		}
		html.append("</select>");
		html.append("</td>");
		html.append("</tr>\n");

		// nickname
		String nickname = Escape.safeXml(user.getNickname());
		html.append("<tr>");
		html.append("<td>").append(dict.nickname() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + User.F_NICKNAME + "' value='" + nickname + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// firstname
		String firstname = Escape.safeXml(user.getFirstname());
		html.append("<tr>");
		html.append("<td>").append(dict.firstname() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + User.F_FIRSTNAME + "' value='" + firstname + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// lastname
		String lastname = Escape.safeXml(user.getLastname());
		html.append("<tr>");
		html.append("<td>").append(dict.lastname() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + User.F_LASTNAME + "' value='" + lastname + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// address
		String address = Escape.safeXml(user.getAddress());
		html.append("<tr>");
		html.append("<td>").append(dict.address() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + User.F_ADDRESS + "' value='" + address + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// phone
		String phone = Escape.safeXml(user.getPhone());
		html.append("<tr>");
		html.append("<td>").append(dict.phone() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + User.F_PHONE + "' value='" + phone + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// email
		String email = Escape.safeXml(user.getEmail());
		html.append("<tr>");
		html.append("<td>").append(dict.email() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + User.F_EMAIL + "' value='" + email + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// save
		html.append("<tr>\n");
		html.append("<td />\n");
		html.append("<td>\n");
		html.append("<input type='submit' value='" + dict.save() + "' />\n");
		html.append("</td>\n");
		html.append("</tr>\n");

		// close table
		html.append("</table>\n");

		// close form
		html.append("</form>\n");

		// close element
		html.append("</div>\n\n");
	}

}
