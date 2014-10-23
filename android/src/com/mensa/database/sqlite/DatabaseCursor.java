package com.mensa.database.sqlite;

import android.database.Cursor;

import com.mensa.database.sqlite.core.SQLiteRuntimeException;

/** @author M Rafay Aleem */
public class DatabaseCursor implements com.mensa.database.sqlite.core.DatabaseCursor {

    private Cursor cursor;

    @Override
    public byte[] getBlob(int columnIndex) {
	try {
	    return cursor.getBlob(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the blob at column " + columnIndex, e);
	}
    }

    @Override
    public double getDouble(int columnIndex) {
	try {
	    return cursor.getDouble(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the double at column " + columnIndex, e);
	}
    }

    @Override
    public float getFloat(int columnIndex) {
	try {
	    return cursor.getFloat(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the float at column " + columnIndex, e);
	}
    }

    @Override
    public int getInt(int columnIndex) {
	try {
	    return cursor.getInt(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the integer at column " + columnIndex, e);
	}
    }

    @Override
    public long getLong(int columnIndex) {
	try {
	    return cursor.getLong(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the long at column " + columnIndex, e);
	}
    }

    @Override
    public short getShort(int columnIndex) {
	try {
	    return cursor.getShort(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the short at column " + columnIndex, e);
	}
    }

    @Override
    public String getString(int columnIndex) {
	try {
	    return cursor.getString(columnIndex);
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the string at column " + columnIndex, e);
	}
    }

    @Override
    public boolean next() {
	try {
	    return cursor.moveToNext();
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("Unable to move to next", e);
	}
    }

    @SuppressWarnings("deprecation")
    @Override
    @Deprecated
    public int getCount() {
	int count = -1;
	try {
	    count = cursor.getCount();
	    return count;
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("Unable to get count", e);
	}
    }

    @Override
    public void close() {
	try {
	    cursor.close();
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteRuntimeException("The cursor wasn't closed properly", e);
	}
    }

    public void setNativeCursor(Cursor cursorRef) {
	cursor = cursorRef;
    }
}
