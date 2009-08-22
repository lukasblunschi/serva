package ch.serva.localization;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.serva.SessionConstants;

/**
 * Available dictionaries and functions to retrieve them.
 * 
 * @author Lukas Blunschi
 */
public class Dictionaries {

	private static final Map<String, Dictionary> dictMap;

	static {
		English en = new English();
		dictMap = new HashMap<String, Dictionary>();
		dictMap.put(en.getLanguageCode(), en);
	}

	public static Dictionary getDictionaryByLang(String lang) {
		return dictMap.get(lang);
	}

	public static Dictionary getDictionaryFromSession(HttpServletRequest req) {
		String langcode = (String) req.getSession().getAttribute(SessionConstants.A_LANGUAGE);
		if (langcode == null) {
			// english is default
			return dictMap.get("en");
		} else {
			return getDictionaryByLang(langcode);
		}
	}

}
