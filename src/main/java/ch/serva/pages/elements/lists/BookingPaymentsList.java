package ch.serva.pages.elements.lists;

import java.util.List;

import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Payment;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditPaymentPage;
import ch.serva.pages.elements.Element;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;
import ch.serva.tools.Escape;

/**
 * A list of payments for a given booking.
 * 
 * @author Lukas Blunschi
 * 
 */
public class BookingPaymentsList implements Element {

	private final Booking booking;

	public BookingPaymentsList(Booking booking) {
		this.booking = booking;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// payments
		List<Payment> payments = booking.getPayments();

		// element
		html.append("<!-- booking payments list -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.payments()).append("</h4>\n");

		// table
		html.append("<table>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.date()).append("</td>");
		html.append("<td>").append(dict.amount()).append("</td>");
		html.append("<td>").append(dict.text()).append("</td>");
		html.append("</tr>\n");
		for (Payment payment : payments) {
			html.append("<tr>");

			// date
			String dateStr = Dates.dateFormat.format(payment.getDate());
			html.append("<td>");
			html.append("<a href='?page=" + EditPaymentPage.NAME + "&amp;id=" + payment.getId() + "'>");
			html.append(dateStr);
			html.append("</a>");
			html.append("</td>");

			// amount and text
			String amountStr = Doubles.formatter.format(payment.getAmount());
			html.append("<td>").append(amountStr).append("</td>");
			html.append("<td>").append(Escape.safeXml(payment.getText())).append("</td>");

			html.append("</tr>\n");
		}

		// close table
		html.append("</table>\n");

		// close element
		html.append("</div>\n\n");
	}

}
