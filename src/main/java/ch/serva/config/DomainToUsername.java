package ch.serva.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import ch.serva.tools.Props;

/**
 * Domain to username mapping.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainToUsername {

	private static final Logger logger = Logger.getLogger(DomainToUsername.class);

	private final String pathMapping;

	private long lastModified;

	private Map<String, String> mapping;

	// --------------------------------------------------------------- instance

	private static DomainToUsername instance = null;

	private DomainToUsername(String pathMapping) {
		this.pathMapping = pathMapping;
		// 0 if not existing
		this.lastModified = 0L;
		this.mapping = new HashMap<String, String>();
		refreshIfNeeded();
	}

	public static DomainToUsername getInstance() {
		if (instance == null) {
			Properties properties = Props.load(DomainToUsername.class);
			String pathMapping = properties.getProperty(Config.PN_PATH_DOMAINTOUSERNAMEMAPPING);
			instance = new DomainToUsername(pathMapping);
		}
		return instance;
	}

	// ----------------------------------------------------------------- lookup

	/**
	 * Get username for given domainname.
	 * 
	 * @param domainname
	 *            domainname.
	 * @return username assigned to this domain or null if domainname not mapped.
	 */
	public String getUsername(String domainname) {
		refreshIfNeeded();
		return mapping.get(domainname);
	}

	// --------------------------------------------------------------- internal

	private void refreshIfNeeded() {

		// check for changes
		File mappingFile = new File(pathMapping);
		long lastModifiedNew = mappingFile.lastModified();
		if (lastModifiedNew != lastModified) {
			lastModified = lastModifiedNew;

			// reload
			// format: 10000: template.domain
			mapping.clear();
			try (BufferedReader reader = new BufferedReader(new FileReader(mappingFile))) {
				String line = null;
				while ((line = reader.readLine()) != null) {

					// ignore comments and empty lines
					if (line.trim().startsWith("#") || line.trim().length() == 0) {
						continue;
					}

					// parse line
					String[] parts = line.split(":");
					if (parts.length == 2) {
						int domainUserNumber = Integer.parseInt(parts[0]);
						if (domainUserNumber >= 10000 && domainUserNumber < 20000) {
							String domainname = parts[1].trim();
							String username = "user" + domainUserNumber;
							mapping.put(domainname, username);
						}
					}
				}
			} catch (IOException ioe) {
				mapping.clear();
				logger.warn("Exception while reloading domainname to username mapping: " + ioe.getMessage());
			}
		}
	}

}
