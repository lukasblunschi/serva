package ch.serva.pages.elements.selectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.config.Config;
import ch.serva.db.Instance;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.elements.Element;

/**
 * The services selector allows to select several services.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ServicesSelector implements Element {

	public static final String ID_FOCUS = "select_service_ids";

	public static final String P_SERVICE_IDS = "service_ids";

	private final List<Service> servicesAll;

	private final String[] selServiceIdsArr;

	private final String pagename;

	public ServicesSelector(List<Service> servicesAll, String[] selServiceIdsArr, String pagename) {
		this.servicesAll = servicesAll;
		this.selServiceIdsArr = selServiceIdsArr;
		this.pagename = pagename;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// create a set to lookup selected service ids
		HashSet<String> selServiceIds = new HashSet<String>();
		if (selServiceIdsArr != null) {
			for (String selServiceIdStr : selServiceIdsArr) {
				selServiceIds.add(selServiceIdStr);
			}
		}

		// prepare options
		Map<String, String> options = new TreeMap<String, String>();
		for (Service service : servicesAll) {
			options.put(service.toShortString(), service.getId().toString());
		}

		// html
		html.append("<!-- services selector -->\n");
		html.append("<div class='content floatleft module'>\n");
		html.append("<form id='services_selector_form' action='?' method='get'>\n");
		html.append("<table>\n");
		html.append("<tr>\n");
		html.append("<td>\n");
		html.append("<input type='hidden' name='page' value='" + pagename + "' />\n");
		html.append(dict.services() + ":");
		html.append("</td>\n");
		html.append("<td>\n");
		String js = "javascript:document.getElementById(\"services_selector_form\").submit()";
		html.append("<select id='" + ID_FOCUS + "' name='" + P_SERVICE_IDS + "' size='10' multiple='multiple' onchange='" + js + "'>\n");
		for (Map.Entry<String, String> entry : options.entrySet()) {
			String value = entry.getValue();
			if (selServiceIds.contains(value)) {
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

	public static List<Service> getSelectedServices(HttpServletRequest req, EntityManager em, Dictionary dict, String page,
			StringBuffer html) {
		Config config = new Config();
		List<Service> servicesAll = new Instance(em).getServices();
		List<Service> servicesSel = new ArrayList<Service>();
		String[] selServiceIdsArr = req.getParameterValues(ServicesSelector.P_SERVICE_IDS);
		new ServicesSelector(servicesAll, selServiceIdsArr, page).appendHtml(html, config, dict);
		if (selServiceIdsArr != null) {
			for (String selServiceIdStr : selServiceIdsArr) {
				Service service = em.find(Service.class, Long.valueOf(selServiceIdStr));
				servicesSel.add(service);
			}
		}
		return servicesSel;
	}

	public static String getParameterStr(HttpServletRequest req) {
		StringBuffer buf = new StringBuffer();
		String[] selServiceIdsArr = req.getParameterValues(ServicesSelector.P_SERVICE_IDS);
		if (selServiceIdsArr != null) {
			boolean first = true;
			for (String selServiceIdStr : selServiceIdsArr) {
				if (first) {
					first = false;
				} else {
					buf.append("&amp;");
				}
				buf.append(P_SERVICE_IDS).append("=").append(selServiceIdStr);
			}
		}
		return buf.toString();
	}

}
