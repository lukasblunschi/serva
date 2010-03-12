package ch.serva.localization;

public interface DictionaryDatabase {

	// ----------------------------------------------------------------- common

	String id();

	String name();

	String properties();

	// -------------------------------------------------------- users/user page

	String username();

	String password();

	String isAdmin();

	String language();

	String address();

	String email();

	String mobile();

	// --------------------------------------------------------------- bookings

	String from();

	String to();

}
