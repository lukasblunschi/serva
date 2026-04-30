package ch.serva.pages.elements;

import ch.serva.config.Config;
import ch.serva.localization.Dictionary;

/**
 * An element produces a block of HTML with a leading comment and a trailing empty line.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface Element {

	/**
	 * Append HTML content of this element.
	 * 
	 * @param html
	 * @param config
	 * @param dict
	 */
	void appendHtml(StringBuffer html, Config config, Dictionary dict);

}
