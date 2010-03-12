package ch.serva;

import javax.persistence.EntityManager;

import ch.serva.tools.EMF;

/**
 * A standalone application to test the connection to the database.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RunServaEntityManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// get access to data
		EntityManager em = EMF.get().createEntityManager();

		String query = "select u from User u";
		em.createQuery(query).getResultList();

	}

}
