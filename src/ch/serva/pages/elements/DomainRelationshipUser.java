package ch.serva.pages.elements;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;

/**
 * An embedable element to show the relationship of a user to a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainRelationshipUser implements Embedable {

	private final Domain domain;

	private final User user;

	public DomainRelationshipUser(Domain domain, User user) {
		this.domain = domain;
		this.user = user;
	}

	public void appendEmbedableHtml(StringBuffer html, Config config, Dictionary dict) {
		html.append("<div class='relationship'>");
		if (user.equals(domain.getHolder())) {
			html.append("<b>H</b>, ");
		} else {
			html.append("H, ");
		}
		if (user.equals(domain.getBillingcontact())) {
			html.append("<b>B</b>, ");
		} else {
			html.append("B, ");
		}
		if (user.equals(domain.getTechnicalcontact())) {
			html.append("<b>T</b>, ");
		} else {
			html.append("T, ");
		}
		if (user.equals(domain.getHostingcontact())) {
			html.append("<b>S</b>");
		} else {
			html.append("S");
		}
		html.append("</div>\n");
	}

}
