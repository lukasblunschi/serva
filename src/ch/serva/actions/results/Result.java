package ch.serva.actions.results;

/**
 * Result of an action.
 * 
 * @author Lukas Blunschi
 * 
 */
public abstract class Result {

	public final boolean success;

	public String message;

	public Result(boolean success) {
		this.success = success;
		this.message = "";
	}

	public Result(boolean success, String message) {
		this.success = success;
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
