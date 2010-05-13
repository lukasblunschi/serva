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

	public String none() {
		return "None";
	}

	public String all() {
		return "All";
	}

	public String relationships() {
		return "Relationships";
	}

	public String contact() {
		return "Contact";
	}

	public String invoiceFor() {
		return "Invoice for";
	}

	public String accountDetails() {
		return "Account Details";
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

	public String edit() {
		return "Edit";
	}

	public String remove() {
		return "Remove";
	}

	public String save() {
		return "Save";
	}

	public String show() {
		return "Show";
	}

	public String toggle() {
		return "Toggle";
	}

	public String success() {
		return "Success";
	}

	public String failure() {
		return "Failure";
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

	public String editService() {
		return "Edit Service";
	}

	public String editBooking() {
		return "Edit Booking";
	}

	public String editPayment() {
		return "Edit Payment";
	}

	// ----------------------------------------------------------- cost related

	public String costs() {
		return "Costs";
	}

	public String cost() {
		return "Cost";
	}

	public String pricePerYear() {
		return "Price per Year";
	}

	public String totalCost() {
		return "Total Cost";
	}

	public String payed() {
		return "Payed";
	}

	public String openCost() {
		return "Open Cost";
	}

	public String sum() {
		return "Sum";
	}

	public String sumActive() {
		return "Sum Active";
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

	// --------------------------------------------------------------- messages

	public String domainUsernameNotMapped() {
		return "Domain username not mapped.";
	}

	// ----------------------------------------------------------------- checks

	public String checks() {
		return "Checks";
	}

	public String check() {
		return "Check";
	}

	public String noProblem() {
		return "No Problem";
	}

	public String minorProblem() {
		return "Minor Problem";
	}

	public String problem() {
		return "Problem";
	}

}
