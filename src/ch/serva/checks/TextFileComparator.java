package ch.serva.checks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;

/**
 * A text file comparator to match a config file to a template file using a given set of possible replacements.
 * 
 * @author Lukas Blunschi
 * 
 */
public class TextFileComparator {

	/**
	 * Compare the given config file against the given template file using the given set of possible replacements.
	 * 
	 * @param configFile
	 *            config file.
	 * @param templateFile
	 *            template file.
	 * @param replacementMap
	 *            possible replacements.
	 * @return success if equal, failure if not equal.
	 */
	public static Result compare(File configFile, File templateFile, Map<String, String> replacementMap) {
		try {

			// readers
			BufferedReader readerConfig = new BufferedReader(new FileReader(configFile));
			BufferedReader readerTemplate = new BufferedReader(new FileReader(templateFile));

			// loop over all lines in config file
			String lineConfig = null;
			while ((lineConfig = readerConfig.readLine()) != null) {

				// ensure config file is not longer than template file
				String lineTemplate = readerTemplate.readLine();
				if (lineTemplate == null) {
					return new Failure("config file is longer than template file");
				}

				// compare line by line
				if (!lineConfig.equals(lineTemplate)) {

					// try replacements
					boolean matches = false;
					for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
						String regex = entry.getKey();
						String replacement = entry.getValue();

						String lineNew = lineConfig.replaceAll(regex, replacement);
						if (lineNew.equals(lineTemplate)) {
							matches = true;
							break;
						}
					}
					if (!matches) {
						return new Failure("config line '" + lineConfig + "' does not match expected template line '"
								+ lineTemplate + "'.");
					}
				}
			}

			// ensure config file is not shorter than template file
			String lineTemplate = readerTemplate.readLine();
			if (lineTemplate != null) {
				return new Failure("config file is shorter than template file");
			}

		} catch (Exception e) {
			return new Failure("exception in text file comparer: " + e.getMessage());
		}

		return new Success();
	}

}
