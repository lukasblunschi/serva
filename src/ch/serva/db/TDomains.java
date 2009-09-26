package ch.serva.db;

/**
 * Domains table.
 * 
 * @author Lukas Blunschi
 * 
 */
public class TDomains {

	public static final String TBL_NAME = "domains";

	public static final String F_ID = "id";

	public static final String F_DOMAINNAME = "domainname";

	/** foreign key into users table */
	public static final String F_HOLDER = "holder";

	/** foreign key into users table */
	public static final String F_BILLING = "billing";

	/** foreign key into users table */
	public static final String F_TECHNICAL = "technical";

	/* many relations */

	public static final String MR_HOLDER = TBL_NAME + "." + F_HOLDER;

	public static final String MR_BILLING = TBL_NAME + "." + F_BILLING;

	public static final String MR_TECHNICAL = TBL_NAME + "." + F_TECHNICAL;

	public static final String getSQLCreate() {
		StringBuffer sql = new StringBuffer();
		sql.append("create table if not exists " + TBL_NAME + "(");
		sql.append(F_ID + "         int not null primary key auto_increment, ");
		sql.append(F_DOMAINNAME + " varchar(200) character set utf8 not null, ");
		sql.append(F_HOLDER + "       int not null, ");
		sql.append(F_BILLING + "       int not null, ");
		sql.append(F_TECHNICAL + "       int not null, ");
		sql.append("INDEX (" + F_HOLDER + "), ");
		sql.append("INDEX (" + F_BILLING + "), ");
		sql.append("INDEX (" + F_TECHNICAL + "));");
		return sql.toString();
	}

}
