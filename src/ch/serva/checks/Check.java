package ch.serva.checks;

import java.util.List;
import java.util.Properties;

import ch.serva.actions.results.Result;

/**
 * Interface for all checks.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface Check {

	/**
	 * Run this check.
	 * 
	 * @param domainname
	 *            domain name.
	 * @param properties
	 *            properties.
	 * @return result.
	 */
	Result run(String domainname, Properties properties);

	/**
	 * Get configuration lines for an other service.
	 * 
	 * @param domainname
	 *            domain name.
	 * @return configuration lines.
	 */
	List<String> getExternalConfigLines(String domainname);

}
