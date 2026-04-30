package ch.serva.checks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import ch.serva.checks.results.CheckProblem;
import ch.serva.checks.results.CheckResult;
import ch.serva.checks.results.CheckResultLevel;
import ch.serva.config.Config;
import ch.serva.db.Domain;

/**
 * A check for webalizer.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckWebalizer implements Check {

	public List<CheckResult> run(List<Domain> domains, Properties properties) {
		return new ArrayList<CheckResult>();
	}

	public List<CheckResult> runEnabled(String domainname, String username, Properties properties) {
		List<CheckResult> results = new ArrayList<CheckResult>();

		// config file
		checkConfigFile(domainname, username, properties, results);

		// TODO check webalizer dir

		return results;
	}

	public List<CheckResult> runDisabled(String domainname, String username, Properties properties) {
		List<CheckResult> results = new ArrayList<CheckResult>();

		final String pathDomainConfigDir = properties.getProperty(Config.PN_PATH_DOMAINCONFIGDIR);

		// config file
		File configFile = new File(pathDomainConfigDir + "/etc/webalizer/" + domainname + ".conf");
		if (configFile.exists()) {
			String path = configFile.getAbsolutePath();
			results.add(new CheckProblem("webalizer config file " + path + " exists."));
		}

		return results;
	}

	public List<String> getExternalConfigLines(String domainname) {
		// TODO Auto-generated method stub
		return null;
	}

	private void checkConfigFile(String domainname, String username, Properties properties, List<CheckResult> results) {

		// webalizer config file (DOMAINCONFIGDIR/etc/webalizer/DOMAINNAME.conf)
		//
		// template config file: DOMAINCONFIGDIR/etc/webalizer/template.domain.conf

		final String pathDomainConfigDir = properties.getProperty(Config.PN_PATH_DOMAINCONFIGDIR);

		// template file
		File templateFile = new File(pathDomainConfigDir + "/etc/webalizer/template.domain.conf");
		if (!templateFile.canRead()) {
			String path = templateFile.getAbsolutePath();
			results.add(new CheckProblem("webalizer template file " + path + " does not exist or can not be read."));
		}

		// config file
		File configFile = new File(pathDomainConfigDir + "/etc/webalizer/" + domainname + ".conf");
		if (!configFile.canRead()) {
			String path = configFile.getAbsolutePath();
			results.add(new CheckProblem("webalizer config file " + path + " does not exist or can not be read."));
		}

		// only if both files can be read
		if (templateFile.canRead() && configFile.canRead()) {

			// replacement map (template > config)
			Map<String, String> replacementMap = new HashMap<String, String>();
			replacementMap.put("user00000", username);
			replacementMap.put("template.domain", domainname);

			// compare
			CheckResult result = TextFileComparator.compare(configFile, templateFile, replacementMap);
			if (result.level == CheckResultLevel.NO_PROBLEM) {
				result.appendMessage("- config file content as expected.");
			}
			results.add(result);
		}

	}

}
