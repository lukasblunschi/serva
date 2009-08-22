package ch.serva.localization;

/**
 * Interface which defines the vocabulary of Serva.
 * 
 * @author Lukas Blunschi
 */
public interface Dictionary {

	// -------------------------------------------------------------- languages

	/**
	 * Get language code.
	 * 
	 * @return language code (ISO 2 letter language name - ISO 639-1).
	 */
	String getLanguageCode();

	// ----------------------------------------------------------------- common

	String login();

	String logout();

	String go();

	String add();

	String save();

	String error();

	String undefined();

	// ----------------------------------------------------------------- header

	String welcome();

	String hello();

	// ------------------------------------------------------------------ users

	String id();

	String username();

	String password();

	String isAdmin();

	String name();

	String language();

	String address();

	String email();

	String mobile();

	// --

	String from();

	String to();

}
