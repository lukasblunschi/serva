package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Service;
import ch.serva.tools.PostRequest;

/**
 * An action to save a service.
 * 
 * @author Lukas Blunschi
 * 
 */
public class SaveServiceAction implements Action {

	public static final String NAME = "saveService";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		PostRequest postReq = new PostRequest();
		try {
			postReq.parse(req, null, false);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}
		String idStr = postReq.getFormField(Service.F_ID);
		String servicename = postReq.getFormField(Service.F_SERVICENAME);
		String priceStr = postReq.getFormField(Service.F_PRICE);
		String desc = postReq.getFormField(Service.F_DESC);
		if (idStr == null || idStr.length() > 100 || idStr.trim().length() == 0) {
			return new Failure("No or too long service id given!");
		}
		if (servicename == null || servicename.length() > 100 || servicename.trim().length() == 0) {
			return new Failure("No or too long service name entered!");
		}
		if (priceStr == null || priceStr.length() > 100) {
			return new Failure("No or too long price entered!");
		}
		if (desc == null || desc.length() > 4000) {
			return new Failure("No or too long holder id given!");
		}

		// parse parameters
		double price = 0.0;
		try {
			price = Double.valueOf(priceStr);
		} catch (Exception e) {
		}

		// get entitiy
		Service service = null;
		if (idStr.equals(ServaConstants.NEW)) {
			service = new Service(servicename, price, desc);
		} else {
			Long id = null;
			try {
				id = Long.valueOf(idStr);
				service = em.find(Service.class, id);
			} catch (Exception e) {
			}

			// access control
			if (service == null) {
				return new Failure("service not found!");
			}

			service.set(servicename, price, desc);
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(service);
		tx.commit();

		// result
		return new Success();
	}

}
