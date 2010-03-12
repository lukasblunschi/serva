package ch.serva.pages.elements;

import ch.serva.config.Config;
import ch.serva.localization.Dictionary;

/**
 * An embedable produces a block of HTML which has no comments or trailing empty line.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface Embedable {

	/**
	 * Append embedable HTML content of this element.
	 * 
	 * @param html
	 * @param config
	 * @param dict
	 */
	void appendEmbedableHtml(StringBuffer html, Config config, Dictionary dict);

}
