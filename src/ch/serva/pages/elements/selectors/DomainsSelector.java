package ch.serva.pages.elements.selectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;

/**
 * A domain selector which allows to select several domains.
 * <p>
 * TODO refactor to DomainsSelector
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainsSelector implements Element {

	public static final String ID_FOCUS = "select_domain_ids";

	public static final String P_DOMAIN_IDS = "domain_ids";

	private final List<Domain> domainsAll;

	private final String[] selDomainIdsArr;

	private final String pagename;

	public DomainsSelector(List<Domain> domainsAll, String[] selDomainIdsArr, String pagename) {
		this.domainsAll = domainsAll;
		this.selDomainIdsArr = selDomainIdsArr;
		this.pagename = pagename;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// create a set to lookup selected domain ids
		HashSet<String> selDomainIds = new HashSet<String>();
		if (selDomainIdsArr != null) {
			for (String selDomainIdStr : selDomainIdsArr) {
				selDomainIds.add(selDomainIdStr);
			}
		}

		// prepare options
		Map<String, String> options = new TreeMap<String, String>();
		for (Domain domain : domainsAll) {
			options.put(domain.toShortString(), domain.getId().toString());
		}

		// html
		html.append("<!-- multiple domain selector -->\n");
		html.append("<div class='content floatleft module'>\n");
		html.append("<form id='multiple_domain_selector_form' action='?' method='get'>\n");
		html.append("<table>\n");
		html.append("<tr>\n");
		html.append("<td>\n");
		html.append("<input type='hidden' name='page' value='" + pagename + "' />\n");
		html.append(dict.domains() + ":");
		html.append("</td>\n");
		html.append("<td>\n");
		String js = "javascript:document.getElementById(\"multiple_domain_selector_form\").submit()";
		html.append("<select id='" + ID_FOCUS + "' name='" + P_DOMAIN_IDS + "' size='10' multiple='multiple' onchange='" + js + "'>\n");
		for (Map.Entry<String, String> entry : options.entrySet()) {
			String value = entry.getValue();
			if (selDomainIds.contains(value)) {
				html.append("<option value='" + value + "' selected='selected'>" + entry.getKey() + "</option>\n");
			} else {
				html.append("<option value='" + value + "'>" + entry.getKey() + "</option>\n");
			}
		}
		html.append("</select>\n");
		html.append("</td>\n");
		html.append("</tr>\n");
		html.append("</table>\n");
		html.append("</form>\n");
		html.append("</div>\n\n");
	}

	public static List<Domain> getSelectedDomains(HttpServletRequest req, EntityManager em, Dictionary dict, String page, StringBuffer html) {
		Config config = new Config();
		List<Domain> domainsAll = new Instance(em).getDomains();
		List<Domain> domainsSel = new ArrayList<Domain>();
		String[] selDomainIdsArr = req.getParameterValues(DomainsSelector.P_DOMAIN_IDS);
		new DomainsSelector(domainsAll, selDomainIdsArr, page).appendHtml(html, config, dict);
		if (selDomainIdsArr != null) {
			for (String selDomainIdStr : selDomainIdsArr) {
				Domain domain = em.find(Domain.class, Long.valueOf(selDomainIdStr));
				domainsSel.add(domain);
			}
		}
		return domainsSel;
	}

	public static String getParameterStr(HttpServletRequest req) {
		StringBuffer buf = new StringBuffer();
		String[] selDomainIdsArr = req.getParameterValues(DomainsSelector.P_DOMAIN_IDS);
		if (selDomainIdsArr != null) {
			boolean first = true;
			for (String selDomainIdStr : selDomainIdsArr) {
				if (first) {
					first = false;
				} else {
					buf.append("&amp;");
				}
				buf.append(P_DOMAIN_IDS).append("=").append(selDomainIdStr);
			}
		}
		return buf.toString();
	}

}
