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

	// ----------------------------------------------------------- common terms

	String yes();

	String no();

	// --------------------------------------------------------- common actions

	String actions();

	String login();

	String logout();

	String go();

	String add();

	String remove();

	String save();

	String error();

	String undefined();

	String sendLogin();

	// ----------------------------------------------- header line, page titles

	String welcome();

	String hello();

	String home();

	String admin();

	String editUser();

	// ---------------------------------------------------------------- actions

	String loginInformationSubject();

	String loginInformationBody(String username, String password, String address);

	String emailSent();

}
