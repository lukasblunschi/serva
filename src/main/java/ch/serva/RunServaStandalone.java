package ch.serva;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ch.serva.servlets.PdfServlet;
import ch.serva.servlets.ResourcesServlet;
import ch.serva.servlets.ServaServlet;

/**
 * Run Serva as a standalone application using Jetty as its HTTP server.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RunServaStandalone {

	/**
	 * Entry point of standalone version.
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		int port = 8081;
		if (args.length > 0) {
			for (int i = 0; i < args.length; i += 2) {

				// port
				if (args[i].equals("-p")) {
					try {
						port = Integer.parseInt(args[i + 1]);
					} catch (Exception e) {
						System.err.println("Unable to parse port from command line.");
					}
				}
			}
		}
		new RunServaStandalone().run(port);
	}

	/**
	 * Run it.
	 */
	private void run(final int port) {

		// context name and port
		String contextName = "serva";
		System.out.println("Context name:      " + contextName);
		System.out.println("Port:              " + port);

		// start jetty
		try {
			Server server = new Server(port);

			// create context
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setContextPath("/" + contextName);
			server.setHandler(context);

			// add servlet(s)
			context.addServlet(new ServletHolder(new ServaServlet()), "/");
			context.addServlet(new ServletHolder(new PdfServlet()), "*.pdf");
			context.addServlet(new ServletHolder(new ResourcesServlet()), "/css/*");
			context.addServlet(new ServletHolder(new ResourcesServlet()), "/images/*");
			context.addServlet(new ServletHolder(new ResourcesServlet()), "/js/*");

			// start HTTP server
			server.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
