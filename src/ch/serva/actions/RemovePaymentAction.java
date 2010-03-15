package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Payment;

/**
 * An action to remove a payment.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RemovePaymentAction implements Action {

	public static final String NAME = "removePayment";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		String idPaymentStr = req.getParameter("payment_" + Payment.F_ID);

		// parse parameters
		Long idPayment = null;
		try {
			idPayment = Long.valueOf(idPaymentStr);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}

		// entity
		Payment payment = em.find(Payment.class, idPayment);

		// access control
		if (payment == null) {
			return new Failure("payment not found!");
		}

		// check if removable
		if (!payment.isRemovable()) {
			return new Failure("payment not removable!");
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(payment);
		tx.commit();

		// result
		return new Success();
	}

}
