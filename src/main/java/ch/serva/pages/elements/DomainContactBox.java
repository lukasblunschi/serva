package ch.serva.pages.elements;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;

/**
 * An element that shows the contact of a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainContactBox implements Element {

	private final Domain domain;

	public DomainContactBox(Domain domain) {
		this.domain = domain;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// open div and subtitle
		html.append("<!-- domain contact box -->\n");
		html.append("<div class='userinfobox'>\n");
		html.append("<div class='subtitle'>").append(dict.contact()).append("</div>\n");

		// table
		html.append("<table>\n");
		html.append("<tr>");
		html.append("<td>");
		User contact = domain.getHostingcontact();
		html.append(contact.toFullName()).append("<br />");
		html.append(dict.email() + ": <a href='mailto:" + contact.getEmail() + "'>" + contact.getEmail() + "</a><br />");
		html.append(dict.phone() + ": " + contact.getPhone());
		html.append("</td>");
		html.append("</tr>\n");
		html.append("</table>\n");

		// close div
		html.append("</div>\n\n");
	}

}
