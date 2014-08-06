package guestbook;

import java.io.InputStream;
import java.io.Serializable;

public class InputStreamWrapper implements Serializable {

	private InputStream stream;
	public InputStreamWrapper(InputStream is) {
		stream = is;
	}
	public InputStream getInputStream() {
		return stream;
	}
}
