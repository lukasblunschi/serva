package ch.serva.checks.results;

/**
 * Result of a check.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckResult {

	public final CheckResultLevel level;

	public String message;

	public CheckResult(CheckResultLevel level) {
		this.level = level;
		this.message = null;
	}

	public CheckResult(CheckResultLevel level, String message) {
		this.level = level;
		this.message = message;
	}

	public void appendMessage(String message) {
		if (this.message == null) {
			this.message = message;
		} else {
			this.message += "\n" + message;
		}
	}

}
