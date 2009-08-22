package ch.serva.db;

/**
 * Users table.
 * 
 * @author Lukas Blunschi
 * 
 */
public class TUsers {

	public static final String TBL_NAME = "users";

	public static final String F_ID = "id";

	public static final String F_USERNAME = "username";

	public static final String F_PASSWORD = "password";

	public static final String F_ISADMIN = "isadmin";

	/** foreign key into addresses table */
	public static final String F_ADDRESS = "address";

	/* many relations */

	public static final String MR_ADDRESS = TBL_NAME + "." + F_ADDRESS;

	public static final String getSQLCreate() {
		StringBuffer sql = new StringBuffer();
		sql.append("create table if not exists " + TBL_NAME + "(");
		sql.append(F_ID + "         int not null primary key auto_increment, ");
		sql.append(F_USERNAME + "   varchar(200) character set utf8 not null, ");
		sql.append(F_PASSWORD + "   varchar(200) character set utf8 not null, ");
		sql.append(F_ISADMIN + "    boolean not null, ");
		sql.append(F_ADDRESS + "    int not null, ");
		sql.append("INDEX (" + F_ADDRESS + "));");
		return sql.toString();
	}

}
