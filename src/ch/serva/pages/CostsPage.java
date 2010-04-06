package ch.serva.pages;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.DomainsServicesCostTable;
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

		// domains
		List<Domain> domainsAll = new Instance(em).getDomains();
		List<Domain> domainsSel = new ArrayList<Domain>();
		String[] selDomainIdsArr = req.getParameterValues(MultipleDomainSelector.P_DOMAIN_IDS);
		new MultipleDomainSelector(domainsAll, selDomainIdsArr, CostsPage.NAME).appendHtml(html, config, dict);
		if (selDomainIdsArr != null) {
			for (String selDomainIdStr : selDomainIdsArr) {
				Domain domain = em.find(Domain.class, Long.valueOf(selDomainIdStr));
				domainsSel.add(domain);
			}
		}

		// cost table
		new DomainsServicesCostTable(domainsSel, null).appendHtml(html, config, dict);

		return html.toString();
	}

}
