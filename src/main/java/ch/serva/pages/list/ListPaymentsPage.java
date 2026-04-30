package ch.serva.pages.list;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.RemovePaymentAction;
import ch.serva.db.Instance;
import ch.serva.db.Payment;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.edit.EditPaymentPage;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;
import ch.serva.tools.Escape;
import ch.serva.tools.comparators.PaymentComparator;

/**
 * A page which lists all payments.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ListPaymentsPage extends AbstractAdminPage {

	public static final String NAME = "listPayments";

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.payments()).append("</h3>\n\n");

		// action prefixes
		String pActionRemove = "action=" + RemovePaymentAction.NAME + "&amp;";
		String pPage = "page=" + ListPaymentsPage.NAME + "&amp;";
		String pPaymentIdPrefLong = "payment_" + Payment.F_ID + "=";
		String removePrefix = "?" + pActionRemove + pPage + pPaymentIdPrefLong;

		// payments
		html.append("<!-- payments -->\n");
		html.append("<table class='content'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.booking()).append("</td>");
		html.append("<td>").append(dict.date()).append("</td>");
		html.append("<td>").append(dict.amount()).append("</td>");
		html.append("<td>").append(dict.text()).append("</td>");
		html.append("<td>").append(dict.actions()).append("</td>");
		html.append("</tr>\n");
		List<Payment> payments = new Instance(em).getPayments();
		Collections.sort(payments, new PaymentComparator());
		for (Payment payment : payments) {
			html.append("<tr>");

			// booking
			html.append("<td>").append(Escape.safeXml(payment.getBooking().toShortString())).append("</td>");

			// date, amount and text
			String dateStr = Dates.dateFormat.format(payment.getDate());
			String amountStr = Doubles.formatter.format(payment.getAmount());
			html.append("<td>").append(dateStr).append("</td>");
			html.append("<td>").append(amountStr).append("</td>");
			html.append("<td>").append(Escape.safeXml(payment.getText())).append("</td>");

			// actions
			html.append("<td>");
			html.append("<a href='?page=" + EditPaymentPage.NAME + "&amp;id=" + payment.getId() + "'>");
			html.append(dict.edit());
			html.append("</a>");
			if (payment.isRemovable()) {
				html.append(" <a href='" + removePrefix + payment.getId() + "'>" + dict.remove() + "</a>");
			}
			html.append("</td>");

			html.append("</tr>\n");
		}
		html.append("</table>\n\n");

		// actions
		html.append("<!-- actions -->\n");
		html.append("<div class='content actions'>\n");
		html.append("<span>");
		html.append("<a href='?page=" + EditPaymentPage.NAME + "&amp;id=" + ServaConstants.NEW + "'>" + dict.add() + "</a>");
		html.append("</span>\n");
		html.append("</div>\n\n");

		return html.toString();
	}

}
