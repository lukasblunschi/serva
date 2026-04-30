package ch.serva.checks.results;

/**
 * No problem.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CheckNoProblem extends CheckResult {

	public CheckNoProblem() {
		super(CheckResultLevel.NO_PROBLEM);
	}

	public CheckNoProblem(String message) {
		super(CheckResultLevel.NO_PROBLEM, message);
	}

}
