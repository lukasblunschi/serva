package ch.serva.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ch.serva.localization.Dictionary;
import ch.serva.localization.English;
import ch.serva.tools.Escape;
import ch.serva.tools.comparators.DomainComparator;

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

	private int isadmin;

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

	@OneToMany(mappedBy = "hostingcontact")
	private List<Domain> domainsAsHostingcontact;

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

	public User(String username, String password, boolean isadmin, String language, String nickname, String firstname, String lastname,
			String address, String phone, String email) {
		set(username, password, isadmin, language, nickname, firstname, lastname, address, phone, email);
	}

	public void set(String username, String password, boolean isadmin, String language, String nickname, String firstname, String lastname,
			String address, String phone, String email) {
		this.username = username;
		this.password = password;
		this.isadmin = isadmin ? 1 : 0;
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
		this.domainsAsHostingcontact = new ArrayList<Domain>();
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
		return isadmin > 0;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin ? 1 : 0;
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

	public List<Domain> getDomainsAsHostingcontact() {
		return domainsAsHostingcontact;
	}

	// ----------------------------------------------------- additional methods

	public boolean isRemovable(User userLoggedIn) {
		boolean notHolder = domainsAsHolder != null && domainsAsHolder.isEmpty();
		boolean notBillingContact = domainsAsBillingcontact != null && domainsAsBillingcontact.isEmpty();
		boolean notTechnicalContact = domainsAsTechnicalcontact != null && domainsAsTechnicalcontact.isEmpty();
		boolean notHostingContact = domainsAsHostingcontact != null && domainsAsHostingcontact.isEmpty();
		boolean notItself = id != null && !id.equals(userLoggedIn.id);
		return notHolder && notBillingContact && notTechnicalContact && notHostingContact && notItself;
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

	public String getAddressAndPhoneAsHtml() {
		StringBuffer html = new StringBuffer();
		boolean hasAddress = address.trim().length() > 0;
		boolean hasPhone = phone.trim().length() > 0;
		if (hasAddress) {
			html.append(Escape.safeXml(address).replaceAll(",", "<br/>"));
		}
		if (hasAddress && hasPhone) {
			html.append("<br/>");
		}
		if (hasPhone) {
			html.append(Escape.safeXml(phone));
		}
		return html.toString();
	}

	/**
	 * Get all domains related to this user.
	 * 
	 * @return list of domains (no duplicates, ordered).
	 */
	public List<Domain> getDomains() {

		// collect and remove duplicates
		Set<Domain> domainSet = new HashSet<Domain>();
		domainSet.addAll(domainsAsHolder);
		domainSet.addAll(domainsAsBillingcontact);
		domainSet.addAll(domainsAsTechnicalcontact);
		domainSet.addAll(domainsAsHostingcontact);

		// order
		List<Domain> domains = new ArrayList<Domain>(domainSet);
		Collections.sort(domains, new DomainComparator());
		return domains;
	}

	public boolean getAsBillingContact(Domain domain) {
		return domainsAsBillingcontact.contains(domain);
	}

	/**
	 * Get relationship string, e.g. [holder, hosting contact].
	 * 
	 * @param domain
	 * @param dict
	 * @return
	 */
	public String getRelationshipString(Domain domain, Dictionary dict) {
		List<String> relationshipList = new ArrayList<String>();
		if (domainsAsHolder.contains(domain)) {
			relationshipList.add(dict.holder());
		}
		if (domainsAsBillingcontact.contains(domain)) {
			relationshipList.add(dict.billingcontact());
		}
		if (domainsAsTechnicalcontact.contains(domain)) {
			relationshipList.add(dict.technicalcontact());
		}
		if (domainsAsHostingcontact.contains(domain)) {
			relationshipList.add(dict.hostingcontact());
		}
		return relationshipList.toString();
	}

	/**
	 * Get user identification (nickname if available, username otherwise)
	 * 
	 * @return short name.
	 */
	public String toShortString() {
		boolean hasNickname = nickname != null && nickname.trim().length() > 0;
		if (hasNickname) {
			return nickname;
		} else {
			return username;
		}
	}

	public String toMiddleString() {
		StringBuffer buf = new StringBuffer();
		boolean hasNickname = nickname != null && nickname.trim().length() > 0;
		boolean hasFirstname = firstname != null && firstname.trim().length() > 0;
		boolean hasLastname = lastname != null && lastname.trim().length() > 0;
		if (hasNickname || hasFirstname || hasLastname) {
			buf.append(nickname).append(", ");
			buf.append(firstname).append(" ").append(lastname).append(", ");
		} else {
			buf.append(username).append(", ");
		}
		buf.append(email);
		return buf.toString();
	}

	public String toFullName() {
		return firstname + " " + lastname;
	}

	public String toFullHtml() {
		StringBuffer html = new StringBuffer();
		html.append(id + "/" + username + (isadmin > 0 ? "*" : "")).append("\n");
		html.append(nickname).append("\n");
		html.append(firstname + " " + lastname).append("\n");
		html.append(email);
		return html.toString();
	}

}
