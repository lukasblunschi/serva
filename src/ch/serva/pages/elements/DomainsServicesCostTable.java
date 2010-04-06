package ch.serva.pages.elements;

import java.util.Date;
import java.util.List;

import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;

/**
 * A table which lists a set of domains and its related costs.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainsServicesCostTable implements Element {

	private final List<Domain> domains;

	private final User user;

	/**
	 * 
	 * @param domains
	 *            domains to show
	 * @param user
	 *            user to show relationship for, or null to show everthing.
	 */
	public DomainsServicesCostTable(List<Domain> domains, User user) {
		this.domains = domains;
		this.user = user;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// open table and header
		html.append("<table class='tablecontent'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.service()).append("</td>");
		html.append("<td>").append(dict.from()).append("</td>");
		html.append("<td>").append(dict.to()).append("</td>");
		html.append("<td>").append(dict.pricePerYear()).append("</td>");
		html.append("<td>").append(dict.totalCost()).append("</td>");
		html.append("<td>").append(dict.payed()).append("</td>");
		html.append("<td>").append(dict.openCost()).append("</td>");
		html.append("</tr>\n");

		// loop over all domains
		double sumPrice = 0.0;
		double sumTotalCost = 0.0;
		double sumPayed = 0.0;
		double sumOpenCost = 0.0;
		for (Domain domain : domains) {

			// domain name
			html.append("<tr>");
			html.append("<td colspan='7'>");
			html.append("<h3>").append(domain.getDomainname()).append("</h3>");
			if (user == null) {
				new DomainRelationshipList(domain).appendEmbedableHtml(html, config, dict);
			} else {
				html.append(user.getRelationshipString(domain, dict));
			}
			html.append("</td>");
			html.append("</tr>\n");

			// loop over all services
			for (Booking booking : domain.getBookings()) {
				Service service = booking.getService();

				// booking details
				String fromStr = Dates.dateFormat.format(booking.getFrom());
				String toStr = booking.getTo() == null ? "" : Dates.dateFormat.format(booking.getTo());
				html.append("<tr>");
				html.append("<td>").append(service.getServicename()).append("</td>");
				html.append("<td>").append(fromStr).append("</td>");
				html.append("<td>").append(toStr).append("</td>");

				// cost related details
				if (user == null || user.getAsBillingContact(domain)) {
					Date dateTo = booking.getTo() == null ? new Date() : booking.getTo();
					int numMonths = Dates.getFullMonths(booking.getFrom(), dateTo);
					double price = service.getPrice();
					double totalCost = numMonths > 0 && price > 0.0 ? numMonths * (price / 12.0) : 0.0;
					double payed = booking.getPayed();
					double openCost = totalCost - payed;
					html.append("<td>").append(Doubles.formatter.format(price)).append("</td>");
					html.append("<td>").append(Doubles.formatter.format(totalCost)).append("</td>");
					html.append("<td>").append(Doubles.formatter.format(payed)).append("</td>");
					html.append("<td>").append(Doubles.formatter.format(openCost)).append("</td>");

					// sum
					sumPrice += price;
					sumTotalCost += totalCost;
					sumPayed += payed;
					sumOpenCost += openCost;
				} else {
					html.append("<td colspan='4' />");
				}
				html.append("</tr>\n");
			}
		}

		// sum row
		html.append("<tr>");
		html.append("<td colspan='7'><h3>").append(dict.sum()).append("</h3></td>");
		html.append("</tr>\n");
		html.append("<tr>");
		html.append("<td colspan='3' />");
		html.append("<td>").append(Doubles.formatter.format(sumPrice)).append("</td>");
		html.append("<td>").append(Doubles.formatter.format(sumTotalCost)).append("</td>");
		html.append("<td>").append(Doubles.formatter.format(sumPayed)).append("</td>");
		html.append("<td>").append(Doubles.formatter.format(sumOpenCost)).append("</td>");
		html.append("</tr>\n");

		// close table
		html.append("</table>\n\n");
	}

}
