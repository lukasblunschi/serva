package ch.serva.checks.results;

/**
 * Problem.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckProblem extends CheckResult {

	public CheckProblem() {
		super(CheckResultLevel.PROBLEM);
	}

	public CheckProblem(String message) {
		super(CheckResultLevel.PROBLEM, message);
	}

}
