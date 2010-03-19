package ch.serva.pages.elements;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.serva.ServaConstants;
import ch.serva.actions.SaveServiceAction;
import ch.serva.checks.Checks;
import ch.serva.config.Config;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.pages.EditServicePage;
import ch.serva.pages.ListServicesPage;
import ch.serva.tools.Doubles;
import ch.serva.tools.Escape;
import ch.serva.tools.html.Select;

/**
 * Form to edit a service.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ServiceForm implements Element {

	private final boolean isNew;

	private final Service service;

	public ServiceForm(boolean isNew, Service service) {
		this.isNew = isNew;
		this.service = service;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// element
		html.append("<!-- service form -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.properties()).append("</h4>\n");

		// form
		String pAction = "action=" + SaveServiceAction.NAME + "&amp;";
		String pPage = "page=" + (isNew ? ListServicesPage.NAME : EditServicePage.NAME + "&amp;");
		String pId = isNew ? "" : Service.F_ID + "=" + service.getId();
		String action = "?" + pAction + pPage + pId;
		String enc = "multipart/form-data";
		html.append("<form id='service_form' enctype='" + enc + "' action='" + action + "' method='post'>\n");

		// table
		html.append("<table>\n");

		// id (hidden)
		String idStr = isNew ? ServaConstants.NEW : String.valueOf(service.getId());
		html.append("<tr class='hidden'>");
		html.append("<td>").append(dict.id() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='hidden' name='" + Service.F_ID + "' value='" + idStr + "' />");
		html.append(idStr);
		html.append("</td>");
		html.append("</tr>\n");

		// service name
		String servicename = Escape.safeXml(service.getServicename());
		html.append("<tr>");
		html.append("<td>").append(dict.servicename() + ":").append("</td>");
		html.append("<td>");
		html.append("<input id='focus_servicename' type='text' class='text' name='" + Service.F_SERVICENAME + "' value='"
				+ servicename + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// price
		String priceStr = Doubles.formatter.format(service.getPrice());
		html.append("<tr>");
		html.append("<td>").append(dict.price() + ":").append("</td>");
		html.append("<td>");
		html.append("<input type='text' name='" + Service.F_PRICE + "' value='" + priceStr + "' />");
		html.append("</td>");
		html.append("</tr>\n");

		// description
		String description = Escape.safeXml(service.getDescription());
		html.append("<tr>");
		html.append("<td>").append(dict.description() + ":").append("</td>");
		html.append("<td>");
		html.append("<textarea name='" + Service.F_DESCRIPTION + "' rows='4' cols='80'>" + description + "</textarea>\n");
		html.append("</td>");
		html.append("</tr>\n");

		// check definition
		Map<String, String> options = new TreeMap<String, String>();
		options.put("-", "");
		List<String> checks = Checks.getCheckDefinitions();
		for (String checkDefinition : checks) {
			options.put(checkDefinition, checkDefinition);
		}
		String selValue = isNew ? null : service.getCheckDefinition();
		html.append("<tr>");
		html.append("<td>").append(dict.checkDefinition() + ":").append("</td>");
		html.append("<td>");
		new Select(Service.F_CHECKDEFINITION, options, selValue).appendHtml(html);
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
