package ch.serva.pages.list;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.actions.RemoveDomainAction;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AbstractAdminPage;
import ch.serva.pages.edit.EditDomainPage;
import ch.serva.tools.Escape;
import ch.serva.tools.comparators.DomainComparator;

/**
 * A page which lists all domains.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ListDomainsPage extends AbstractAdminPage {

	public static final String NAME = "listDomains";

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.domains()).append("</h3>\n\n");

		// action prefixes
		String pActionRemove = "action=" + RemoveDomainAction.NAME + "&amp;";
		String pPage = "page=" + ListDomainsPage.NAME + "&amp;";
		String pDomainIdPrefLong = "domain_" + Domain.F_ID + "=";
		String removePrefix = "?" + pActionRemove + pPage + pDomainIdPrefLong;

		// domains
		html.append("<!-- domains -->\n");
		html.append("<table class='content'>\n");
		html.append("<tr>");
		html.append("<td>").append(dict.domainname()).append("</td>");
		html.append("<td>").append(dict.holder()).append("</td>");
		html.append("<td>").append(dict.billingcontact()).append("</td>");
		html.append("<td>").append(dict.technicalcontact()).append("</td>");
		html.append("<td>").append(dict.hostingcontact()).append("</td>");
		html.append("<td>").append(dict.actions()).append("</td>");
		html.append("</tr>\n");
		List<Domain> domains = new Instance(em).getDomains();
		Collections.sort(domains, new DomainComparator());
		for (Domain domain : domains) {

			// open row
			String cssClazz = domain.isActive() ? "" : " class='inactive'";
			html.append("<tr" + cssClazz + ">");

			// domainname
			html.append("<td>");
			html.append("<a href='?page=" + EditDomainPage.NAME + "&amp;id=" + domain.getId() + "'>");
			html.append(Escape.safeXml(domain.getDomainname()));
			html.append("</a>");
			html.append("</td>");

			// holder, billing-, technical- and hosting contact
			appendUser(html, domain, domain.getHolder(), Domain.F_HOLDER);
			appendUser(html, domain, domain.getBillingcontact(), Domain.F_BILLINGCONTACT);
			appendUser(html, domain, domain.getTechnicalcontact(), Domain.F_TECHNICALCONTACT);
			appendUser(html, domain, domain.getHostingcontact(), Domain.F_HOSTINGCONTACT);

			// actions
			if (domain.isRemovable()) {
				html.append("<td><a href='" + removePrefix + domain.getId() + "'>" + dict.remove() + "</a></td>");
			} else {
				html.append("<td />");
			}
			html.append("</tr>\n");
		}
		html.append("</table>\n\n");

		// actions
		html.append("<!-- actions -->\n");
		html.append("<div class='content actions'>\n");
		html.append("<span>");
		html.append("<a href='?page=" + EditDomainPage.NAME + "&amp;id=" + ServaConstants.NEW + "'>" + dict.add() + "</a>");
		html.append("</span>\n");
		html.append("</div>\n\n");

		return html.toString();
	}

	private void appendUser(StringBuffer html, Domain domain, User user, String prefix) {
		String divId = prefix + domain.getId();
		html.append("<td>");
		html.append(Escape.safeXml(user.toShortString()));
		html.append("<div class='tooltip' id='" + divId + "'>");
		html.append(Escape.safeXml(user.toFullHtml()).replaceAll("\n", "<br />"));
		html.append("</div>");
		html.append("<span onmouseover='showTT(\"" + divId + "\")' onmouseout='hideTT()'> [+]</span>");
		html.append("</td>");
	}

}
