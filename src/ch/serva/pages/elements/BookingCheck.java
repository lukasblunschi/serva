package ch.serva.pages.elements;

import java.util.Properties;

import ch.serva.actions.results.Result;
import ch.serva.checks.Check;
import ch.serva.checks.Checks;
import ch.serva.config.Config;
import ch.serva.config.DomainToUsername;
import ch.serva.db.Booking;
import ch.serva.db.Domain;
import ch.serva.db.Service;
import ch.serva.localization.Dictionary;
import ch.serva.tools.Props;
import ch.serva.tools.TextToHtml;

/**
 * An element which runs checks for a booking and shows it results.
 * 
 * @author Lukas Blunschi
 * 
 */
public class BookingCheck implements Element {

	private final Booking booking;

	public BookingCheck(Booking booking) {
		this.booking = booking;
	}

	public void appendHtml(StringBuffer html, Config config, Dictionary dict) {

		// element
		html.append("<!-- booking check -->\n");
		html.append("<div class='content floatleft'>\n");

		// element title
		html.append("<h4>").append(dict.check()).append("</h4>\n");

		// get connected entities
		Domain domain = booking.getDomain();
		Service service = booking.getService();

		// get check
		Check check = Checks.getByDefinition(service.getCheckDefinition());

		// run
		String domainname = domain.getDomainname();
		String username = DomainToUsername.getInstance().getUsername(domainname);
		if (username == null) {
			html.append("<div class='checkproblem'>" + dict.domainUsernameNotMapped() + "</div>\n");
		} else {
			Properties properties = Props.load(this.getClass());
			Result result = check.run(domainname, username, properties);
			String msgSuffix = result.message == null ? "" : ":<br/>" + TextToHtml.toHtml(result.message);
			if (result.success) {
				html.append("<div class='checksuccess'>" + dict.success() + msgSuffix + "</div>\n");
			} else {
				html.append("<div class='checkproblem'>" + dict.failure() + msgSuffix + "</div>\n");
			}
		}

		// close element
		html.append("</div>\n\n");
	}

}
