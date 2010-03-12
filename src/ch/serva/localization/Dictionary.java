package ch.serva.localization;

/**
 * Dictionary for this application.
 * 
 * @author Lukas Blunschi
 */
public interface Dictionary extends DictionaryDatabase {

	// -------------------------------------------------------------- languages

	/**
	 * Get language code.
	 * 
	 * @return language code (ISO 2 letter language name - ISO 639-1).
	 */
	String getLanguageCode();

	String getLanguageName();

	// --------------------------------------------------------- common actions

	String login();

	String logout();

	String go();

	String add();

	String save();

	String error();

	String undefined();

	// ----------------------------------------------- header line, page titles

	String welcome();

	String hello();

	String home();

	String admin();

}
