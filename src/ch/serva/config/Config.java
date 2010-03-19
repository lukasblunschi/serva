package ch.serva.config;

/**
 * Placeholder for config.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Config {

	/**
	 * Property name to retrieve the path to the domain configuration directory.
	 * <p>
	 * E.g. /root/domainconfig
	 * <p>
	 * NOT ENDING WITH A SLASH
	 */
	public static final String PN_PATH_DOMAINCONFIGDIR = "path.domainconfigdir";

	/**
	 * Property name to retrieve the path to the domain name to domain username mapping file.
	 * <p>
	 * E.g. /root/serverconfig/user-domain_mapping.txt
	 */
	public static final String PN_PATH_DOMAINTOUSERNAMEMAPPING = "path.domainToUsernameMapping";

}
