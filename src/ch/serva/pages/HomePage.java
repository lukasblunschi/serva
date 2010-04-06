package ch.serva.pages;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.DomainsServicesCostTable;
import ch.serva.pages.elements.selectors.MultipleDomainSelector;

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
		List<Domain> domainsAll = userLoggedIn.getDomains();
		List<Domain> domainsSel = null;
		if (domainsAll.size() < 3) {
			domainsSel = domainsAll;
		} else {
			domainsSel = new ArrayList<Domain>();
			String[] selDomainIdsArr = req.getParameterValues("domain_ids");
			new MultipleDomainSelector(domainsAll, selDomainIdsArr, HomePage.NAME).appendHtml(html, config, dict);
			if (selDomainIdsArr != null) {
				for (String selDomainIdStr : selDomainIdsArr) {
					Domain domain = em.find(Domain.class, Long.valueOf(selDomainIdStr));
					domainsSel.add(domain);
				}
			}
		}

		new DomainsServicesCostTable(domainsSel, userLoggedIn).appendHtml(html, config, dict);

		return html.toString();
	}

}
