package ch.serva.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An entity to store a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
@Entity
@Table(name = "domains")
public class Domain {

	@Id
	@GeneratedValue
	private int id;

	private String domainname;

	@ManyToOne
	private User holder;

	@ManyToOne
	private User billingcontact;

	@ManyToOne
	private User technicalcontact;

	public static final String F_ID = "id";
	public static final String F_DOMAINNAME = "domainname";
	public static final String F_HOLDER = "holder";
	public static final String F_BILLINGCONTACT = "billingcontact";
	public static final String F_TECHNICALCONTACT = "technicalcontact";

	// ----------------------------------------------------------- construction

	public Domain() {
		set("", null, null, null);
	}

	public Domain(String domainname, User holder, User billingcontact, User technicalcontact) {
		set(domainname, holder, billingcontact, technicalcontact);
	}

	public void set(String domainname, User holder, User billingcontact, User technicalcontact) {
		this.domainname = domainname;
		this.holder = holder;
		this.billingcontact = billingcontact;
		this.technicalcontact = technicalcontact;
	}

	// ---------------------------------------------------- getters and setters

	public int getId() {
		return id;
	}

	public String getDomainname() {
		return domainname;
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}

	public User getHolder() {
		return holder;
	}

	public void setHolder(User holder) {
		this.holder = holder;
	}

	public User getBillingcontact() {
		return billingcontact;
	}

	public void setBillingcontact(User billingcontact) {
		this.billingcontact = billingcontact;
	}

	public User getTechnicalcontact() {
		return technicalcontact;
	}

	public void setTechnicalcontact(User technicalcontact) {
		this.technicalcontact = technicalcontact;
	}

	// ----------------------------------------------------- additional methods

}
