/*
 * Created on Jun 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.filesystems;

/**
 * FileSystem Exception. this exception is thrown whenever a filesystem related problem might occur (e.g. an i/o problem)
 * @author sergiu
 */
public class FSException extends Exception {

	public FSException() {
		super();
	}

	public FSException(String message) {
		super(message);
	}

	public FSException(Throwable cause) {
		super(cause);
	}

	public FSException(String message, Throwable cause) {
		super(message, cause);
	}

}
