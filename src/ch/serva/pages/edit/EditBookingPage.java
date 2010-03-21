package ch.serva.pages.edit;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.checks.Check;
import ch.serva.checks.Checks;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.elements.BookingCheck;
import ch.serva.pages.elements.BookingForm;

/**
 * A page to edit a booking.
 * 
 * @author Lukas Blunschi
 * 
 */
public class EditBookingPage extends AbstractAdminPage {

	public static final String NAME = "editBooking";

	@Override
	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"focus_domain\").focus();";
	}

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {

		// config
		Config config = new Config();

		// get entity
		Booking booking = null;
		final String idStr = req.getParameter(Booking.F_ID);
		if (idStr == null) {
			return "id not given.";
		}
		final boolean isNew = idStr.equals(ServaConstants.NEW);
		if (isNew) {
			booking = new Booking();
		} else {
			try {
				Long id = Long.valueOf(idStr);
				booking = em.find(Booking.class, id);

				// access control
				if (booking == null) {
					return "<p class='error content'>booking does not exist!</p>";
				}
			} catch (Exception e) {
				return "id not parsable.";
			}
		}

		// create html
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>" + dict.editBooking() + "</h3>\n\n");

		// booking form
		new BookingForm(isNew, booking, em).appendHtml(html, config, dict);

		// booking check
		if (!isNew) {
			Check check = Checks.getByDefinition(booking.getService().getCheckDefinition());
			if (check != null) {
				new BookingCheck(booking).appendHtml(html, config, dict);
			}
		}

		return html.toString();
	}

}
