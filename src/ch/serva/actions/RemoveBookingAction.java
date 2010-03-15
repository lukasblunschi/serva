package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Booking;

/**
 * An action to remove a booking.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RemoveBookingAction implements Action {

	public static final String NAME = "removeBooking";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		String idBookingStr = req.getParameter("booking_" + Booking.F_ID);

		// parse parameters
		Long idBooking = null;
		try {
			idBooking = Long.valueOf(idBookingStr);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}

		// entity
		Booking booking = em.find(Booking.class, idBooking);

		// access control
		if (booking == null) {
			return new Failure("booking not found!");
		}

		// check if removable
		if (!booking.isRemovable()) {
			return new Failure("booking not removable!");
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(booking);
		tx.commit();

		// result
		return new Success();
	}

}
