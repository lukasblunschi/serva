package ch.serva.pages.elements.selectors;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.serva.config.Config;
import ch.serva.db.Instance;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;

/**
 * A user selector.
 * 
 * @author Lukas Blunschi
 * 
 */
public class UserSelector implements Element {

	private final User userSel;

	private final String page;

	private final Instance instance;

	public UserSelector(User userSel, String page, Instance instance) {
		this.userSel = userSel;
		this.page = page;
		this.instance = instance;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// prepare values
		Map<String, String> options = new TreeMap<String, String>();
		List<User> users = instance.getUsers();
		for (User user : users) {
			options.put(user.toShortString(), user.getId().toString());
		}

		// selected value
		String selValue = userSel.getId().toString();

		// html
		html.append("<!-- user selector -->\n");
		html.append("<form id='user_selector' action='?' method='get'>\n");
		html.append("<div class='content'>\n");
		html.append("<input type='hidden' name='page' value='" + page + "' />\n");
		html.append(dict.user() + ":\n");
		String onchange = "onchange='document.getElementById(\"user_selector\").submit();'";
		html.append("<select id='focus_userselector' name='user_" + User.F_ID + "' " + onchange + " size='1'>\n");
		for (Map.Entry<String, String> entry : options.entrySet()) {
			String value = entry.getValue();
			if (value.equals(selValue)) {
				html.append("<option value='" + value + "' selected='selected'>" + entry.getKey() + "</option>\n");
			} else {
				html.append("<option value='" + value + "'>" + entry.getKey() + "</option>\n");
			}
		}
		html.append("</select>\n");
		html.append("</div>\n");
		html.append("</form>\n\n");
	}

}
