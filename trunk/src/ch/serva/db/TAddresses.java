package ch.serva.db;

/**
 * Addresses table.
 * 
 * @author Lukas Blunschi
 * 
 */
public class TAddresses {

	public static final String TBL_NAME = "addresses";

	public static final String F_ID = "id";

	public static final String F_NAME = "name";

	public static final String F_STREET = "street";

	public static final String F_POSTALCODE = "postalcode";

	public static final String F_LOCALITY = "locality";

	public static final String F_COUNTRY = "country";

	public static final String F_TELEFON = "telefon";

	public static final String F_MOBILE = "mobile";

	public static final String F_EMAIL = "email";

	public static final String F_HOMEPAGE = "homepage";

	public static final String getSQLCreate() {
		StringBuffer sql = new StringBuffer();
		sql.append("create table if not exists " + TBL_NAME + "(");
		sql.append(F_ID + "         int not null primary key auto_increment, ");
		sql.append(F_NAME + "       varchar(200) character set utf8 not null, ");
		sql.append(F_STREET + "     varchar(200) character set utf8 not null, ");
		sql.append(F_POSTALCODE + " varchar(200) character set utf8 not null, ");
		sql.append(F_LOCALITY + "   varchar(200) character set utf8 not null, ");
		sql.append(F_COUNTRY + "    varchar(200) character set utf8 not null, ");
		sql.append(F_TELEFON + "    varchar(200) character set utf8 not null, ");
		sql.append(F_MOBILE + "     varchar(200) character set utf8 not null, ");
		sql.append(F_EMAIL + "      varchar(200) character set utf8 not null, ");
		sql.append(F_HOMEPAGE + "   varchar(200) character set utf8 not null)");
		return sql.toString();
	}

}
