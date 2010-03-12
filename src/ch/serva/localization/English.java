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

	public String home() {
		return "Home";
	}

	public String admin() {
		return "Admin";
	}

}
