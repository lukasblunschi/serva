package ch.serva.pages.elements;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;

import ch.serva.ServaConstants;
import ch.serva.actions.SaveDomainAction;
import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.db.User;
import ch.serva.localization.Dictionary;
import ch.serva.pages.edit.EditDomainPage;
import ch.serva.pages.list.ListDomainsPage;
import ch.serva.tools.Escape;
import ch.serva.tools.html.Select;

/**
 * Form to edit a domain.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainForm implements Element {

	private final boolean isNew;

	private final Domain domain;

	private final EntityManager em;

	public DomainForm(boolean isNew, Domain domain, EntityManager em) {
		this.isNew = isNew;
		this.domain = domain;
		this.em = em;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// element
		html.append("<!-- domain form -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.properties()).append("</h4>\n");

		// form
		String pAction = "action=" + SaveDomainAction.NAME + "&amp;";
		String pPage = "page=" + (isNew ? ListDomainsPage.NAME : EditDomainPage.NAME + "&amp;");
		String pId = isNew ? "" : Domain.F_ID + "=" + domain.getId();
		String action = "?" + pAction + pPage + pId;
		String enc = "multipart/form-data";
		html.append("<form id='domain_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");

		// table
		html.append("<table>\n");

		// id (hidden)
		String idStr = isNew ? ServaConstants.NEW : String.valueOf(domain.getId());
		html.append("<tr class='hidden'>");
		html.append("<td>").append(dict.id() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='hidden' name='" + Domain.F_ID + "' value='" + idStr + "' />");
		html.append(idStr);
		html.append("</td>");
		html.append("</tr>\n");

		// domainname
		String domainname = Escape.safeXml(domain.getDomainname());
		html.append("<tr>");
		html.append("<td>").append(dict.domainname() + ":").append("</td>");
		html.append("<td>");
		html.append("<input id='focus_domainname' type='text' class='text' name='" + Domain.F_DOMAINNAME + "' value='" + domainname
				+ "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// prepare user options
		Map<String, String> options = new TreeMap<String, String>();
		List<User> users = new Instance(em).getUsers();
		for (User user : users) {
			options.put(user.toMiddleString(), user.getId().toString());
		}

		// holder
		String selValue = isNew ? null : domain.getHolder().getId().toString();
		html.append("<tr>");
		html.append("<td>").append(dict.holder() + ":").append("</td>");
		html.append("<td>");
		new Select(Domain.F_HOLDER, options, selValue).appendHtml(html);
		html.append("</td>");
		html.append("</tr>\n");

		// billing contact
		selValue = isNew ? null : domain.getBillingcontact().getId().toString();
		html.append("<tr>");
		html.append("<td>").append(dict.billingcontact() + ":").append("</td>");
		html.append("<td>");
		new Select(Domain.F_BILLINGCONTACT, options, selValue).appendHtml(html);
		html.append("</td>");
		html.append("</tr>\n");

		// technical contact
		selValue = isNew ? null : domain.getTechnicalcontact().getId().toString();
		html.append("<tr>");
		html.append("<td>").append(dict.technicalcontact() + ":").append("</td>");
		html.append("<td>");
		new Select(Domain.F_TECHNICALCONTACT, options, selValue).appendHtml(html);
		html.append("</td>");
		html.append("</tr>\n");

		// prepare admins
		options.clear();
		List<User> admins = new Instance(em).getAdmins();
		for (User admin : admins) {
			options.put(admin.toMiddleString(), admin.getId().toString());
		}

		// hosting contact
		selValue = isNew ? null : domain.getHostingcontact().getId().toString();
		html.append("<tr>");
		html.append("<td>").append(dict.hostingcontact() + ":").append("</td>");
		html.append("<td>");
		new Select(Domain.F_HOSTINGCONTACT, options, selValue).appendHtml(html);
		html.append("</td>");
		html.append("</tr>\n");

		// save
		html.append("<tr>\n");
		html.append("<td />\n");
		html.append("<td>\n");
		html.append("<input type='submit' value='" + dict.save() + "' />\n");
		html.append("</td>\n");
		html.append("</tr>\n");

		// close table
		html.append("</table>\n");

		// close form
		html.append("</form>\n");

		// close element
		html.append("</div>\n\n");
	}

}
