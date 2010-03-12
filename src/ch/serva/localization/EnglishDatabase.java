package ch.serva.localization;

public class EnglishDatabase implements DictionaryDatabase {

	// ----------------------------------------------------------------- common

	public String id() {
		return "Id";
	}

	public String name() {
		return "Name";
	}

	public String properties() {
		return "Properties";
	}

	// -------------------------------------------------------- users/user page

	public String username() {
		return "Username";
	}

	public String password() {
		return "Password";
	}

	public String isAdmin() {
		return "Is Admin";
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
