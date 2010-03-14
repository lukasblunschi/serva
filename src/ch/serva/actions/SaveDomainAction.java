package ch.serva.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.Domain;
import ch.serva.db.User;
import ch.serva.tools.PostRequest;

/**
 * An action to save a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class SaveDomainAction implements Action {

	public static final String NAME = "saveDomain";

	public Result execute(HttpServletRequest req, EntityManager em) {

		// get parameters
		PostRequest postReq = new PostRequest();
		try {
			postReq.parse(req, null, false);
		} catch (Exception e) {
			return new Failure(e.getMessage());
		}
		String idStr = postReq.getFormField(Domain.F_ID);
		String domainname = postReq.getFormField(Domain.F_DOMAINNAME);
		String holderIdStr = postReq.getFormField(Domain.F_HOLDER);
		String billingContactIdStr = postReq.getFormField(Domain.F_BILLINGCONTACT);
		String technicalContactIdStr = postReq.getFormField(Domain.F_TECHNICALCONTACT);
		String hostingContactIdStr = postReq.getFormField(Domain.F_HOSTINGCONTACT);
		if (idStr == null || idStr.length() > 100 || idStr.trim().length() == 0) {
			return new Failure("No or too long domain id given!");
		}
		if (domainname == null || domainname.length() > 100 || domainname.trim().length() == 0) {
			return new Failure("No or too long domainname entered!");
		}
		if (holderIdStr == null || holderIdStr.length() > 100 || holderIdStr.trim().length() == 0) {
			return new Failure("No or too long holder id given!");
		}
		if (billingContactIdStr == null || billingContactIdStr.length() > 100 || billingContactIdStr.trim().length() == 0) {
			return new Failure("No or too long billing contact id given!");
		}
		if (technicalContactIdStr == null || technicalContactIdStr.length() > 100
				|| technicalContactIdStr.trim().length() == 0) {
			return new Failure("No or too long technical contact id given!");
		}
		if (hostingContactIdStr == null || hostingContactIdStr.length() > 100 || hostingContactIdStr.trim().length() == 0) {
			return new Failure("No or too long hosting contact id given!");
		}

		// parse parameters
		User holder = null;
		User billingContact = null;
		User technicalContact = null;
		User hostingContact = null;
		try {
			holder = em.find(User.class, Long.valueOf(holderIdStr));
			billingContact = em.find(User.class, Long.valueOf(billingContactIdStr));
			technicalContact = em.find(User.class, Long.valueOf(technicalContactIdStr));
			hostingContact = em.find(User.class, Long.valueOf(hostingContactIdStr));
		} catch (Exception e) {
		}
		if (holder == null || billingContact == null || technicalContact == null || hostingContact == null) {
			return new Failure("holder, billing contact, technical contact or hosting contact not found.");
		}

		// get entitiy
		Domain domain = null;
		if (idStr.equals(ServaConstants.NEW)) {
			domain = new Domain(domainname, holder, billingContact, technicalContact, hostingContact);
		} else {
			Long id = null;
			try {
				id = Long.valueOf(idStr);
				domain = em.find(Domain.class, id);
			} catch (Exception e) {
			}

			// access control
			if (domain == null) {
				return new Failure("domain not found!");
			}

			domain.set(domainname, holder, billingContact, technicalContact, hostingContact);
		}

		// persist
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(domain);
		tx.commit();

		// result
		return new Success();
	}

}
