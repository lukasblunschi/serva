package ch.serva.db.collections;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.db.Service;

/**
 * Tools to work with services.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Services {

	/**
	 * Get active services for given domain.
	 * 
	 * @param domain
	 * @return
	 */
	public static List<Service> getActive(Domain domain) {
		List<Service> result = new ArrayList<Service>();

		// loop over all bookings
		for (Booking booking : domain.getBookings()) {
			if (booking.isActive()) {
				result.add(booking.getService());
			}
		}

		return result;
	}

	/**
	 * Get not active services for given domain.
	 * 
	 * @param domain
	 * @param em
	 * @return
	 */
	public static List<Service> getNotActive(Domain domain, EntityManager em) {
		List<Service> services = new Instance(em).getServices();
		services.removeAll(getActive(domain));
		return services;
	}

}
