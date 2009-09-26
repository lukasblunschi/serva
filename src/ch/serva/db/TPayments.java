package ch.serva.db;

/**
 * Payments table.
 * 
 * @author Lukas Blunschi
 * 
 */
public class TPayments {

	public static final String TBL_NAME = "payments";

	public static final String F_ID = "id";

	/** foreign key into bookings table */
	public static final String F_BOOKING = "booking";

	public static final String F_DATE = "date";

	public static final String F_AMOUNT = "amount";

	public static final String F_TEXT = "text";

	/* many relations */

	public static final String getSQLCreate() {
		StringBuffer sql = new StringBuffer();
		sql.append("create table if not exists " + TBL_NAME + "(");
		sql.append(F_ID + "         int not null primary key auto_increment, ");
		sql.append(F_BOOKING + "    int not null, ");
		sql.append(F_DATE + "       date not null, ");
		sql.append(F_AMOUNT + "     double not null, ");
		sql.append(F_TEXT + "       varchar(2000) character set utf8 not null, ");
		sql.append("INDEX (" + F_BOOKING + "));");
		return sql.toString();
	}

}
