package ch.serva.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.selectors.MultipleDomainSelector;
import ch.serva.tools.Dates;
import ch.serva.tools.Doubles;

/**
 * Main page for the users which are not admins.
 * 
 * @author Lukas Blunschi
 * 
 */
public class HomePage extends AbstractUserPage {

	public static final String NAME = "home";

	public String getUserContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// user [not null here]
		User userLoggedIn = Users.getUserFromSession(req, em);

		// config
		Config config = new Config();

		// domains
		List<Domain> domainsAll = userLoggedIn.getDomains();
		List<Domain> domainsSel = null;
		if (domainsAll.size() < 3) {
			domainsSel = domainsAll;
		} else {
			domainsSel = new ArrayList<Domain>();
			String[] selDomainIdsArr = req.getParameterValues("domain_ids");
			new MultipleDomainSelector(domainsAll, selDomainIdsArr, HomePage.NAME).appendHtml(html, config, dict);
			if (selDomainIdsArr != null) {
				for (String selDomainIdStr : selDomainIdsArr) {
					Domain domain = em.find(Domain.class, Long.valueOf(selDomainIdStr));
					domainsSel.add(domain);
				}
			}
		}

		// open table and header
		html.append("<table class='tablecontent'>\n");
		html.append("<tr>\n");
		html.append("<td>").append(dict.service()).append("</td>\n");
		html.append("<td>").append(dict.from()).append("</td>\n");
		html.append("<td>").append(dict.to()).append("</td>\n");
		html.append("<td>").append(dict.pricePerYear()).append("</td>\n");
		html.append("<td>").append(dict.totalCost()).append("</td>\n");
		html.append("<td>").append(dict.payed()).append("</td>\n");
		html.append("<td>").append(dict.openCost()).append("</td>\n");
		html.append("</tr>\n");

		// loop over all domains
		for (Domain domain : domainsSel) {

			// domain name
			html.append("<tr>\n");
			html.append("<td colspan='7'>");
			html.append("<h3>").append(domain.getDomainname()).append("</h3>");
			html.append(userLoggedIn.getRelationshipString(domain, dict));
			html.append("</td>\n");
			html.append("</tr>\n");

			// loop over all services
			for (Booking booking : domain.getBookings()) {
				Service service = booking.getService();

				// booking details
				String fromStr = Dates.dateFormat.format(booking.getFrom());
				String toStr = booking.getTo() == null ? "" : Dates.dateFormat.format(booking.getTo());
				html.append("<tr>\n");
				html.append("<td>").append(service.getServicename()).append("</td>\n");
				html.append("<td>").append(fromStr).append("</td>\n");
				html.append("<td>").append(toStr).append("</td>\n");

				// cost related details
				if (userLoggedIn.getAsBillingContact(domain)) {
					Date dateTo = booking.getTo() == null ? new Date() : booking.getTo();
					int numMonths = Dates.getFullMonths(booking.getFrom(), dateTo);
					double price = service.getPrice();
					double totalCost = numMonths > 0 && price > 0.0 ? numMonths * (price / 12.0) : 0.0;
					double payed = booking.getPayed();
					double openCost = totalCost - payed;
					html.append("<td>").append(Doubles.formatter.format(price)).append("</td>\n");
					html.append("<td>").append(Doubles.formatter.format(totalCost)).append("</td>\n");
					html.append("<td>").append(Doubles.formatter.format(payed)).append("</td>\n");
					html.append("<td>").append(Doubles.formatter.format(openCost)).append("</td>\n");
				} else {
					html.append("<td colspan='4' />");
				}
				html.append("</tr>\n");
			}
		}

		// close table
		html.append("</table>\n\n");

		return html.toString();
	}

}
