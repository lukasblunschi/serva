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

	String none();

	String all();

	String relationships();

	String contact();

	String invoices();

	String invoice();

	String invoiceFor();

	String accountDetails();

	// --------------------------------------------------------- common actions

	String actions();

	String login();

	String logout();

	String go();

	String add();

	String edit();

	String remove();

	String save();

	String show();

	String toggle();

	String success();

	String failure();

	String error();

	String undefined();

	String sendLogin();

	// ----------------------------------------------- header line, page titles

	String welcome();

	String hello();

	String home();

	String admin();

	String editUser();

	String editDomain();

	String editService();

	String editBooking();

	String editPayment();

	// ----------------------------------------------------------- cost related

	String costs();

	String cost();

	String pricePerYear();

	String totalCost();

	String payed();

	String openCost();

	String sum();

	String sumActive();

	// ---------------------------------------------------------------- actions

	String loginInformationSubject();

	String loginInformationBody(String username, String password, String address);

	String emailSent();

	// --------------------------------------------------------------- messages

	String domainUsernameNotMapped();

	// ----------------------------------------------------------------- checks

	String checks();

	String check();

	String noProblem();

	String minorProblem();

	String problem();

}
