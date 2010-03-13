package ch.serva.pages.elements;

import ch.serva.actions.SendLoginAction;
import ch.serva.config.Config;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.pages.EditUserPage;

/**
 * The send login button.
 * 
 * @author Lukas Blunschi
 * 
 */
public class SendLoginButton implements Element {

	private final User user;

	public SendLoginButton(User user) {
		this.user = user;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// send login form
		final boolean hasEmail = user.getEmail() != null && user.getEmail().trim().length() > 0;
		if (hasEmail) {

			String pAction = "action=" + SendLoginAction.NAME + "&amp;";
			String pPage = "page=" + EditUserPage.NAME + "&amp;";
			String pId = User.F_ID + "=" + user.getId();
			String action = "?" + pAction + pPage + pId;
			String enc = "multipart/form-data";

			html.append("<!-- send login button -->\n");
			html.append("<div class='content floatleft'>\n");
			html.append("<h4>").append(dict.actions()).append("</h4>\n");
			html.append("<form id='sendlogin_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");
			html.append("<div>\n");
			html.append("<input type='submit' value='" + dict.sendLogin() + "' />\n");
			html.append("</div>\n");
			html.append("</form>\n");
			html.append("</div>\n\n");
		}

	}

}
