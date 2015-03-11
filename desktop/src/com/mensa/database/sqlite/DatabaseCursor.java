package com.mensa.database.sqlite;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mensa.database.sqlite.core.SQLiteException;
import com.mensa.database.sqlite.core.SQLiteRuntimeException;
import com.sun.rowset.CachedRowSetImpl;

/**
 * This is a Desktop implementation of the public interface {@link DatabaseCursor}. Note that columns in JDBC are not zero-based
 * and hence +1 has been added to accomodate for this difference.
 * 
 * @author M Rafay Aleem
 */
public class DatabaseCursor implements com.mensa.database.sqlite.core.DatabaseCursor {

    /**
     * Reference of {@code CachedRowSetImpl} Class Type created for both forward, backward, and random traversing the records, as for ResultSet Class Type
     * sqlite does not support other than forward traversing
     */
    private CachedRowSetImpl resultSet = null;

    @Override
    public byte[] getBlob(int columnIndex) {
	try {
	    Blob blob = resultSet.getBlob(columnIndex + 1);
	    return blob.getBytes(1, (int) blob.length());
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the blob at column " + (columnIndex + 1), e);
	}
    }

    @Override
    public double getDouble(int columnIndex) {
	try {
	    return resultSet.getDouble(columnIndex + 1);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the double at column " + (columnIndex + 1), e);
	}
    }

    @Override
    public float getFloat(int columnIndex) {
	try {
	    return resultSet.getFloat(columnIndex + 1);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the float at column " + (columnIndex + 1), e);
	}
    }

    @Override
    public int getInt(int columnIndex) {
	try {
	    return resultSet.getInt(columnIndex + 1);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the integer at column " + (columnIndex + 1), e);
	}
    }

    @Override
    public long getLong(int columnIndex) {
	try {
	    return resultSet.getLong(columnIndex + 1);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the long at column " + (columnIndex + 1), e);
	}
    }

    @Override
    public short getShort(int columnIndex) {
	try {
	    return resultSet.getShort(columnIndex + 1);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the short at column " + (columnIndex + 1), e);
	}
    }

    @Override
    public String getString(int columnIndex) {
	try {
	    return resultSet.getString(columnIndex + 1);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the string at column " + (columnIndex + 1), e);
	}
    }

    @Override
    public boolean next() {
	try {
	    return resultSet.next();
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("Unable to move to next", e);
	}
    }

    @SuppressWarnings("deprecation")
    @Override
    @Deprecated
    public int getCount() throws SQLiteException {
	return getRowCount(resultSet);
    }

    @Override
    public void close() {
	try {
	    resultSet.close();
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("The cursor wasn't closed properly", e);
	}
    }

    private int getRowCount(ResultSet resultSet) throws SQLiteException {
	if (resultSet == null) {
	    throw new NullPointerException("The result shouldn't be null");
	}

	try {
	    resultSet.last();

	    return resultSet.getRow();
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("Unable to get count", e);
	} finally {
	    try {
		resultSet.beforeFirst();
	    } catch (SQLException e) {
		throw new SQLiteException("Unable to rewind the result", e);
	    }
	}
    }

    public void setNativeCursor(ResultSet resultSetRef) throws SQLiteException {
	try {
	    resultSet = new CachedRowSetImpl();
	    resultSet.populate(resultSetRef);
	} catch (SQLException e) {
	    throw new SQLiteException("There was an error counting the number of results", e);
	}

    }

}
