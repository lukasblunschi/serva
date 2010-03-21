package ch.serva.pages.edit;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.config.Config;
import ch.serva.db.Payment;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.elements.PaymentForm;

/**
 * A page to edit a payment.
 * 
 * @author Lukas Blunschi
 * 
 */
public class EditPaymentPage extends AbstractAdminPage {

	public static final String NAME = "editPayment";

	@Override
	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"focus_booking\").focus();";
	}

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {

		// config
		Config config = new Config();

		// get entity
		Payment payment = null;
		final String idStr = req.getParameter(Payment.F_ID);
		if (idStr == null) {
			return "id not given.";
		}
		final boolean isNew = idStr.equals(ServaConstants.NEW);
		if (isNew) {
			payment = new Payment();
		} else {
			try {
				Long id = Long.valueOf(idStr);
				payment = em.find(Payment.class, id);

				// access control
				if (payment == null) {
					return "<p class='error content'>payment does not exist!</p>";
				}
			} catch (Exception e) {
				return "id not parsable.";
			}
		}

		// create html
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>" + dict.editPayment() + "</h3>\n\n");

		// payment form
		new PaymentForm(isNew, payment, em).appendHtml(html, config, dict);

		return html.toString();
	}

}
