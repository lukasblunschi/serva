package ch.serva.localization;

/**
 * English dictionary.
 * 
 * @author Lukas Blunschi
 */
public class English extends EnglishDatabase implements Dictionary {

	public static final String LANGCODE = "en";

	// -------------------------------------------------------------- languages

	public String getLanguageCode() {
		return LANGCODE;
	}

	public String getLanguageName() {
		return "English";
	}

	// ----------------------------------------------------------- common terms

	public String yes() {
		return "Yes";
	}

	public String no() {
		return "No";
	}

	// --------------------------------------------------------- common actions

	public String actions() {
		return "Actions";
	}

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

	public String remove() {
		return "Remove";
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

	public String sendLogin() {
		return "Send Login";
	}

	// ----------------------------------------------- header line, page titles

	public String welcome() {
		return "Welcome";
	}

	public String hello() {
		return "Hello";
	}

	public String home() {
		return "Home";
	}

	public String admin() {
		return "Admin";
	}

	public String editUser() {
		return "Edit User";
	}

	public String editDomain() {
		return "Edit Domain";
	}

	// ---------------------------------------------------------------- actions

	public String loginInformationSubject() {
		return "Serva Login Information";
	}

	public String loginInformationBody(String username, String password, String address) {
		StringBuffer msg = new StringBuffer();
		msg.append("Hi " + username + ",\n");
		msg.append("\n");
		msg.append("Your login information for " + address + " is:\n");
		msg.append("\n");
		msg.append("Username: " + username + "\n");
		msg.append("Password: " + password + "\n");
		msg.append("\n");
		msg.append("Cheers,\n");
		msg.append("Serva\n");
		return msg.toString();
	}

	public String emailSent() {
		return "Email Sent";
	}

}
