package ch.serva.actions;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Booking;
import ch.serva.db.Payment;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;
import ch.serva.tools.PostRequest;

/**
 * An action to save a payment.
 * 
 * @author Lukas Blunschi
 * 
 */
public class SavePaymentAction implements Action {

	public static final String NAME = "savePayment";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		PostRequest postReq = new PostRequest();
		try {
			postReq.parse(req, null, false);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}
		String idStr = postReq.getFormField(Payment.F_ID);
		String bookingIdStr = postReq.getFormField(Payment.F_BOOKING);
		String dateStr = postReq.getFormField(Payment.F_DATE);
		String amountStr = postReq.getFormField(Payment.F_AMOUNT);
		String text = postReq.getFormField(Payment.F_TEXT);
		if (idStr == null || idStr.length() > 100 || idStr.trim().length() == 0) {
			return new Failure("No or too long payment id given!");
		}
		if (bookingIdStr == null || bookingIdStr.length() > 100 || bookingIdStr.trim().length() == 0) {
			return new Failure("No or too long booking id given!");
		}
		if (dateStr == null || dateStr.length() > 100 || dateStr.trim().length() == 0) {
			return new Failure("No or too long date given!");
		}
		if (amountStr == null || amountStr.length() > 100 || amountStr.trim().length() == 0) {
			return new Failure("No or too long amount given!");
		}
		if (text == null || text.length() > 100) {
			return new Failure("No or too long text given!");
		}

		// parse parameters
		Booking booking = null;
		Date date = null;
		Double amount = null;
		try {
			booking = em.find(Booking.class, Long.valueOf(bookingIdStr));
			date = Dates.dateFormat.parse(dateStr);
			amount = Doubles.formatter.parse(amountStr).doubleValue();
		} catch (Exception e) {
		}
		if (booking == null) {
			return new Failure("booking not found.");
		}
		if (date == null) {
			return new Failure("could not parse date.");
		}
		if (amount == null) {
			return new Failure("could not parse amount.");
		}

		// get entitiy
		Payment payment = null;
		if (idStr.equals(ServaConstants.NEW)) {
			payment = new Payment(booking, date, amount, text);
		} else {
			Long id = null;
			try {
				id = Long.valueOf(idStr);
				payment = em.find(Payment.class, id);
			} catch (Exception e) {
			}

			// access control
			if (payment == null) {
				return new Failure("payment not found!");
			}

			payment.set(booking, date, amount, text);
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(payment);
		tx.commit();

		// result
		return new Success();
	}

}
