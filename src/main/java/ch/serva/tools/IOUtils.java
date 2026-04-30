package ch.serva.tools;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utilities to pipe on stream into another.
 * 
 * @author Lukas Blunschi
 */
public class IOUtils {

	/**
	 * Pipe one stream into another. no close operations are applied to the streams.
	 * 
	 * @param in
	 *            read from this.
	 * @param out
	 *            write to this.
	 * @return number of bytes piped or -1 if an exception occured.
	 */
	public static long pipe(InputStream in, OutputStream out) {
		try {
			long numBytes = 0;
			byte[] buffer = new byte[4096];
			int num = -1;
			while ((num = in.read(buffer)) > 0) {
				numBytes += num;
				out.write(buffer, 0, num);
			}
			return numBytes;
		} catch (Exception e) {
			return -1;
		}
	}

}
