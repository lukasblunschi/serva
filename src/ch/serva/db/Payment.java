package ch.serva.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * An entity to store a payment.
 * 
 * @author Lukas Blunschi
 * 
 */
@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Booking booking;

	private Date date;

	private double amount;

	private String text;

	public static final String F_ID = "id";
	public static final String F_BOOKING = "booking";
	public static final String F_DATE = "date";
	public static final String F_AMOUNT = "amount";
	public static final String F_TEXT = "text";

	// ----------------------------------------------------------- construction

	public Payment() {
		set(null, new Date(), 0.0, "");
	}

	public Payment(Booking booking, Date date, double amount, String text) {
		set(booking, date, amount, text);
	}

	public void set(Booking booking, Date date, double amount, String text) {
		this.booking = booking;
		this.date = date;
		this.amount = amount;
		this.text = text;
	}

	// ---------------------------------------------------- getters and setters

	public Long getId() {
		return id;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	// ----------------------------------------------------- additional methods

	public boolean isRemovable() {
		return true;
	}

}
