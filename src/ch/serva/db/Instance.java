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

}
