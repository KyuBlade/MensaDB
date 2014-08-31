package com.mensa.database.sqlite.core;

/**
 * Unchecked runtime exception for SQLite used in this extension.
 * 
 * @author M Rafay Aleem
 */
public class SQLiteRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -7413657108678806286L;

    public SQLiteRuntimeException(String message) {
	super(message);
    }

    public SQLiteRuntimeException(Throwable t) {
	super(t);
    }

    public SQLiteRuntimeException(String message, Throwable t) {
	super(message, t);
    }

}
