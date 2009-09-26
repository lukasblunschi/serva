package ch.serva.db;

/**
 * Services table.
 * 
 * @author Lukas Blunschi
 * 
 */
public class TServices {

	public static final String TBL_NAME = "services";

	public static final String F_ID = "id";

	public static final String F_SERVICENAME = "servicename";

	/** price per year */
	public static final String F_PRICE = "price";

	public static final String F_DESC = "desc";

	public static final String getSQLCreate() {
		StringBuffer sql = new StringBuffer();
		sql.append("create table if not exists " + TBL_NAME + "(");
		sql.append(F_ID + "          int not null primary key auto_increment, ");
		sql.append(F_SERVICENAME + " varchar(200) character set utf8 not null, ");
		sql.append(F_PRICE + "       double not null, ");
		sql.append(F_DESC + "        varchar(200) character set utf8 not null)");
		return sql.toString();
	}

}
