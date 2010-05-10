package ch.serva.export;

/**
 * Interface for all PDF exports.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface PdfExport extends Export {

	String getXslFilename();

	void appendXml(StringBuffer xml);

}
