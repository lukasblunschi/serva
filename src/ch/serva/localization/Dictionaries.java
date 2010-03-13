package ch.serva.localization;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;

/**
 * Available dictionaries and functions to retrieve them.
 * 
 * @author Lukas Blunschi
 */
public class Dictionaries {

	private static final Map<String, Dictionary> dictionaryMap;

	static {
		dictionaryMap = new TreeMap<String, Dictionary>();
		dictionaryMap.put(English.LANGCODE, new English());
	}

	public static Collection<String> getLanguageCodes() {
		return dictionaryMap.keySet();
	}

	public static Dictionary getDictionary(String langcode) {
		if (langcode == null) {
			langcode = English.LANGCODE;
		}
		Dictionary dict = dictionaryMap.get(langcode);
		return dict == null ? dictionaryMap.get(English.LANGCODE) : dict;
	}

	public static Dictionary getDictionaryFromSession(HttpServletRequest req) {
		String langCodeStr = (String) req.getSession().getAttribute(ServaConstants.A_LANGUAGE);
		if (langCodeStr == null) {
			// english is default
			return getDictionary(English.LANGCODE);
		} else {
			return getDictionary(langCodeStr);
		}
	}
}
