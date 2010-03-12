package ch.serva.localization;

public interface DictionaryDatabase {

	// ----------------------------------------------------------------- common

	String id();

	String name();

	String properties();

	// -------------------------------------------------------- users/user page

	String users();

	String user();

	String username();

	String password();

	String isAdmin();

	String language();

	String nickname();

	String firstname();

	String lastname();

	String address();

	String phone();

	String email();

	// --------------------------------------------------------------- bookings

	String from();

	String to();

}
