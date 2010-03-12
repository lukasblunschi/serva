package ch.serva.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An entity to store a booking.
 * 
 * @author Lukas Blunschi
 * 
 */
@Entity
@Table(name = "bookings")
public class Booking {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Domain domain;

	@ManyToOne
	private Service service;

	private Date fromdate;

	/**
	 * open-ended
	 */
	private Date todate;

	/**
	 * e.g. login info
	 */
	private String info;

	public static final String F_ID = "id";
	public static final String F_DOMAIN = "domain";
	public static final String F_SERVICE = "service";
	public static final String F_FROM = "from";
	public static final String F_TO = "to";
	public static final String F_INFO = "info";

	// ----------------------------------------------------------- construction

	public Booking() {
		set(null, null, new Date(), null, "");
	}

	public Booking(Domain domain, Service service, Date from, Date to, String info) {
		set(domain, service, from, to, info);
	}

	public void set(Domain domain, Service service, Date from, Date to, String info) {
		this.domain = domain;
		this.service = service;
		this.fromdate = from;
		this.todate = to;
		this.info = info;
	}

	// ---------------------------------------------------- getters and setters

	public Long getId() {
		return id;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Date getFrom() {
		return fromdate;
	}

	public void setFrom(Date from) {
		this.fromdate = from;
	}

	public Date getTo() {
		return todate;
	}

	public void setTo(Date to) {
		this.todate = to;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	// ----------------------------------------------------- additional methods

}
