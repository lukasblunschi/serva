package ch.serva.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Tools to access properties.
 * 
 * @author Lukas Blunschi
 * 
 */
public class Props {

	/**
	 * Load properties.
	 * 
	 * @param clazz
	 *            class which provides the classload to load the properties.
	 * @return properties
	 */
	public static Properties load(Class<?> clazz) {
		InputStream in = clazz.getClassLoader().getResourceAsStream("serva.properties");
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

}
