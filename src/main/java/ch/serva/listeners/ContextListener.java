package ch.serva.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * ServletContextListener to close DB connections.
 */
public class ContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Context initialized.");

		// Example: set an attribute available to the app
		sce.getServletContext().setAttribute("serva.startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Context destroyed.");

		// TODO Clean up resources here if needed
	}

}
