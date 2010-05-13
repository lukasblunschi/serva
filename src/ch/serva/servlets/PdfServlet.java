package ch.serva.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import ch.serva.db.Domain;
import ch.serva.db.User;
import ch.serva.db.Users;
import ch.serva.export.Export;
import ch.serva.export.PdfExport;
import ch.serva.export.PdfInvoice;
import ch.serva.localization.Dictionaries;
import ch.serva.localization.Dictionary;
import ch.serva.tools.EMF;
import ch.serva.tools.GetRequest;
import ch.serva.tools.Request;
import ch.serva.tools.ServletResponseTools;
import ch.serva.tools.ServletTools;

/**
 * The PDF servlet.
 * 
 * @author Lukas Blunschi
 * 
 */
public class PdfServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private FopFactory fopFactory = FopFactory.newInstance();

	private TransformerFactory tFactory = TransformerFactory.newInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// get access to data
		EntityManager em = EMF.get().createEntityManager();
		try {

			// logged in user
			User loggedInUser = Users.getUserFromSession(req, em);
			if (loggedInUser == null) {
				ServletResponseTools.sendForbidden(req, resp, "Please login first.");
				return;
			}

			// parse GET request
			Request request = new GetRequest(req);

			// parameters
			String exportName = request.getParameter(Export.P_NAME);

			// domain
			Domain domain = null;
			try {
				String domainIdStr = request.getParameter(Domain.F_ID);
				Long id = Long.valueOf(domainIdStr);
				domain = em.find(Domain.class, id);
			} catch (Exception e) {
			}

			// access control
			if (domain == null) {
				ServletResponseTools.sendForbidden(req, resp, "Domain not found!");
				return;
			}
			boolean isBillingContact = domain.getBillingcontact().getId().equals(loggedInUser.getId());
			boolean isHostingContact = domain.getHostingcontact().getId().equals(loggedInUser.getId());
			boolean mayAccess = isBillingContact || isHostingContact;
			if (!mayAccess) {
				ServletResponseTools.sendForbidden(req, resp, "Not allowed to access this domain!");
				return;
			}

			// docroot
			String docroot = ServletTools.getDocroot(req);

			// switch on export
			PdfExport export = null;
			StringBuffer xml = new StringBuffer();
			Dictionary dict = Dictionaries.getDictionaryFromSession(req);
			if (exportName != null && exportName.equals(PdfInvoice.NAME)) {

				// PDF invoice
				export = new PdfInvoice(domain, docroot);
			} else {

				// unknown export
				ServletResponseTools.sendBadRequest(req, resp, "Unknown export name: " + exportName);
				return;
			}
			export.appendXml(xml, dict);

			// Setup a buffer to obtain the content length
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			// Setup FOP
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, out);

			// configure
			userAgent.setAuthor(loggedInUser.getUsername());
			userAgent.setCreationDate(new Date());

			// Setup Transformer
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(export.getXslFilename());
			Source xsltSrc = new StreamSource(in);
			Transformer transformer = tFactory.newTransformer(xsltSrc);

			// Make sure the XSL transformation's result is piped through to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Setup input
			Source src = new StreamSource(new StringReader(xml.toString()));

			// Start the transformation and rendering process
			transformer.transform(src, res);

			// write log okay
			final int length = out.size();
			ServletResponseTools.writeLog(req, 200, 0, length);

			// Prepare response
			resp.setContentType("application/pdf");
			resp.setContentLength(length);

			// Send content to Browser
			resp.getOutputStream().write(out.toByteArray());
			resp.getOutputStream().flush();

		} catch (Exception e) {
			ServletResponseTools.sendInternalServerError(req, resp, e.getMessage());

		} finally {
			em.close();
		}
	}

}
