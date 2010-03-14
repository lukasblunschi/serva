package ch.serva.tools.html;

import java.util.Map;

/**
 * HTML select tag (no newlines).
 * 
 * @author Lukas Blunschi
 * 
 */
public class Select {

	private final String name;

	private final Map<String, String> options;

	private final String selValue;

	/**
	 * @param name
	 *            input name.
	 * @param options
	 *            possible optioins (key (shown) -> value).
	 * @param selValue
	 *            selected value.
	 */
	public Select(String name, Map<String, String> options, String selValue) {
		this.name = name;
		this.options = options;
		this.selValue = selValue;
	}

	public void appendHtml(StringBuffer html) {
		html.append("<select name='" + name + "' size='1'>");
		for (Map.Entry<String, String> entry : options.entrySet()) {
			String value = entry.getValue();
			if (value.equals(selValue)) {
				html.append("<option value='" + value + "' selected='selected'>" + entry.getKey() + "</option>");
			} else {
				html.append("<option value='" + value + "'>" + entry.getKey() + "</option>");
			}
		}
		html.append("</select>");
	}

}
