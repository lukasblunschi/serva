package ch.serva.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.db.Service;
import ch.serva.tools.Dates;
import ch.serva.tools.PostRequest;

/**
 * An action to add multiple bookings.
 * 
 * @author Lukas Blunschi
 * 
 */
public class AddBookingsAction implements Action {

	public static final String NAME = "addBookings";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		PostRequest postReq = new PostRequest();
		try {
			postReq.parse(req, null, false);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}
		String domainIdStr = postReq.getFormField(Booking.F_DOMAIN);
		String fromStr = postReq.getFormField(Booking.F_FROM);
		if (domainIdStr == null || domainIdStr.length() > 100 || domainIdStr.trim().length() == 0) {
			return new Failure("No or too long domain id given!");
		}
		if (fromStr == null || fromStr.length() > 100 || fromStr.trim().length() == 0) {
			return new Failure("No or too long from date given!");
		}

		// parse parameters
		Domain domain = null;
		Date from = null;
		try {
			domain = em.find(Domain.class, Long.valueOf(domainIdStr));
			from = Dates.dateFormat.parse(fromStr);
		} catch (Exception e) {
		}
		if (domain == null) {
			return new Failure("domain not found.");
		}
		if (from == null) {
			return new Failure("could not parse from date.");
		}

		// services
		List<Service> services = new ArrayList<Service>();
		for (Service service : new Instance(em).getServices()) {
			String serviceIdStr = postReq.getFormField(Booking.F_SERVICE + "_" + service.getId());
			if (serviceIdStr != null) {
				services.add(service);
			}
		}

		// bookings
		List<Booking> bookings = new ArrayList<Booking>();
		for (Service service : services) {
			bookings.add(new Booking(domain, service, from, null, ""));
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		for (Booking booking : bookings) {
			em.persist(booking);
		}
		tx.commit();

		// result
		return new Success();
	}

}
