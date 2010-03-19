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

	// ---------------------------------------------------------------- domains

	String domains();

	String domain();

	String domainname();

	String holder();

	String billingcontact();

	String technicalcontact();

	String hostingcontact();

	// --------------------------------------------------------------- services

	String services();

	String service();

	String servicename();

	String price();

	String description();

	String checkDefinition();

	// --------------------------------------------------------------- bookings

	String bookings();

	String booking();

	String from();

	String to();

	String info();

	// --------------------------------------------------------------- payments

	String payments();

	String payment();

	String date();

	String amount();

	String text();

}
