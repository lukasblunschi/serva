package ch.serva.actions.results;

/**
 * A success result.
 * 
 * @author Lukas Blunschi
 */
public class Success extends Result {

	/**
	 * Constructor.
	 */
	public Success() {
		super(true, null);
	}

	/**
	 * Constructor with message
	 */
	public Success(String message) {
		super(true, message);
	}

}
