package ch.serva.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.serva.tools.ServletResponseTools;
import ch.serva.tools.ServletTools;

/**
 * A servlet to retrieve static resources.
 * 
 * @author Lukas Blunschi
 */
public class ResourcesServlet extends HttpServlet {

	/**
	 * Required serial version UID.
	 */
	private static final long serialVersionUID = 1;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// print paths
		// request URI, e.g. /serva/resources/css/serva.css
		// context path, e.g. /serva
		// servlet path, e.g. /resources
		String uri = req.getRequestURI();
		String ctxPath = req.getContextPath();
		// String servletPath = req.getServletPath();

		// get requested resource
		String docroot = ServletTools.getDocroot(req);
		String path = docroot + "/war" + uri.substring(ctxPath.length());
		File resource = new File(path);

		// send result
		if (resource.exists()) {
			// 7200s = 2h
			ServletResponseTools.streamFile(resource, req, resp, 7200);
		} else {
			ServletResponseTools.sendNotFound(req, resp);
		}

	}

}
