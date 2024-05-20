package ch.serva.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Logo.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Logo {

	private final String docroot;

	public Logo(String docroot) {
		this.docroot = docroot;
	}

	public String toXml() {
		StringBuffer xml = new StringBuffer();
		File file = getLogoFile();
		if (file != null) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					xml.append(line);
				}
			} catch (Exception e) {
				xml = new StringBuffer();
				xml.append("<error>" + e.getMessage() + "</error>");
			}
		}
		return xml.toString();
	}

	/**
	 * 
	 * @return logo file or null if no logo file present.
	 */
	private File getLogoFile() {

		// standalone
		File file = new File(docroot + "/war/images/logo.svg");
		if (file.exists()) {
			return file;
		} else {

			// webapp
			file = new File(docroot + "/images/logo.svg");
			if (file.exists()) {
				return file;
			} else {
				return null;
			}
		}
	}

}
