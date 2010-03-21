package ch.serva.pages.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ch.serva.checks.Check;
import ch.serva.checks.Checks;
import ch.serva.checks.results.CheckResult;
import ch.serva.checks.results.CheckResultLevel;
import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Props;

public class DomainsServicesCheckTable implements Element {

	private final List<Domain> domains;

	private final List<Service> services;

	public DomainsServicesCheckTable(List<Domain> domains, List<Service> services) {
		this.domains = domains;
		this.services = services;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// properties
		Properties properties = Props.load(this.getClass());

		// loop over domains
		html.append("<!-- domains services check table -->\n");
		html.append("<table class='tablecontent'>\n");
		for (Domain domain : domains) {
			String domainname = domain.getDomainname();
			String username = domain.getUsername();

			// header
			html.append("<tr>");
			html.append("<td colspan='2'><h4>" + domain.toShortString() + "</h4></td>");
			html.append("</tr>\n");

			// loop over services
			for (Service service : services) {

				// check
				Check check = Checks.getByDefinition(service.getCheckDefinition());
				List<CheckResult> checkResults = null;

				// switch on service being booked and service having check
				final boolean isBooked = domain.isServiceBooked(service);
				final boolean hasCheck = check != null;
				if (hasCheck) {
					if (isBooked) {
						checkResults = check.runEnabled(domainname, username, properties);
					} else {
						checkResults = check.runDisabled(domainname, username, properties);
					}
				} else {
					checkResults = new ArrayList<CheckResult>();

				}

				// look for problems
				boolean hasProblems = false;
				for (CheckResult checkResult : checkResults) {
					if (checkResult.level != CheckResultLevel.NO_PROBLEM) {
						hasProblems = true;
						break;
					}
				}

				// show if is booked or has problems
				if (isBooked || hasProblems) {
					String servicenameItem = (isBooked ? "+" : "-") + " " + service.getServicename();
					html.append("<tr>");
					html.append("<td>").append(servicenameItem).append("</td>");
					html.append("<td>");
					for (CheckResult checkResult : checkResults) {
						new CheckResultDiv(checkResult).appendEmbedableHtml(html, config, dict);
					}
					html.append("</td>");
					html.append("</tr>\n");
				}
			}
		}
		html.append("</table>\n\n");
	}

}
