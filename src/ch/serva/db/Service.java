package ch.serva.db;

import java.util.List;

import javax.persistence.Column;
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

	@Column(length = 4000)
	private String description;

	/**
	 * e.g. java:ch.serva.checks.Webalizer
	 */
	private String checkdefinition;

	@OneToMany(mappedBy = "service")
	private List<Booking> bookings;

	public static final String F_ID = "id";
	public static final String F_SERVICENAME = "servicename";
	public static final String F_PRICE = "price";
	public static final String F_DESCRIPTION = "description";
	public static final String F_CHECKDEFINITION = "checkdefinition";

	// ----------------------------------------------------------- construction

	public Service() {
		set("", 0.0, "", "");
	}

	public Service(String servicename, double price, String description, String checkDefinition) {
		set(servicename, price, description, checkDefinition);
	}

	public void set(String servicename, double price, String description, String checkDefinition) {
		this.servicename = servicename;
		this.price = price;
		this.description = description;
		this.checkdefinition = checkDefinition;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCheckDefinition() {
		return checkdefinition;
	}

	public void setCheckDefinition(String checkDefinition) {
		this.checkdefinition = checkDefinition;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	// ----------------------------------------------------- additional methods

	public boolean isRemovable() {
		return bookings != null && bookings.isEmpty();
	}

	public String toShortString() {
		return servicename;
	}

}
