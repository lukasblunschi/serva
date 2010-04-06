package ch.serva.pages;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.DomainContactBox;
import ch.serva.pages.elements.DomainRelationshipsBox;
import ch.serva.pages.elements.DomainRelationshipUser;
import ch.serva.pages.elements.DomainServicesBox;

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
		List<Domain> domains = userLoggedIn.getDomains();

		// title
		html.append("<!-- title -->\n");
		html.append("<h1 class='content'>").append(dict.yourDomains() + ":").append("</h1>\n\n");

		// loop over all the user's domains
		for (Domain domain : domains) {

			// open domain div
			final String domainName = domain.getDomainname();
			html.append("<!-- " + domainName + " -->\n");
			html.append("<div class='domain content'>\n\n");

			// relationship
			html.append("<!-- relationship -->\n");
			html.append("<div class='floatright'>\n");
			new DomainRelationshipUser(domain, userLoggedIn).appendEmbedableHtml(html, config, dict);
			html.append("</div>\n\n");

			// info link
			html.append("<!-- show info -->\n");
			html.append("<div class='infolink'>");
			html.append("<a href='javascript:toggleDisplay(\"domain" + domain.getId() + "\")'>");
			html.append(domainName);
			html.append("</a>");
			html.append("</div>\n\n");

			// info div
			html.append("<!-- info div -->\n");
			html.append("<div id='domain" + domain.getId() + "' style='display:none'>\n\n");

			// relationships
			new DomainRelationshipsBox(domain).appendHtml(html, config, dict);

			// services
			boolean showCost = userLoggedIn.getAsBillingContact(domain);
			new DomainServicesBox(domain, showCost).appendHtml(html, config, dict);

			// contact
			new DomainContactBox(domain).appendHtml(html, config, dict);

			// clearer
			html.append("<div style='clear:both'></div>\n\n");

			// close info div
			html.append("<!-- end of info div -->\n");
			html.append("</div>\n\n");

			// close domain div
			html.append("<!-- end of " + domainName + " -->\n");
			html.append("</div>\n\n");
		}

		return html.toString();
	}

}
