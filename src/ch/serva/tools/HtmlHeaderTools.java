package ch.serva.tools;

import java.util.Set;

/**
 * Some functions to create HTML strings for the headers in an HTML document.
 * 
 * @author Lukas Blunschi
 */
public class HtmlHeaderTools {

	/**
	 * Create an XHTML 1.0 Strict header. If any of the parameters is null, it will be ignored.
	 * 
	 * @param langCode
	 *            ISO 2 language code.
	 * @param title
	 *            page title.
	 * @param description
	 *            description meta tag.
	 * @param generator
	 *            generator meta tag.
	 * @param csslinks
	 *            set of css files to be included.
	 * @param jslinks
	 *            set of java script files to be included.
	 * @return XHTML 1.0 Strict header, opening html tag and head tag.
	 */
	public static StringBuffer getXHtmlStrictHeader(String langCode, String title, String description, String generator,
			Set<String> csslinks, Set<String> jslinks) {

		// language code (ISO 2 letter)
		if (langCode == null) {
			langCode = "en";
		}

		StringBuffer html = new StringBuffer();
		html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" ");
		html.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
		html.append("<html xmlns='http://www.w3.org/1999/xhtml' ");
		html.append("xml:lang='" + langCode + "' lang='" + langCode + "'>\n");
		html.append("<head>\n");

		// make it expring
		html.append("   <meta http-equiv='pragma' content='no-cache' />\n");
		html.append("   <meta http-equiv='expires' content='tue, 04 dec 1993 21:29:02 gmt' />\n");
		html.append("   <meta http-equiv='content-type' content='text/html; charset=utf-8' />\n");

		// description, generator and title
		if (description != null) {
			html.append("   <meta name='description' content='" + description + "' />\n");
		}
		if (generator != null) {
			html.append("   <meta name='generator' content='" + generator + "' />\n");
		}
		if (title != null) {
			html.append("   <title>" + title + "</title>\n");
		}

		// css links
		if (csslinks != null) {
			for (String css : csslinks) {
				html.append("   <link rel='stylesheet' type='text/css' href='" + css + "' />\n");
			}
		}

		// js links
		if (jslinks != null) {
			for (String js : jslinks) {
				html.append("   <script type='text/javascript' src='" + js + "'></script>\n");
			}
		}

		html.append("</head>\n");
		return html;
	}

}
