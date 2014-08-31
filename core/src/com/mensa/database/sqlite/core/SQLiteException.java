
package com.mensa.database.sqlite.core;

/** Checked Exception for SQLite used in this extension.
 * 
 * @author M Rafay Aleem */
public class SQLiteException extends Exception {
	private static final long serialVersionUID = 123750592122585758L;

	public SQLiteException (String message) {
		super(message);
	}

	public SQLiteException (Throwable t) {
		super(t);
	}

	public SQLiteException (String message, Throwable t) {
		super(message, t);
	}

}
