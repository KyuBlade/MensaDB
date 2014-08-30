package com.badlogic.gdx.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.database.Cursor;

import com.badlogic.gdx.sqlite.core.SQLiteRuntimeException;


/** @author M Rafay Aleem */
public class DatabaseCursor implements com.badlogic.gdx.sqlite.core.DatabaseCursor {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseCursor.class);
    
    private Cursor cursor;

    @Override
    public byte[] getBlob(int columnIndex) {
	try {
	    return cursor.getBlob(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in getting the blob", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public double getDouble(int columnIndex) {
	try {
	    return cursor.getDouble(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in getting the double", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public float getFloat(int columnIndex) {
	try {
	    return cursor.getFloat(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in getting the float", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public int getInt(int columnIndex) {
	try {
	    return cursor.getInt(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in getting the int", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public long getLong(int columnIndex) {
	try {
	    return cursor.getLong(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in getting the long", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public short getShort(int columnIndex) {
	try {
	    return cursor.getShort(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in getting the short", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public String getString(int columnIndex) {
	try {
	    return cursor.getString(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in getting the string", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public boolean next() {
	try {
	    return cursor.moveToNext();
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in moving the cursor to next", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public int getCount() {
	int count = -1;
	try {
	    count = cursor.getCount();
	    return count;
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("Can't get count", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public void close() {
	try {
	    cursor.close();
	} catch (android.database.sqlite.SQLiteException e) {
	    logger.error("There was an error in closing the cursor", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    public void setNativeCursor(Cursor cursorRef) {
	cursor = cursorRef;
    }
}
