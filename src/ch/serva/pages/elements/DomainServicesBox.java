package ch.serva.pages.elements;

import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;

/**
 * An element that shows the services of a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainServicesBox implements Element {

	private final Domain domain;

	private final boolean showCost;

	public DomainServicesBox(Domain domain, boolean showCost) {
		this.domain = domain;
		this.showCost = showCost;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// open div and subtitle
		html.append("<!-- domain services box -->\n");
		html.append("<div class='userinfobox'>\n");
		html.append("<div class='subtitle'>").append(dict.services()).append("</div>\n");

		// open table and header row
		html.append("<table>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.service()).append("</td>");
		html.append("<td>").append(dict.from()).append("</td>");
		html.append("<td>").append(dict.to()).append("</td>");
		if (showCost) {
			html.append("<td class='currency'>").append(dict.pricePerYear()).append("</td>");
			html.append("<td class='currency'>").append(dict.totalCost()).append("</td>");
			html.append("<td class='currency'>").append(dict.payed()).append("</td>");
			html.append("<td class='currency'>").append(dict.openCost()).append("</td>");
		}
		html.append("</tr>\n");

		// sums
		double sumPrice = 0.0;
		double sumPriceActive = 0.0;
		double sumTotalCost = 0.0;
		double sumPayed = 0.0;
		double sumOpenCost = 0.0;

		// loop over all services
		for (Booking booking : domain.getBookings()) {
			Service service = booking.getService();

			// booking
			String fromStr = Dates.dateFormat.format(booking.getFrom());
			String toStr = booking.getTo() == null ? "" : Dates.dateFormat.format(booking.getTo());
			html.append("<tr>");
			html.append("<td>").append(service.getServicename()).append("</td>");
			html.append("<td>").append(fromStr).append("</td>");
			html.append("<td>").append(toStr).append("</td>");

			// cost
			if (showCost) {
				double price = service.getPrice();
				double totalCost = booking.getTotalCost();
				double payed = booking.getPayed();
				double openCost = totalCost - payed;
				html.append("<td class='currency'>").append(Doubles.formatter.format(price)).append("</td>");
				html.append("<td class='currency'>").append(Doubles.formatter.format(totalCost)).append("</td>");
				html.append("<td class='currency'>").append(Doubles.formatter.format(payed)).append("</td>");
				html.append("<td class='currency'>").append(Doubles.formatter.format(openCost)).append("</td>");

				// sum
				sumPrice += price;
				sumPriceActive += booking.isActive() ? price : 0.0;
				sumTotalCost += totalCost;
				sumPayed += payed;
				sumOpenCost += openCost;
			}

			html.append("</tr>\n");
		}

		// sum row
		if (showCost) {
			html.append("<tr>");
			html.append("<td colspan='3'>").append(dict.sum() + ": ").append("</td>");
			html.append("<td class='currency'>").append(Doubles.formatter.format(sumPrice)).append("</td>");
			html.append("<td class='currency'>").append(Doubles.formatter.format(sumTotalCost)).append("</td>");
			html.append("<td class='currency'>").append(Doubles.formatter.format(sumPayed)).append("</td>");
			html.append("<td class='currency'>").append(Doubles.formatter.format(sumOpenCost)).append("</td>");
			html.append("</tr>\n");
			html.append("<tr>");
			html.append("<td colspan='3'>").append(dict.sumActive() + ": ").append("</td>");
			html.append("<td class='currency'>").append(Doubles.formatter.format(sumPriceActive)).append("</td>");
			html.append("<td colspan='3' />");
			html.append("</tr>\n");
		}

		// close table
		html.append("</table>\n");

		// close div
		html.append("</div>\n\n");
	}

}
