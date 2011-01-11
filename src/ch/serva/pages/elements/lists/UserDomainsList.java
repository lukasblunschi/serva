package ch.serva.pages.elements.lists;

import java.util.List;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditDomainPage;
import ch.serva.pages.elements.Element;
import ch.serva.tools.Escape;

/**
 * A list of domains for a given user.
 * 
 * @author Lukas Blunschi
 * 
 */
public class UserDomainsList implements Element {

	private final User user;

	public UserDomainsList(User user) {
		this.user = user;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// domains
		List<Domain> domains = user.getDomains();

		// element
		html.append("<!-- user domains list -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.domains()).append("</h4>\n");

		// table
		html.append("<table>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.domainname()).append("</td>");
		html.append("<td>").append(dict.holder().substring(0, 1)).append("</td>");
		html.append("<td>").append(dict.billingcontact().substring(0, 1)).append("</td>");
		html.append("<td>").append(dict.technicalcontact().substring(0, 1)).append("</td>");
		html.append("<td>").append(dict.hostingcontact().substring(0, 1)).append("</td>");
		html.append("</tr>\n");
		for (Domain domain : domains) {
			html.append("<tr>");

			// domainname
			html.append("<td>");
			html.append("<a href='?page=" + EditDomainPage.NAME + "&amp;id=" + domain.getId() + "'>");
			html.append(Escape.safeXml(domain.getDomainname()));
			html.append("</a>");
			html.append("</td>");

			// related as what columns
			boolean isHolder = domain.getHolder().getId().equals(user.getId());
			boolean isBillingContact = domain.getBillingcontact().getId().equals(user.getId());
			boolean isTechnicalContact = domain.getTechnicalcontact().getId().equals(user.getId());
			boolean isHostingContact = domain.getHostingcontact().getId().equals(user.getId());
			html.append("<td>").append(isHolder ? dict.yes() : "").append("</td>");
			html.append("<td>").append(isBillingContact ? dict.yes() : "").append("</td>");
			html.append("<td>").append(isTechnicalContact ? dict.yes() : "").append("</td>");
			html.append("<td>").append(isHostingContact ? dict.yes() : "").append("</td>");

			html.append("</tr>\n");
		}

		// close table
		html.append("</table>\n");

		// close element
		html.append("</div>\n\n");
	}

}
