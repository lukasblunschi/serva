package ch.serva.tools;

/**
 * A request parameter.
 * 
 * @author Lukas Blunschi
 * 
 */
public class RequestParam {

	public final String name;

	public String value;

	public RequestParam(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name + "=" + value;
	}

}
