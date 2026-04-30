package ch.serva.checks.results;

/**
 * Minor problem.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckMinorProblem extends CheckResult {

	public CheckMinorProblem() {
		super(CheckResultLevel.MINOR_PROBLEM);
	}

	public CheckMinorProblem(String message) {
		super(CheckResultLevel.MINOR_PROBLEM, message);
	}

}
