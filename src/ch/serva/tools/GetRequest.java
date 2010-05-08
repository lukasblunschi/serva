package ch.serva.tools;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
	 * Get a list of all parameters.
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<RequestParam> getParameters(HttpServletRequest req) {
		List<RequestParam> params = new ArrayList<RequestParam>();
		Enumeration<String> enumNames = req.getParameterNames();
		while (enumNames.hasMoreElements()) {
			String name = enumNames.nextElement();
			for (String value : req.getParameterValues(name)) {
				params.add(new RequestParam(name, value));
			}
		}
		return params;
	}

	/**
	 * Create map containing all GET parameters of the given request.
	 * <p>
	 * TODO remove this: unsafe!
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
	 * @param msg
	 *            message parameter to append (not added if null or zero-length).
	 * @return parameter string, e.g. '?a=2'
	 */
	public static String reconstructParameters(HttpServletRequest req, String msg) {

		// get parameters
		List<RequestParam> params = getParameters(req);

		// add (or concat) message
		if (msg != null && msg.trim().length() > 0) {
			boolean found = false;
			for (RequestParam param : params) {
				if (param.name.equals("msg")) {
					found = true;
					param.value += "_" + msg;
				}
			}
			if (!found) {
				params.add(new RequestParam("msg", msg));
			}
		}

		// removing action and language parameters
		for (int i = params.size() - 1; i >= 0; i--) {
			String name = params.get(i).name;
			if (name.equals("action") || name.equals(ServaConstants.A_LANGUAGE)) {
				params.remove(i);
			}
		}

		// recreate parameters string
		StringBuffer parameters = new StringBuffer();
		boolean first = true;
		for (RequestParam param : params) {
			if (first) {
				first = false;
				parameters.append("?");
			} else {
				parameters.append("&");
			}
			parameters.append(param);
		}
		return parameters.toString();
	}

}
