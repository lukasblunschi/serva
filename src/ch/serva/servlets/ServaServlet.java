package ch.serva.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.serva.actions.Action;
import ch.serva.actions.AddBookingsAction;
import ch.serva.actions.ChangeLanguageAction;
import ch.serva.actions.LoginAction;
import ch.serva.actions.LogoutAction;
import ch.serva.actions.RemoveBookingAction;
import ch.serva.actions.RemoveDomainAction;
import ch.serva.actions.RemovePaymentAction;
import ch.serva.actions.RemoveServiceAction;
import ch.serva.actions.RemoveUserAction;
import ch.serva.actions.SaveBookingAction;
import ch.serva.actions.SaveDomainAction;
import ch.serva.actions.SavePaymentAction;
import ch.serva.actions.SaveServiceAction;
import ch.serva.actions.SaveUserAction;
import ch.serva.actions.SendLoginAction;
import ch.serva.actions.results.Failure;
import ch.serva.actions.results.Result;
import ch.serva.actions.results.Success;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.localization.Dictionaries;
import ch.serva.localization.Dictionary;
import ch.serva.pages.AdminPage;
import ch.serva.pages.ChecksPage;
import ch.serva.pages.CostsPage;
import ch.serva.pages.CostsUserPage;
import ch.serva.pages.HomePage;
import ch.serva.pages.LoginPage;
import ch.serva.pages.Page;
import ch.serva.pages.edit.EditBookingPage;
import ch.serva.pages.edit.EditDomainPage;
import ch.serva.pages.edit.EditPaymentPage;
import ch.serva.pages.edit.EditServicePage;
import ch.serva.pages.edit.EditUserPage;
import ch.serva.pages.list.ListBookingsPage;
import ch.serva.pages.list.ListDomainsPage;
import ch.serva.pages.list.ListPaymentsPage;
import ch.serva.pages.list.ListServicesPage;
import ch.serva.pages.list.ListUsersPage;
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

			// find action
			Action action = null;
			String actionStr = req.getParameter("action");
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
					if (isAdmin) {

						// save
						if (actionStr.equals(SaveUserAction.NAME)) {
							action = new SaveUserAction();
						} else if (actionStr.equals(SaveDomainAction.NAME)) {
							action = new SaveDomainAction();
						} else if (actionStr.equals(SaveServiceAction.NAME)) {
							action = new SaveServiceAction();
						} else if (actionStr.equals(SaveBookingAction.NAME)) {
							action = new SaveBookingAction();
						} else if (actionStr.equals(SavePaymentAction.NAME)) {
							action = new SavePaymentAction();

							// remove
						} else if (actionStr.equals(RemoveUserAction.NAME)) {
							action = new RemoveUserAction();
						} else if (actionStr.equals(RemoveDomainAction.NAME)) {
							action = new RemoveDomainAction();
						} else if (actionStr.equals(RemoveServiceAction.NAME)) {
							action = new RemoveServiceAction();
						} else if (actionStr.equals(RemoveBookingAction.NAME)) {
							action = new RemoveBookingAction();
						} else if (actionStr.equals(RemovePaymentAction.NAME)) {
							action = new RemovePaymentAction();

							// add
						} else if (actionStr.equals(AddBookingsAction.NAME)) {
							action = new AddBookingsAction();

							// special
						} else if (actionStr.equals(SendLoginAction.NAME)) {
							action = new SendLoginAction();
						}
					}
				}
			}

			// execute action (if any)
			Result result = null;
			if (actionStr != null && action == null) {
				result = new Failure("Action unknown or insuffiecient rights.");
			}
			if (action != null) {
				result = action.execute(req, em);
				// if everything was okay
				// send reload (retaining get parameters)
				if (result.success) {
					String parameters = GetRequest.reconstructParameters(req, result.message);
					String uri = req.getRequestURI();
					ServletResponseTools.sendReload(req, resp, uri + parameters);
					return;
				}
			}

			// find message (if any)
			String msg = req.getParameter("msg");
			if (msg != null) {
				result = new Success(msg);
			}

			// find page
			Page page = null;
			String pageStr = req.getParameter("page");
			if (pageStr == null) {
				pageStr = HomePage.NAME;
			}
			if (pageStr.equals(LoginPage.NAME)) {
				page = new LoginPage();

				// logged in
			} else if (isLoggedIn) {
				if (pageStr.equals(HomePage.NAME)) {
					page = new HomePage();
				} else if (pageStr.equals(CostsUserPage.NAME)) {
					page = new CostsUserPage();

					// admin
				} else if (isAdmin) {
					if (pageStr.equals(AdminPage.NAME)) {
						page = new AdminPage();

						// list pages
					} else if (pageStr.equals(ListUsersPage.NAME)) {
						page = new ListUsersPage();
					} else if (pageStr.equals(ListDomainsPage.NAME)) {
						page = new ListDomainsPage();
					} else if (pageStr.equals(ListServicesPage.NAME)) {
						page = new ListServicesPage();
					} else if (pageStr.equals(ListBookingsPage.NAME)) {
						page = new ListBookingsPage();
					} else if (pageStr.equals(ListPaymentsPage.NAME)) {
						page = new ListPaymentsPage();

						// edit pages
					} else if (pageStr.equals(EditUserPage.NAME)) {
						page = new EditUserPage();
					} else if (pageStr.equals(EditDomainPage.NAME)) {
						page = new EditDomainPage();
					} else if (pageStr.equals(EditServicePage.NAME)) {
						page = new EditServicePage();
					} else if (pageStr.equals(EditBookingPage.NAME)) {
						page = new EditBookingPage();
					} else if (pageStr.equals(EditPaymentPage.NAME)) {
						page = new EditPaymentPage();

						// special
					} else if (pageStr.equals(ChecksPage.NAME)) {
						page = new ChecksPage();
					} else if (pageStr.equals(CostsPage.NAME)) {
						page = new CostsPage();
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

			// onload javascript
			String onload = "";
			String onloadJs = page.getOnloadJs(req, em);
			if (onloadJs != null) {
				onload = " onload='" + onloadJs + "'";
			}

			// html
			html.append(getXHtmlStrictHeader(dict));
			html.append("<body" + onload + ">\n\n");
			html.append(page.getContent(req, em, dict, result));
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
		html.append("   <script type='text/javascript' src='js/serva.js'></script>\n");
		html.append("</head>\n");

		return html;
	}

}
