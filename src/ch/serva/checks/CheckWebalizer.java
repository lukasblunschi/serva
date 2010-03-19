package ch.serva.checks;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.config.Config;

/**
 * A check for webalizer.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckWebalizer implements Check {

	public List<String> getExternalConfigLines(String domainname) {
		// TODO Auto-generated method stub
		return null;
	}

	public Result run(String domainname, String username, Properties properties) {

		// webalizer config file (DOMAINCONFIGDIR/etc/webalizer/DOMAINNAME.conf)
		//
		// template config file: DOMAINCONFIGDIR/etc/webalizer/template.domain.conf

		final String pathDomainConfigDir = properties.getProperty(Config.PN_PATH_DOMAINCONFIGDIR);

		// template file
		File templateFile = new File(pathDomainConfigDir + "/etc/webalizer/template.domain.conf");
		if (!templateFile.canRead()) {
			String path = templateFile.getAbsolutePath();
			return new Failure("webalizer template file " + path + " does not exist or can not be read.");
		}

		// config file
		File configFile = new File(pathDomainConfigDir + "/etc/webalizer/" + domainname + ".conf");
		if (!configFile.canRead()) {
			String path = configFile.getAbsolutePath();
			return new Failure("webalizer config file " + path + " does not exist or can not be read.");
		}

		// replacement map (template > config)
		Map<String, String> replacementMap = new HashMap<String, String>();
		replacementMap.put("user00000", username);
		replacementMap.put("template.domain", domainname);

		// compare
		Result result = TextFileComparator.compare(configFile, templateFile, replacementMap);

		// TODO check webalizer dir

		return result;
	}

}
