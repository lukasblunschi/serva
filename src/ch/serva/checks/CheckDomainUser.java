package ch.serva.checks;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import ch.serva.checks.results.CheckProblem;
import ch.serva.checks.results.CheckResult;
import ch.serva.config.DomainToUsername;
import ch.serva.db.Domain;

/**
 * The domain user check.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckDomainUser implements Check {

	public List<CheckResult> run(List<Domain> domains, Properties properties) {
		List<CheckResult> results = new ArrayList<CheckResult>();

		// check if all domains are mapped to a username
		Set<String> domainNames = new HashSet<String>();
		Set<String> userNames = new HashSet<String>();
		for (Domain domain : domains) {
			String domainname = domain.getDomainname();
			domainNames.add(domainname);
			String username = DomainToUsername.getInstance().getUsername(domainname);
			userNames.add(username);
			if (username == null) {
				results.add(new CheckProblem(domainname + " is not mapped to a username."));
			}
		}

		// check that all home directories are mapped
		File homeBaseDir = new File("/home");
		for (File homeDir : homeBaseDir.listFiles()) {
			String homeDirName = homeDir.getName();
			if (homeDirName.startsWith("user")) {
				if (!userNames.contains(homeDirName)) {
					results.add(new CheckProblem("Unmapped home directory: /home/" + homeDirName));
				}
			}
		}

		return results;
	}

	public List<CheckResult> runEnabled(String domainname, String username, Properties properties) {
		List<CheckResult> results = new ArrayList<CheckResult>();

		// test if home directory exists
		String homeDirPath = "/home/" + username;
		File homeDir = new File(homeDirPath);
		if (homeDir.exists()) {
			if (!homeDir.isDirectory()) {
				results.add(new CheckProblem("Home directory ('" + homeDirPath + "') is not a directory!"));
			}
		} else {
			results.add(new CheckProblem("Home directory ('" + homeDirPath + "') does not exist."));
		}

		return results;
	}

	public List<CheckResult> runDisabled(String domainname, String username, Properties properties) {
		List<CheckResult> results = new ArrayList<CheckResult>();
		results.add(new CheckProblem("The domain user service should be enabled for all domains."));
		return results;
	}

	public List<String> getExternalConfigLines(String domainname) {
		return null;
	}

}
