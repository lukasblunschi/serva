package ch.serva.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.checks.Check;
import ch.serva.checks.Checks;
import ch.serva.checks.results.CheckResult;
import ch.serva.config.Config;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.CheckResultDiv;
import ch.serva.pages.elements.selectors.DomainSelector;
import ch.serva.tools.Props;

/**
 * A page to show checks.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ChecksPage extends AbstractAdminPage {

	public static final String NAME = "checks";

	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"focus_domainselector\").focus();";
	}

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// config
		Config config = new Config();

		// properties
		Properties properties = Props.load(this.getClass());

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.checks()).append("</h3>\n\n");

		// parameters
		// - selected domain (may also be 'none' or 'all')
		String selDomainIdStr = req.getParameter("domain_" + Domain.F_ID);

		// domain selector
		Instance instance = new Instance(em);
		new DomainSelector(selDomainIdStr, ChecksPage.NAME, instance).appendHtml(html, config, dict);

		// domains
		List<Domain> domains = new ArrayList<Domain>();
		if (selDomainIdStr == null || selDomainIdStr.equals(ServaConstants.NONE)) {
			// none
		} else if (selDomainIdStr.equals(ServaConstants.ALL)) {
			// all
			domains = instance.getDomains();
		} else {
			// one
			try {
				Long domainId = Long.valueOf(selDomainIdStr);
				Domain domain = em.find(Domain.class, domainId);
				domains.add(domain);
			} catch (Exception e) {
			}
		}

		// loop over domains
		html.append("<!-- checks -->\n");
		html.append("<table class='tablecontent'>\n");
		for (Domain domain : domains) {
			String domainname = domain.getDomainname();
			String username = domain.getUsername();

			// header
			html.append("<tr>");
			html.append("<td colspan='2'><h4>" + domain.toShortString() + "</h4></td>");
			html.append("</tr>\n");

			// loop over bookings
			List<Booking> bookings = domain.getBookings();
			for (Booking booking : bookings) {
				Service service = booking.getService();

				// check
				Check check = Checks.getByDefinition(service.getCheckDefinition());
				List<CheckResult> checkResults = null;
				if (check == null) {
					checkResults = new ArrayList<CheckResult>();
				} else {
					checkResults = check.run(domainname, username, properties);
				}

				html.append("<tr>");
				html.append("<td>").append(service.getServicename()).append("</td>");
				html.append("<td>");
				for (CheckResult checkResult : checkResults) {
					new CheckResultDiv(checkResult).appendEmbedableHtml(html, config, dict);
				}
				html.append("</td>");
				html.append("</tr>\n");

			}
		}
		html.append("</table>\n\n");

		return html.toString();
	}

}
