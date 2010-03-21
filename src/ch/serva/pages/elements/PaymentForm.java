package ch.serva.pages.elements;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import ch.serva.ServaConstants;
import ch.serva.actions.SavePaymentAction;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Instance;
import ch.serva.db.Payment;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditPaymentPage;
import ch.serva.pages.list.ListPaymentsPage;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;
import ch.serva.tools.Escape;
import ch.serva.tools.html.Select;

/**
 * Form to edit a payment.
 * 
 * @author Lukas Blunschi
 * 
 */
public class PaymentForm implements Element {

	private final boolean isNew;

	private final Payment payment;

	private final EntityManager em;

	public PaymentForm(boolean isNew, Payment payment, EntityManager em) {
		this.isNew = isNew;
		this.payment = payment;
		this.em = em;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// element
		html.append("<!-- payment form -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.properties()).append("</h4>\n");

		// form
		String pAction = "action=" + SavePaymentAction.NAME + "&amp;";
		String pPage = "page=" + (isNew ? ListPaymentsPage.NAME : EditPaymentPage.NAME + "&amp;");
		String pId = isNew ? "" : Payment.F_ID + "=" + payment.getId();
		String action = "?" + pAction + pPage + pId;
		String enc = "multipart/form-data";
		html.append("<form id='payment_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");

		// table
		html.append("<table>\n");

		// id (hidden)
		String idStr = isNew ? ServaConstants.NEW : String.valueOf(payment.getId());
		html.append("<tr class='hidden'>");
		html.append("<td>").append(dict.id() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='hidden' name='" + Payment.F_ID + "' value='" + idStr + "' />");
		html.append(idStr);
		html.append("</td>");
		html.append("</tr>\n");

		// booking
		Map<String, String> options = new TreeMap<String, String>();
		List<Booking> bookings = new Instance(em).getBookings();
		for (Booking booking : bookings) {
			options.put(booking.toShortString(), booking.getId().toString());
		}
		String selValue = isNew ? null : payment.getBooking().getId().toString();
		html.append("<tr>");
		html.append("<td>").append(dict.booking() + ":").append("</td>");
		html.append("<td>");
		new Select("focus_booking", Payment.F_BOOKING, options, selValue).appendHtml(html);
		html.append("</td>");
		html.append("</tr>\n");

		// date
		String dateStr = Dates.dateFormat.format(payment.getDate());
		html.append("<tr>");
		html.append("<td>").append(dict.date() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' name='" + Payment.F_DATE + "' value='" + dateStr + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// amount
		String amountStr = Doubles.formatter.format(payment.getAmount());
		html.append("<tr>");
		html.append("<td>").append(dict.amount() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' name='" + Payment.F_AMOUNT + "' value='" + amountStr + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// text
		String text = Escape.safeXml(payment.getText());
		html.append("<tr>");
		html.append("<td>").append(dict.text() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' class='text' name='" + Payment.F_TEXT + "' value='" + text + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// save
		html.append("<tr>\n");
		html.append("<td />\n");
		html.append("<td>\n");
		html.append("<input type='submit' value='" + dict.save() + "' />\n");
		html.append("</td>\n");
		html.append("</tr>\n");

		// close table
		html.append("</table>\n");

		// close form
		html.append("</form>\n");

		// close element
		html.append("</div>\n\n");
	}

}
