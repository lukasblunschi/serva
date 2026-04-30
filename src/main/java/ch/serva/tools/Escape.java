package ch.serva.tools;

/**
 * Tools for escaping 'dangerous' characters.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Escape {

	/**
	 * Escape the following characters (these are the predefined entities in XML):
	 * <ul>
	 * <li>" = quotation mark
	 * <li>& = ampersand
	 * <li>' = apostrophe quote
	 * <li>&lt; = less-than sign
	 * <li>&gt; = greater-than sign
	 * </ul>
	 * 
	 * @param input
	 * @return
	 */
	public static String safeXml(String input) {

		// ensure safe input
		if (input == null) {
			return "";
		}

		// this must be first!
		input = input.replaceAll("&", "&amp;");

		// now the rest
		input = input.replaceAll("\"", "&quot;");
		input = input.replaceAll("'", "&apos;");
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		return input;
	}

	/**
	 * Revert safe XML to plain XML.
	 * 
	 * @param input
	 *            safe XML
	 * @return plain XML
	 */
	public static String safeXmlRevert(String input) {

		// ensure safe input
		if (input == null) {
			return "";
		}

		// first the rest (in reverse order)
		input = input.replaceAll("&gt;", ">");
		input = input.replaceAll("&lt;", "<");
		input = input.replaceAll("&apos;", "'");
		input = input.replaceAll("&quot;", "\"");

		// now the ampersand
		input = input.replaceAll("&amp;", "&");

		return input;
	}

	/**
	 * Create non-breaking HTML.
	 * 
	 * @param input
	 * @return non-breaking HTML.
	 */
	public static String nonBreakingHtml(String input) {
		input = input.replaceAll(" ", "&#160;");
		return input;
	}

}
