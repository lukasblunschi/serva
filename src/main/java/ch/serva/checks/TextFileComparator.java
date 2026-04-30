package ch.serva.checks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import ch.serva.checks.results.CheckNoProblem;
import ch.serva.checks.results.CheckProblem;
import ch.serva.checks.results.CheckResult;

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
	 *            possible replacements (template > config).
	 * @return success if equal, failure if not equal.
	 */
	public static CheckResult compare(File configFile, File templateFile, Map<String, String> replacementMap) {
		BufferedReader readerConfig = null;
		BufferedReader readerTemplate = null;
		try {

			// readers
			readerConfig = new BufferedReader(new FileReader(configFile));
			readerTemplate = new BufferedReader(new FileReader(templateFile));

			// loop over all lines in config file
			String lineConfig = null;
			while ((lineConfig = readerConfig.readLine()) != null) {

				// ensure config file is not longer than template file
				String lineTemplate = readerTemplate.readLine();
				if (lineTemplate == null) {
					return new CheckProblem("config file is longer than template file");
				}

				// compare line by line
				if (!lineConfig.equals(lineTemplate)) {

					// try replacements
					boolean matches = false;
					for (Map.Entry<String, String> entry : replacementMap.entrySet()) {
						String regex = entry.getKey();
						String replacement = entry.getValue();

						String lineNew = lineTemplate.replaceAll(regex, replacement);
						if (lineNew.equals(lineConfig)) {
							matches = true;
							break;
						}
					}
					if (!matches) {
						return new CheckProblem("config line '" + lineConfig + "' does not match expected template line '" + lineTemplate
								+ "'.");
					}
				}
			}

			// ensure config file is not shorter than template file
			String lineTemplate = readerTemplate.readLine();
			if (lineTemplate != null) {
				return new CheckProblem("config file is shorter than template file");
			}

		} catch (Exception e) {
			return new CheckProblem("exception in text file comparer: " + e.getMessage());
		} finally {
			try {
				if (readerConfig != null) {
					readerConfig.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (readerTemplate != null) {
					readerTemplate.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new CheckNoProblem();
	}

}
