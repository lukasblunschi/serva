package ch.serva.checks;

import java.util.List;
import java.util.Properties;

import ch.serva.checks.results.CheckResult;

/**
 * Interface for all checks.
 * 
 * @author Lukas Blunschi
 * 
 */
public interface Check {

	/**
	 * Run this check to test if service is enabled.
	 * 
	 * @param domainname
	 *            domain name.
	 * @param username
	 *            domain username.
	 * @param properties
	 *            properties.
	 * @return list of results.
	 */
	List<CheckResult> runEnabled(String domainname, String username, Properties properties);

	/**
	 * Run this check to test if service is disabled.
	 * 
	 * @param domainname
	 *            domain name.
	 * @param username
	 *            domain username.
	 * @param properties
	 *            properties.
	 * @return list of results.
	 */
	List<CheckResult> runDisabled(String domainname, String username, Properties properties);

	/**
	 * Get configuration lines for an other service.
	 * 
	 * @param domainname
	 *            domain name.
	 * @return configuration lines.
	 */
	List<String> getExternalConfigLines(String domainname);

}
