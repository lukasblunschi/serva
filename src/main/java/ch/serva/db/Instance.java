package ch.serva.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * Instance.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Instance {

	private final EntityManager em;

	public Instance(EntityManager em) {
		this.em = em;
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		for (Object obj : em.createQuery("select u from User u").getResultList()) {
			users.add((User) obj);
		}
		return users;
	}

	public List<User> getAdmins() {
		List<User> admins = new ArrayList<User>();
		for (Object obj : em.createQuery("select u from User u").getResultList()) {
			User user = (User) obj;
			if (user.getIsAdmin()) {
				admins.add(user);
			}
		}
		return admins;
	}

	public List<Domain> getDomains() {
		List<Domain> domains = new ArrayList<Domain>();
		for (Object obj : em.createQuery("select d from Domain d").getResultList()) {
			domains.add((Domain) obj);
		}
		return domains;
	}

	public List<Service> getServices() {
		List<Service> services = new ArrayList<Service>();
		for (Object obj : em.createQuery("select s from Service s").getResultList()) {
			services.add((Service) obj);
		}
		return services;
	}

	public List<Booking> getBookings() {
		List<Booking> bookings = new ArrayList<Booking>();
		for (Object obj : em.createQuery("select b from Booking b").getResultList()) {
			bookings.add((Booking) obj);
		}
		return bookings;
	}

	public List<Payment> getPayments() {
		List<Payment> payments = new ArrayList<Payment>();
		for (Object obj : em.createQuery("select p from Payment p").getResultList()) {
			payments.add((Payment) obj);
		}
		return payments;
	}

}
