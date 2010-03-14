package ch.serva.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private Long id;

	private String domainname;

	@ManyToOne
	private User holder;

	@ManyToOne
	private User billingcontact;

	@ManyToOne
	private User technicalcontact;

	@ManyToOne
	private User hostingcontact;

	@OneToMany(mappedBy = "domain")
	private List<Booking> bookings;

	public static final String F_ID = "id";
	public static final String F_DOMAINNAME = "domainname";
	public static final String F_HOLDER = "holder";
	public static final String F_BILLINGCONTACT = "billingcontact";
	public static final String F_TECHNICALCONTACT = "technicalcontact";
	public static final String F_HOSTINGCONTACT = "hostingcontact";

	// ----------------------------------------------------------- construction

	public Domain() {
		set("", null, null, null, null);
	}

	public Domain(String domainname, User holder, User billingcontact, User technicalcontact, User hostingcontact) {
		set(domainname, holder, billingcontact, technicalcontact, hostingcontact);
	}

	public void set(String domainname, User holder, User billingcontact, User technicalcontact, User hostingcontact) {
		this.domainname = domainname;
		this.holder = holder;
		this.billingcontact = billingcontact;
		this.technicalcontact = technicalcontact;
		this.hostingcontact = hostingcontact;
	}

	// ---------------------------------------------------- getters and setters

	public Long getId() {
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

	public User getHostingcontact() {
		return hostingcontact;
	}

	public void setHostingcontact(User hostingcontact) {
		this.hostingcontact = hostingcontact;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	// ----------------------------------------------------- additional methods

	public boolean isRemovable() {
		return bookings != null && bookings.isEmpty();
	}

}
