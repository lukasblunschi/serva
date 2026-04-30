package ch.serva.pages;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;
import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.CheckTable;
import ch.serva.pages.elements.DomainsServicesCheckTable;
import ch.serva.pages.elements.selectors.DomainSelector;

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

		// services
		List<Service> services = instance.getServices();

		// check table
		if (domains.size() == 0) {
			new CheckTable(instance.getDomains()).appendHtml(html, config, dict);
		}

		// check table for domain services
		new DomainsServicesCheckTable(domains, services).appendHtml(html, config, dict);

		return html.toString();
	}

}
