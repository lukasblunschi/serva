package ch.serva.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ch.serva.localization.English;

/**
 * An entity to store a user.
 * <p>
 * A user contains a combination of
 * <ul>
 * <li>login information: username, password
 * <li>rights information: isadmin
 * <li>display information: language, nickname, firstname, lastname
 * <li>contact information: address, phone, email
 * </ul>
 * 
 * @author Lukas Blunschi
 * 
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	private boolean isadmin;

	/**
	 * language code, e.g. en or de
	 */
	private String language;

	private String nickname;

	private String firstname;

	private String lastname;

	/**
	 * full address (components separated by comma), e.g. Langackerstr. 68, 8057 Zürich
	 */
	private String address;

	private String phone;

	private String email;

	@OneToMany(mappedBy = "holder")
	private List<Domain> domainsAsHolder;

	@OneToMany(mappedBy = "billingcontact")
	private List<Domain> domainsAsBillingcontact;

	@OneToMany(mappedBy = "technicalcontact")
	private List<Domain> domainsAsTechnicalcontact;

	public static final String F_ID = "id";
	public static final String F_USERNAME = "username";
	public static final String F_PASSWORD = "password";
	public static final String F_ISADMIN = "isadmin";
	public static final String F_LANGUAGE = "language";
	public static final String F_NICKNAME = "nickname";
	public static final String F_FIRSTNAME = "firstname";
	public static final String F_LASTNAME = "lastname";
	public static final String F_ADDRESS = "address";
	public static final String F_PHONE = "phone";
	public static final String F_EMAIL = "email";

	// ----------------------------------------------------------- construction

	public User() {
		set("", "", false, English.LANGCODE, "", "", "", "", "", "");
		generatePassword();
	}

	public User(String username, String password, boolean isadmin, String language, String nickname, String firstname,
			String lastname, String address, String phone, String email) {
		set(username, password, isadmin, language, nickname, firstname, lastname, address, phone, email);
	}

	public void set(String username, String password, boolean isadmin, String language, String nickname, String firstname,
			String lastname, String address, String phone, String email) {
		this.username = username;
		this.password = password;
		this.isadmin = isadmin;
		this.language = language;
		this.nickname = nickname;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.domainsAsHolder = new ArrayList<Domain>();
		this.domainsAsBillingcontact = new ArrayList<Domain>();
		this.domainsAsTechnicalcontact = new ArrayList<Domain>();
	}

	// ---------------------------------------------------- getters and setters

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isadmin;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Domain> getDomainsAsHolder() {
		return domainsAsHolder;
	}

	public List<Domain> getDomainsAsBillingcontact() {
		return domainsAsBillingcontact;
	}

	public List<Domain> getDomainsAsTechnicalcontact() {
		return domainsAsTechnicalcontact;
	}

	// ----------------------------------------------------- additional methods

	public boolean isRemovable(User userLoggedIn) {
		boolean notHolder = domainsAsHolder != null && domainsAsHolder.isEmpty();
		boolean notBillingContact = domainsAsBillingcontact != null && domainsAsBillingcontact.isEmpty();
		boolean notTechnicalContact = domainsAsTechnicalcontact != null && domainsAsTechnicalcontact.isEmpty();
		boolean notItself = id != null && !id.equals(userLoggedIn.id);
		return notHolder && notBillingContact && notTechnicalContact && notItself;
	}

	private void generatePassword() {
		StringBuffer buf = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			// number or char
			if (random.nextBoolean()) {
				// number
				buf.append(random.nextInt(10));
			} else {
				// char
				// lower or upper
				if (random.nextBoolean()) {
					buf.append((char) (0x61 + random.nextInt(26)));
				} else {
					buf.append((char) (0x41 + random.nextInt(26)));
				}
			}
		}
		setPassword(buf.toString());
	}

	public List<Domain> getDomains() {
		List<Domain> domains = new ArrayList<Domain>();
		domains.addAll(domainsAsHolder);
		domains.addAll(domainsAsBillingcontact);
		domains.addAll(domainsAsTechnicalcontact);
		return domains;
	}

}
