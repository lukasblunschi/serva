package ch.serva.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.ChangeLanguageAction;
import ch.serva.actions.LogoutAction;
import ch.serva.actions.results.Result;
import ch.serva.db.User;
import ch.serva.db.collections.Users;
import ch.serva.localization.Dictionaries;
import ch.serva.localization.Dictionary;
import ch.serva.localization.English;
import ch.serva.tools.GetRequest;

/**
 * Base class for all pages.
 * 
 * @author Lukas Blunschi
 * 
 */
public abstract class AbstractPage implements Page {

	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return null;
	}

	public final String getContent(HttpServletRequest req, EntityManager em, Dictionary dict, Result result) {
		StringBuffer html = new StringBuffer();

		// header (welcome and language selector)
		String langCodeStr = (String) req.getSession().getAttribute(ServaConstants.A_LANGUAGE);
		if (langCodeStr == null) {
			langCodeStr = String.valueOf(English.LANGCODE);
		}
		final String selValue = langCodeStr;
		List<String> texts = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for (String langCode : Dictionaries.getLanguageCodes()) {
			values.add(langCode);
			texts.add(Dictionaries.getDictionary(langCode).getLanguageName());
		}
		html.append("<!-- language selector -->\n");
		html.append("<form id='lang_selector_form' action='?' method='get'>\n");
		html.append("<div id='language'>\n");
		html.append("<input type='hidden' name='action' value='" + ChangeLanguageAction.NAME + "' />\n");
		Map<String, String> paramMap = GetRequest.getParameterMap(req);
		paramMap.remove(ServaConstants.A_LANGUAGE);
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			html.append("<input type='hidden' name='" + name + "' value='" + value + "' />\n");
		}
		html.append(dict.welcome()).append("! ");
		String js = "javascript:document.getElementById(\"lang_selector_form\").submit()";
		html.append("<select name='" + ServaConstants.A_LANGUAGE + "' size='1' onchange='" + js + "'>\n");
		for (int i = 0; i < values.size(); i++) {
			String value = values.get(i);
			if (value.equals(selValue)) {
				html.append("<option value='" + value + "' selected='selected'>" + texts.get(i) + "</option>\n");
			} else {
				html.append("<option value='" + value + "'>" + texts.get(i) + "</option>\n");
			}
		}
		html.append("</select>\n");
		html.append("</div>\n");
		html.append("</form>\n\n");

		// menu
		// - link to admin page
		html.append("<!-- menu -->\n");
		html.append("<div id='menu'>\n");
		User user = Users.getUserFromSession(req, em);
		boolean isLoggedIn = user != null;
		boolean isAdmin = user == null ? false : user.getIsAdmin();
		if (isLoggedIn) {
			html.append("<ul>\n");
			html.append("<li><a href='?page=" + HomePage.NAME + "'>" + dict.home() + "</a></li>\n");
			html.append("<li><a href='?page=" + CostsUserPage.NAME + "'>" + dict.costs() + "</a></li>\n");
			if (isAdmin) {
				html.append("<li><a href='?page=" + AdminPage.NAME + "'>" + dict.admin() + "</a></li>\n");
			}
			if (isLoggedIn) {
				html.append("<li><a href='?action=" + LogoutAction.NAME + "'>" + dict.logout() + "</a></li>\n");
			}
			html.append("</ul>\n");
		} else {
			html.append("&#160;\n");
		}
		html.append("</div>\n\n");

		// title
		html.append("<!-- title -->\n");
		html.append("<div id='title'>");
		html.append("Serva <span class='superscript'>" + ServaConstants.VERSION + "</span>");
		html.append("</div>\n\n");

		// horizontal bar
		html.append("<!-- horizontal bar -->\n");
		html.append("<div id='horizontal-bar'>&#160;</div>\n\n");

		// real html content
		html.append(getHtml(req, em, dict, result));

		// footer

		return html.toString();
	}

	/**
	 * Create HTML for this page. No body tag.
	 * 
	 * @param req
	 * @param em
	 * @param dict
	 * @param result
	 *            result of previous action (may be null).
	 * @return
	 */
	public abstract String getHtml(HttpServletRequest req, EntityManager em, Dictionary dict, Result result);

}
