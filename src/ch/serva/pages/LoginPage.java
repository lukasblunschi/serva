package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.LoginAction;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;

/**
 * A page to login.
 * 
 * @author Lukas Blunschi
 */
public class LoginPage extends AbstractUserPage {

	public static final String NAME = "login";

	@Override
	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"focus_username\").focus();";
	}

	public String getUserContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		html.append("<!-- page title -->\n");
		html.append("<h3 class='content'>" + dict.login() + "</h3>\n\n");

		// opening login box, form and table
		String action = "?action=" + LoginAction.NAME;
		String enc = "enctype='multipart/form-data' ";
		html.append("<!-- login box -->\n");
		html.append("<div class='content'>\n");
		html.append("<form id='login_form' " + enc + "action='" + action + "' method='post'>\n");
		html.append("<table>\n");

		// username
		html.append("<tr>");
		html.append("<td>").append(dict.username()).append("</td>");
		html.append("<td>");
		html.append("<input id='focus_username' type='text' name='" + User.F_USERNAME + "' value='' />");
		html.append("</td>");
		html.append("</tr>\n");

		// password
		html.append("<tr>");
		html.append("<td>").append(dict.password()).append("</td>");
		html.append("<td>");
		html.append("<input type='password' name='" + User.F_PASSWORD + "' value='' />");
		html.append("</td>");
		html.append("</tr>\n");

		// login button
		html.append("<tr>");
		html.append("<td/>");
		html.append("<td>");
		html.append("<input type='submit' value='" + dict.login() + "'/>");
		html.append("</td>");
		html.append("</tr>\n");

		// closing login table, form and box
		html.append("</table>\n");
		html.append("</form>\n");
		html.append("</div>\n\n");

		return html.toString();
	}

}
