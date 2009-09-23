package ch.serva.tests;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ch.serva.db.Domain;
import ch.serva.db.User;

public class TestJPA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Hoi");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("serva");
		EntityManager em = emf.createEntityManager();

		List<?> users = em.createQuery("select u from User u order by u.username").getResultList();
		for (Object obj : users) {
			User user = (User) obj;
			System.out.println("User: " + user);
		}

		List<?> domains = em.createQuery("select d from Domain d order by d.domainname").getResultList();
		for (Object obj : domains) {
			Domain domain = (Domain) obj;
			System.out.println("Domain: " + domain);
		}

		em.close();
		emf.close();

	}

}
