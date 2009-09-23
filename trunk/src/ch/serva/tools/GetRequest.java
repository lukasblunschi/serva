package ch.serva.tools;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ch.serva.ServaConstants;

/**
 * Tools to help with a GET request.
 * 
 * @author Lukas Blunschi
 */
public class GetRequest {

	/**
	 * Create map containing all GET parameters of the given request.
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParameterMap(HttpServletRequest req) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enumNames = req.getParameterNames();
		while (enumNames.hasMoreElements()) {
			String name = enumNames.nextElement();
			String value = req.getParameter(name);
			map.put(name, value);
		}
		return map;
	}

	/**
	 * Reconstruct parameter string for given GET request.
	 * 
	 * @param req
	 * @return parameter string, e.g. '?a=2'
	 */
	public static String reconstructParameters(HttpServletRequest req) {

		// get parameters
		Map<String, String> map = getParameterMap(req);

		// recreate parameters string
		// removing action and language parameters.
		StringBuffer parameters = new StringBuffer();
		map.remove("action");
		map.remove(ServaConstants.A_LANGUAGE);
		boolean first = true;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (first) {
				first = false;
				parameters.append("?");
			} else {
				parameters.append("&");
			}
			parameters.append(entry.getKey()).append("=").append(entry.getValue());
		}
		return parameters.toString();
	}

}
