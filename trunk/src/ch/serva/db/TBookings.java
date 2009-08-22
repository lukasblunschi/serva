package ch.serva.db;

/**
 * Bookings table.
 * 
 * @author Lukas Blunschi
 * 
 */
public class TBookings {

	public static final String TBL_NAME = "bookings";

	public static final String F_ID = "id";

	public static final String F_DOMAIN = "domain";

	public static final String F_SERVICE = "service";

	public static final String F_FROM = "from";

	/** open-ended */
	public static final String F_TO = "to";

	/** e.g. login info */
	public static final String F_INFO = "info";

	/* many relations */

	public static final String MR_DOMAIN = TBL_NAME + "." + F_DOMAIN;

	public static final String MR_SERVICE = TBL_NAME + "." + F_SERVICE;

	public static final String getSQLCreate() {
		StringBuffer sql = new StringBuffer();
		sql.append("create table if not exists " + TBL_NAME + "(");
		sql.append(F_ID + "        int not null primary key auto_increment, ");
		sql.append(F_DOMAIN + "    int not null, ");
		sql.append(F_SERVICE + "   int not null, ");
		sql.append(F_FROM + "      date not null, ");
		sql.append(F_TO + "        date not null, ");
		sql.append(F_INFO + "      varchar(200) character set utf8 not null, ");
		sql.append("INDEX (" + F_DOMAIN + "), ");
		sql.append("INDEX (" + F_SERVICE + "));");
		return sql.toString();
	}

}
