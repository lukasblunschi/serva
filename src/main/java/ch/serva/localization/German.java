package ch.serva.localization;

/**
 * German dictionary.
 * 
 * @author Lukas Blunschi
 */
public class German extends GermanDatabase implements Dictionary {

	public static final String LANGCODE = "de";

	// -------------------------------------------------------------- languages

	public String getLanguageCode() {
		return LANGCODE;
	}

	public String getLanguageName() {
		return "Deutsch";
	}

	// ----------------------------------------------------------- common terms

	public String yes() {
		return "Ja";
	}

	public String no() {
		return "Nein";
	}

	public String none() {
		return "Keine";
	}

	public String all() {
		return "Alle";
	}

	public String relationships() {
		return "Beziehungen";
	}

	public String contact() {
		return "Kontakt";
	}

	public String invoices() {
		return "Rechnungen";
	}

	public String invoice() {
		return "Rechnung";
	}

	public String invoiceFor() {
		return "Rechnung für";
	}

	public String accountDetails() {
		return "Kontoverbindungen";
	}

	// --------------------------------------------------------- common actions

	public String actions() {
		return "Aktionen";
	}

	public String login() {
		return "Login";
	}

	public String logout() {
		return "Logout";
	}

	public String go() {
		return "Weiter";
	}

	public String add() {
		return "Hinzufügen";
	}

	public String edit() {
		return "Bearbeiten";
	}

	public String remove() {
		return "Entfernen";
	}

	public String save() {
		return "Speichern";
	}

	public String show() {
		return "Anzeigen";
	}

	public String toggle() {
		return "Umschalten";
	}

	public String success() {
		return "Erfolg";
	}

	public String failure() {
		return "Fehler";
	}

	public String error() {
		return "Error";
	}

	public String undefined() {
		return "Undefiniert";
	}

	public String sendLogin() {
		return "Login Schicken";
	}

	// ----------------------------------------------- header line, page titles

	public String welcome() {
		return "Willkommen";
	}

	public String hello() {
		return "Hallo";
	}

	public String home() {
		return "Home";
	}

	public String admin() {
		return "Admin";
	}

	public String editUser() {
		return "Benutzer Bearbeiten";
	}

	public String editDomain() {
		return "Domain Bearbeiten";
	}

	public String editService() {
		return "Service Bearbeiten";
	}

	public String editBooking() {
		return "Buchung Bearbeiten";
	}

	public String editPayment() {
		return "Zahlung Bearbeiten";
	}

	// ----------------------------------------------------------- cost related

	public String costs() {
		return "Kosten";
	}

	public String cost() {
		return "Kosten";
	}

	public String pricePerYear() {
		return "Preis pro Jahr";
	}

	public String totalPrice() {
		return "Total";
	}

	public String totalCost() {
		return "Total";
	}

	public String payed() {
		return "Bezahlt";
	}

	public String openCost() {
		return "Offene Kosten";
	}

	public String sum() {
		return "Summe";
	}

	public String sumActive() {
		return "Summe Aktiv";
	}

	// ---------------------------------------------------------------- actions

	public String loginInformationSubject() {
		return "Serva Login Informationen";
	}

	public String loginInformationBody(String username, String password, String address) {
		StringBuffer msg = new StringBuffer();
		msg.append("Sali " + username + ",\n");
		msg.append("\n");
		msg.append("Deine Login Informationen für " + address + " sind:\n");
		msg.append("\n");
		msg.append("Benutzername: " + username + "\n");
		msg.append("Passwort: " + password + "\n");
		msg.append("\n");
		msg.append("Viele Grüsse,\n");
		msg.append("Serva\n");
		return msg.toString();
	}

	public String emailSent() {
		return "Email Verschickt";
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
		return "Kein Problem";
	}

	public String minorProblem() {
		return "Kleines Problem";
	}

	public String problem() {
		return "Problem";
	}

}
