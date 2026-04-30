package ch.serva.pages.elements.common;

import ch.serva.config.Config;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;

/**
 * A CSS float clearer.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Clearer implements Element {

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {
		html.append("<!-- clearer -->\n");
		html.append("<div class='clear' style='height:0px;'>&#160;</div>\n\n");
	}

}
