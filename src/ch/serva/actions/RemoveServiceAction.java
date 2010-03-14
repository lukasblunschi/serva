package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Service;

/**
 * An action to remove a service.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RemoveServiceAction implements Action {

	public static final String NAME = "removeService";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		String idServiceStr = req.getParameter("service_" + Service.F_ID);

		// parse parameters
		Long idService = null;
		try {
			idService = Long.valueOf(idServiceStr);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}

		// entity
		Service service = em.find(Service.class, idService);

		// access control
		if (service == null) {
			return new Failure("service not found!");
		}

		// check if removable
		if (!service.isRemovable()) {
			return new Failure("service not removable!");
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(service);
		tx.commit();

		// result
		return new Success();
	}

}
