package ch.serva.pages.elements;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.localization.Dictionary;

/**
 * An element that shows the relationships of a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainRelationshipsBox implements Element {

	private final Domain domain;

	public DomainRelationshipsBox(Domain domain) {
		this.domain = domain;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		html.append("<!-- domain relationships box -->\n");
		html.append("<div class='userinfobox'>\n");
		html.append("<div class='subtitle'>").append(dict.relationships()).append("</div>\n");

		html.append("<table>\n");

		html.append("<tr>");
		html.append("<td>").append(dict.holder() + ":").append("</td>");
		html.append("<td>").append(domain.getHolder().toFullName()).append("</td>");
		html.append("</tr>\n");

		html.append("<tr>");
		html.append("<td>").append(dict.billingcontact() + ":").append("</td>");
		html.append("<td>").append(domain.getBillingcontact().toFullName()).append("</td>");
		html.append("</tr>\n");

		html.append("<tr>");
		html.append("<td>").append(dict.technicalcontact() + ":").append("</td>");
		html.append("<td>").append(domain.getTechnicalcontact().toFullName()).append("</td>");
		html.append("</tr>\n");

		html.append("<tr>");
		html.append("<td>").append(dict.hostingcontact() + ":").append("</td>");
		html.append("<td>").append(domain.getHostingcontact().toFullName()).append("</td>");
		html.append("</tr>\n");

		html.append("</table>\n");

		html.append("</div>\n\n");
	}

}
