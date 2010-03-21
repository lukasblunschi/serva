package ch.serva.pages.elements.selectors;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.serva.ServaConstants;
import ch.serva.config.Config;
import ch.serva.db.Domain;
import ch.serva.db.Instance;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;

/**
 * A domain selector.
 * <p>
 * Also supports to select none ('none') or all ('all') domains.
 * 
 * @author Lukas Blunschi
 * 
 */
public class DomainSelector implements Element {

	private final String selDomainIdStr;

	private final String page;

	private final Instance instance;

	public DomainSelector(String selDomainIdStr, String page, Instance instance) {
		this.selDomainIdStr = selDomainIdStr;
		this.page = page;
		this.instance = instance;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// prepare values
		Map<String, String> options = new TreeMap<String, String>();
		options.put("- " + dict.none(), ServaConstants.NONE);
		options.put("- " + dict.all(), ServaConstants.ALL);
		List<Domain> domains = instance.getDomains();
		for (Domain domain : domains) {
			options.put(domain.toShortString(), domain.getId().toString());
		}

		// selected value
		String selValue = selDomainIdStr == null ? ServaConstants.NONE : selDomainIdStr;

		// html
		html.append("<!-- domain selector -->\n");
		html.append("<form id='domain_selector' action='?' method='get'>\n");
		html.append("<div class='content'>\n");
		html.append("<input type='hidden' name='page' value='" + page + "' />\n");
		html.append(dict.domain() + ":\n");
		String onchange = "onchange='document.getElementById(\"domain_selector\").submit();'";
		html.append("<select id='focus_domainselector' name='domain_" + Domain.F_ID + "' " + onchange + " size='1'>\n");
		for (Map.Entry<String, String> entry : options.entrySet()) {
			String value = entry.getValue();
			if (value.equals(selValue)) {
				html.append("<option value='" + value + "' selected='selected'>" + entry.getKey() + "</option>\n");
			} else {
				html.append("<option value='" + value + "'>" + entry.getKey() + "</option>\n");
			}
		}
		html.append("</select>\n");
		html.append("</div>\n");
		html.append("</form>\n\n");
	}

}
