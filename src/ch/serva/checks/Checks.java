package ch.serva.checks;

import java.util.ArrayList;
import java.util.List;

/**
 * Tools to work with checks.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Checks {

	/**
	 * 
	 * @return list of all check definitions (hardcoded for the moment:-)
	 */
	public static List<String> getCheckDefinitions() {
		List<String> checkDefinitions = new ArrayList<String>();
		checkDefinitions.add("java:" + CheckWebalizer.class.getName());
		return checkDefinitions;
	}

	/**
	 * Get check by providing a definition.
	 * 
	 * @param checkDefinition
	 * @return check or null if check not found.
	 */
	public static Check getByDefinition(String checkDefinition) {
		if (checkDefinition.equals("java:" + CheckWebalizer.class.getName())) {
			return new CheckWebalizer();
		} else {
			return null;
		}
	}

}
