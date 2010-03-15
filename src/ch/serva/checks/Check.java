package ch.serva.checks;

import java.util.List;

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
	 * @return result.
	 */
	Result run();

	/**
	 * Get configuration lines for an other service.
	 * 
	 * @return configuration lines.
	 */
	List<String> getExternalConfigLines();

}
