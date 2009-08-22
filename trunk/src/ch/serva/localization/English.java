package ch.serva.localization;

/**
 * The english dictionary.
 * 
 * @author Lukas Blunschi
 */
public class English extends AbstractDictionary {

	// -------------------------------------------------------------- languages

	public String getLanguageCode() {
		return "en";
	}

	// --------------------------------------------------------- common actions

	public String login() {
		return "Login";
	}

	public String logout() {
		return "Logout";
	}

	public String go() {
		return "Go";
	}

	public String add() {
		return "Add";
	}

	public String save() {
		return "Save";
	}

	public String error() {
		return "Error";
	}

	public String undefined() {
		return "Undefined";
	}

	// ----------------------------------------------- header line, page titles

	public String welcome() {
		return "Welcome";
	}

	public String hello() {
		return "Hello";
	}

	// -------------------------------------------------------- users/user page

	public String id() {
		return "Id";
	}

	public String username() {
		return "Username";
	}

	public String password() {
		return "Password";
	}

	public String isAdmin() {
		return "Is Admin";
	}

	public String name() {
		return "Name";
	}

	public String language() {
		return "Language";
	}

	public String address() {
		return "Address";
	}

	public String email() {
		return "Email";
	}

	public String mobile() {
		return "Mobile";
	}

	// --------------------------------------------------------------- bookings

	public String from() {
		return "From";
	}

	public String to() {
		return "To";
	}

}
