package ch.serva.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.serva.actions.Action;
import ch.serva.actions.ChangeLanguageAction;
import ch.serva.actions.LoginAction;
import ch.serva.actions.LogoutAction;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.localization.Dictionaries;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AdminPage;
import ch.serva.pages.HomePage;
import ch.serva.pages.LoginPage;
import ch.serva.pages.Page;
import ch.serva.tools.EMF;
import ch.serva.tools.GetRequest;
import ch.serva.tools.ServletResponseTools;

/**
 * The Serva servlet.
 * 
 * @author Lukas Blunschi
 * 
 */
public class ServaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private static final Logger log = Logger.getLogger(ServaServlet.class.getName());

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// create html
		StringBuffer html = new StringBuffer();

		// get access to data
		EntityManager em = EMF.get().createEntityManager();
		try {

			// logged in user
			User user = Users.getUserFromSession(req, em);
			boolean isLoggedIn = user != null;
			boolean isAdmin = user == null ? false : user.getIsAdmin();

			// prepare action and page
			Result result = null;
			Action action = null;
			String actionStr = req.getParameter("action");
			Page page = null;
			String pageStr = req.getParameter("page");

			// check for action
			if (actionStr != null) {
				if (actionStr.equals(ChangeLanguageAction.NAME)) {
					action = new ChangeLanguageAction();
				} else if (actionStr.equals(LoginAction.NAME)) {
					action = new LoginAction();
				} else if (actionStr.equals(LogoutAction.NAME)) {
					action = new LogoutAction();
				} else if (isLoggedIn) {

					// save answer and submit
					// if (actionStr.equals(SaveAnswerAction.NAME)) {
					// action = new SaveAnswerAction();
					// } else if (actionStr.equals(SubmitTaskAction.NAME)) {
					// action = new SubmitTaskAction();
					// } else if (isAdmin) {

					// save
					// if (actionStr.equals(SaveNurseAction.NAME)) {
					// action = new SaveNurseAction();
					// } else if (actionStr.equals(SavePatientAction.NAME)) {
					// action = new SavePatientAction();
					// } else if (actionStr.equals(SaveQuestionAction.NAME)) {
					// action = new SaveQuestionAction();
					// } else if (actionStr.equals(SaveQuestionnaireAction.NAME)) {
					// action = new SaveQuestionnaireAction();

					// associations
					// } else if (actionStr.equals(AddQuestionAction.NAME)) {
					// action = new AddQuestionAction();
					// } else if (actionStr.equals(AddQuestionnaireAction.NAME)) {
					// action = new AddQuestionnaireAction();
					// } else if (actionStr.equals(AddQuestionnaireToAllPatientsAction.NAME)) {
					// action = new AddQuestionnaireToAllPatientsAction();
					// } else if (actionStr.equals(RemoveQuestionAction.NAME)) {
					// action = new RemoveQuestionAction();
					// } else if (actionStr.equals(RemoveQuestionnaireAction.NAME)) {
					// action = new RemoveQuestionnaireAction();

					// special
					// } else if (actionStr.equals(DistributeLoginsAction.NAME)) {
					// action = new DistributeLoginsAction();
					// } else if (actionStr.equals(SendLoginAction.NAME)) {
					// action = new SendLoginAction();
					// } else if (actionStr.equals(DistributeNotificationsAction.NAME)) {
					// action = new DistributeNotificationsAction();
					// } else if (actionStr.equals(SendNotificationAction.NAME)) {
					// action = new SendNotificationAction();
					// }
					// }
				}
			}
			if (actionStr != null && action == null) {
				result = new Failure("Action unknown or insuffiecient rights.");
			}
			if (action != null) {
				result = action.execute(req, em);
				// if everything was okay
				// send reload (retaining get parameters)
				if (result.success) {
					String parameters = GetRequest.reconstructParameters(req);
					String uri = req.getRequestURI();
					ServletResponseTools.sendReload(req, resp, uri + parameters);
					return;
				}
			}

			// find page
			if (pageStr == null) {
				pageStr = HomePage.NAME;
			}
			if (pageStr.equals(LoginPage.NAME)) {
				page = new LoginPage();

				// logged in
			} else if (isLoggedIn) {
				if (pageStr.equals(HomePage.NAME)) {
					page = new HomePage();
					// } else if (pageStr.equals(EditAnswerPage.NAME)) {
					// page = new EditAnswerPage();

					// admin
				} else if (isAdmin) {
					if (pageStr.equals(AdminPage.NAME)) {
						page = new AdminPage();

						// list pages
						// } else if (pageStr.equals(ListNursesPage.NAME)) {
						// page = new ListNursesPage();
						// } else if (pageStr.equals(ListPatientsPage.NAME)) {
						// page = new ListPatientsPage();
						// } else if (pageStr.equals(ListQuestionsPage.NAME)) {
						// page = new ListQuestionsPage();
						// } else if (pageStr.equals(ListQuestionnairesPage.NAME)) {
						// page = new ListQuestionnairesPage();
						// } else if (pageStr.equals(ViewTasksPage.NAME)) {
						// page = new ViewTasksPage();

						// edit pages
						// } else if (pageStr.equals(EditNursePage.NAME)) {
						// page = new EditNursePage();
						// } else if (pageStr.equals(EditPatientPage.NAME)) {
						// page = new EditPatientPage();
						// } else if (pageStr.equals(EditQuestionPage.NAME)) {
						// page = new EditQuestionPage();
						// } else if (pageStr.equals(EditQuestionnairePage.NAME)) {
						// page = new EditQuestionnairePage();

						// association pages
						// } else if (pageStr.equals(EditQuestionnaireQuestionsPage.NAME)) {
						// page = new EditQuestionnaireQuestionsPage();
						// } else if (pageStr.equals(EditTasksPage.NAME)) {
						// page = new EditTasksPage();

						// evaluation
						// } else if (pageStr.equals(EvaluateQuestionnairePage.NAME)) {
						// page = new EvaluateQuestionnairePage();
						// } else if (pageStr.equals(FeverCurvesPage.NAME)) {
						// page = new FeverCurvesPage();
					}
				}
			} else {
				page = new LoginPage();
			}
			if (page == null) {
				result = new Failure("Unknown page.");
				page = new LoginPage();
			}

			// dictionary
			Dictionary dict = Dictionaries.getDictionaryFromSession(req);

			html.append(getXHtmlStrictHeader(dict));
			html.append("<body>\n\n");
			if (result != null) {
				html.append("<p class='error content'>" + result.message + "</p>\n\n");
			}
			html.append(page.getContent(req, em, dict));
			html.append("</body>\n");
			html.append("</html>\n");

		} finally {
			em.close();
		}

		ServletResponseTools.streamStringBuffer(html, "text/html", "UTF-8", 0.0, req, resp);
	}

	public StringBuffer getXHtmlStrictHeader(Dictionary dict) {
		String langCode = dict.getLanguageCode();

		StringBuffer html = new StringBuffer();
		html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" ");
		html.append("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
		html.append("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='" + langCode + "' lang='" + langCode + "'>\n");

		html.append("<head>\n");
		html.append("   <meta http-equiv='pragma' content='no-cache' />\n");
		html.append("   <meta http-equiv='expires' content='tue, 04 dec 1993 21:29:02 gmt' />\n");
		html.append("   <meta http-equiv='content-type' content='text/html; charset=utf-8' />\n");
		html.append("   <title>Serva</title>\n");
		html.append("   <link rel='stylesheet' type='text/css' href='css/serva.css' />\n");
		html.append("   <link rel='icon' type='image/gif' href='images/serva-icon.gif' />\n");
		html.append("   <script type='text/javascript' src='js/tooltips.js'></script>\n");
		html.append("</head>\n");

		return html;
	}

}
