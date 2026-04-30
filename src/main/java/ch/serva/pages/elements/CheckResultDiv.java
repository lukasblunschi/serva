package ch.serva.pages.elements;

import ch.serva.checks.results.CheckResult;
import ch.serva.checks.results.CheckResultLevel;
import ch.serva.config.Config;
import ch.serva.localization.Dictionary;

/**
 * An embedable check result div.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckResultDiv implements Embedable {

	private final CheckResult result;

	public CheckResultDiv(CheckResult result) {
		this.result = result;
	}

	public void appendEmbedableHtml(StringBuffer html, Config config, Dictionary dict) {
		String msgSuffix = result.message == null ? "" : ": " + result.message;
		if (result.level == CheckResultLevel.NO_PROBLEM) {
			html.append("<div class='noProblem'>" + dict.noProblem() + msgSuffix + "</div>");
		} else if (result.level == CheckResultLevel.MINOR_PROBLEM) {
			html.append("<div class='minorProblem'>" + dict.minorProblem() + msgSuffix + "</div>");
		} else {
			html.append("<div class='problem'>" + dict.problem() + msgSuffix + "</div>");
		}
	}

}
