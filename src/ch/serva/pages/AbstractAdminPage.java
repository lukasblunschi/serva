package ch.serva.pages;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ch.serva.localization.Dictionary;

/**
 * Base class for all admin pages.
 * 
 * @author Lukas Blunschi
 * 
 */
public abstract class AbstractAdminPage extends AbstractPage {

	public final String getHtml(HttpServletRequest req, EntityManager em, Dictionary dict) {
		StringBuffer html = new StringBuffer();

		// admin menu
		html.append("<!-- admin menu -->\n");
		html.append("<div id='adminmenu'>\n");
		html.append("<ul>\n");
//		html.append("<li><a href='?page=" + ListQuestionsPage.NAME + "'>" + dict.questions() + "</a></li>\n");
//		html.append("<li><a href='?page=" + ListQuestionnairesPage.NAME + "'>" + dict.questionnaires() + "</a></li>\n");
//		html.append("<li><a href='?page=" + ListNursesPage.NAME + "'>" + dict.nurses() + "</a></li>\n");
//		html.append("<li><a href='?page=" + ListPatientsPage.NAME + "'>" + dict.patients() + "</a></li>\n");
//		html.append("<li><a href='?page=" + ViewTasksPage.NAME + "'>" + dict.viewTasks() + "</a></li>\n");
//		html.append("<li><a href='?page=" + EvaluateQuestionnairePage.NAME + "'>" + dict.evaluateQuestionnaire() + "</a></li>\n");
//		html.append("<li><a href='?page=" + FeverCurvesPage.NAME + "&amp;" + Question.F_RELEVANT + "=on'>" + dict.feverCurves() + "</a></li>\n");
		html.append("</ul>\n");
		html.append("</div>\n\n");

		// content
		html.append(getAdminContent(req, em, dict));

		return html.toString();
	}

	public abstract String getAdminContent(HttpServletRequest req, EntityManager em, Dictionary dict);

}
