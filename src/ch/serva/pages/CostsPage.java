package ch.serva.pages;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.DomainsServicesCostTable;
import ch.serva.pages.elements.common.Clearer;
import ch.serva.pages.elements.selectors.MultipleDomainSelector;

/**
 * A page to show costs.
 * 
 * @author Lukas Blunschi
 * 
 */
public class CostsPage extends AbstractAdminPage {

	public static final String NAME = "costs";

	public String getOnloadJs(HttpServletRequest req, EntityManager em) {
		return "document.getElementById(\"" + MultipleDomainSelector.ID_FOCUS + "\").focus();";
	}

	public String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// config
		Config config = new Config();

		// title
		html.append("<!-- title -->\n");
		html.append("<h3 class='content'>").append(dict.costs()).append("</h3>\n\n");

		// domains
		List<Domain> domainsSel = MultipleDomainSelector.getSelectedDomains(req, em, dict, CostsPage.NAME, html);

		// clearer
		new Clearer().appendHtml(html, config, dict);

		// cost table
		new DomainsServicesCostTable(domainsSel, null).appendHtml(html, config, dict);

		return html.toString();
	}

}
