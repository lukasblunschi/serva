package ch.serva.pages.elements;

import java.util.List;
import java.util.Properties;

import ch.serva.checks.Check;
import ch.serva.checks.Checks;
import ch.serva.checks.results.CheckResult;
import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Props;

/**
 * Check table.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckTable implements Element {

	private final List<Domain> domains;

	public CheckTable(List<Domain> domains) {
		this.domains = domains;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// properties
		Properties properties = Props.load(this.getClass());

		// html
		html.append("<!-- checks -->\n");

		// loop over checks
		for (String checkDefinition : Checks.getCheckDefinitions()) {
			Check check = Checks.getByDefinition(checkDefinition);

			// header
			html.append("<h4 class='content'>" + checkDefinition + "</h4>\n");

			// run
			List<CheckResult> checkResults = check.run(domains, properties);
			for (CheckResult checkResult : checkResults) {
				html.append("<div class='content'>");
				new CheckResultDiv(checkResult).appendEmbedableHtml(html, config, dict);
				html.append("</div>\n");
			}

			// finish check
			html.append("\n");
		}

		// finish element
		html.append("\n");
	}

}
