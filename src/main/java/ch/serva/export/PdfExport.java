package ch.serva.export;

import ch.serva.localization.Dictionary;

/**
 * Interface for all PDF exports.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface PdfExport extends Export {

	String getXslFilename();

	void appendXml(StringBuffer xml, Dictionary dict);

}
