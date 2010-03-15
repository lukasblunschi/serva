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
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.tools.Dates;
import ch.serva.tools.PostRequest;

/**
 * An action to save a booking.
 * 
 * @author Lukas Blunschi
 * 
 */
public class SaveBookingAction implements Action {

	public static final String NAME = "saveBooking";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		PostRequest postReq = new PostRequest();
		try {
			postReq.parse(req, null, false);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}
		String idStr = postReq.getFormField(Booking.F_ID);
		String domainIdStr = postReq.getFormField(Booking.F_DOMAIN);
		String serviceIdStr = postReq.getFormField(Booking.F_SERVICE);
		String fromStr = postReq.getFormField(Booking.F_FROM);
		String toStr = postReq.getFormField(Booking.F_TO);
		String info = postReq.getFormField(Booking.F_INFO);
		if (idStr == null || idStr.length() > 100 || idStr.trim().length() == 0) {
			return new Failure("No or too long booking id given!");
		}
		if (domainIdStr == null || domainIdStr.length() > 100 || domainIdStr.trim().length() == 0) {
			return new Failure("No or too long domain id given!");
		}
		if (serviceIdStr == null || serviceIdStr.length() > 100 || serviceIdStr.trim().length() == 0) {
			return new Failure("No or too long service id given!");
		}
		if (fromStr == null || fromStr.length() > 100 || fromStr.trim().length() == 0) {
			return new Failure("No or too long from date given!");
		}
		if (toStr == null || toStr.length() > 100) {
			return new Failure("No or too long to date given!");
		}
		if (info == null || info.length() > 100) {
			return new Failure("No or too long info given!");
		}

		// parse parameters
		Domain domain = null;
		Service service = null;
		Date from = null;
		Date to = null;
		try {
			domain = em.find(Domain.class, Long.valueOf(domainIdStr));
			service = em.find(Service.class, Long.valueOf(serviceIdStr));
			from = Dates.dateFormat.parse(fromStr);
			to = toStr.trim().length() == 0 ? null : Dates.dateFormat.parse(toStr);
		} catch (Exception e) {
		}
		if (domain == null || service == null) {
			return new Failure("domain or service not found.");
		}
		if (from == null) {
			return new Failure("could not parse from date.");
		}

		// get entitiy
		Booking booking = null;
		if (idStr.equals(ServaConstants.NEW)) {
			booking = new Booking(domain, service, from, to, info);
		} else {
			Long id = null;
			try {
				id = Long.valueOf(idStr);
				booking = em.find(Booking.class, id);
			} catch (Exception e) {
			}

			// access control
			if (booking == null) {
				return new Failure("booking not found!");
			}

			booking.set(domain, service, from, to, info);
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(booking);
		tx.commit();

		// result
		return new Success();
	}

}
