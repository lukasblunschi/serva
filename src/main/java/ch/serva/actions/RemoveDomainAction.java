package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Domain;

/**
 * An action to remove a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RemoveDomainAction implements Action {

	public static final String NAME = "removeDomain";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		String idDomainStr = req.getParameter("domain_" + Domain.F_ID);

		// parse parameters
		Long idDomain = null;
		try {
			idDomain = Long.valueOf(idDomainStr);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}

		// entity
		Domain domain = em.find(Domain.class, idDomain);

		// access control
		if (domain == null) {
			return new Failure("domain not found!");
		}

		// check if removable
		if (!domain.isRemovable()) {
			return new Failure("domain not removable!");
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(domain);
		tx.commit();

		// result
		return new Success();
	}

}
