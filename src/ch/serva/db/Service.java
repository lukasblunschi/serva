package ch.serva.db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * An entity to store a service.
 * 
 * @author Lukas Blunschi
 * 
 */
@Entity
@Table(name = "services")
public class Service {

	@Id
	@GeneratedValue
	private Long id;

	private String servicename;

	/**
	 * price per year
	 */
	private double price;

	private String desc;

	@OneToMany(mappedBy = "service")
	private List<Booking> bookings;

	public static final String F_ID = "id";
	public static final String F_SERVICENAME = "servicename";
	public static final String F_PRICE = "price";
	public static final String F_DESC = "desc";

	// ----------------------------------------------------------- construction

	public Service() {
		set("", 0.0, "");
	}

	public Service(String servicename, double price, String desc) {
		set(servicename, price, desc);
	}

	public void set(String servicename, double price, String desc) {
		this.servicename = servicename;
		this.price = price;
		this.desc = desc;
	}

	// ---------------------------------------------------- getters and setters

	public Long getId() {
		return id;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	// ----------------------------------------------------- additional methods

}
