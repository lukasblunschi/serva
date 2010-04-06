package ch.serva.pages.elements.selectors;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;

/**
 * A domain selector which allows to select several domains.
 * 
 * @author Lukas Blunschi
 * 
 */
public class MultipleDomainSelector implements Element {

	public static final String ID_FOCUS = "select_domain_ids";

	public static final String P_DOMAIN_IDS = "domain_ids";

	private final List<Domain> domainsAll;

	private final String[] selDomainIdsArr;

	private final String pagename;

	public MultipleDomainSelector(List<Domain> domainsAll, String[] selDomainIdsArr, String pagename) {
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
		html.append("<form id='multiple_domain_selector_form' action='?' method='get'>\n");
		html.append("<table class='content multipleselector'>\n");
		html.append("<tr>\n");
		html.append("<td>\n");
		html.append("<input type='hidden' name='page' value='" + pagename + "' />\n");
		html.append(dict.domains() + ":");
		html.append("</td>\n");
		html.append("<td>\n");
		String js = "javascript:document.getElementById(\"multiple_domain_selector_form\").submit()";
		html.append("<select id='" + ID_FOCUS + "' name='" + P_DOMAIN_IDS + "' size='5' multiple='multiple' onchange='" + js + "'>\n");
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
		html.append("</form>\n\n");
	}

}
