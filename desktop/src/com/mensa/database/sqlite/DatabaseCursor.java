package com.mensa.database.sqlite;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(DatabaseCursor.class);

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
	    logger.error("There was an error in getting the blob", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public double getDouble(int columnIndex) {
	try {
	    return resultSet.getDouble(columnIndex + 1);
	} catch (SQLException e) {
	    logger.error("There was an error in getting the double", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public float getFloat(int columnIndex) {
	try {
	    return resultSet.getFloat(columnIndex + 1);
	} catch (SQLException e) {
	    logger.error("There was an error in getting the float", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public int getInt(int columnIndex) {
	try {
	    return resultSet.getInt(columnIndex + 1);
	} catch (SQLException e) {
	    logger.error("There was an error in getting the int", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public long getLong(int columnIndex) {
	try {
	    return resultSet.getLong(columnIndex + 1);
	} catch (SQLException e) {
	    logger.error("There was an error in getting the long", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public short getShort(int columnIndex) {
	try {
	    return resultSet.getShort(columnIndex + 1);
	} catch (SQLException e) {
	    logger.error("There was an error in getting the short", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public String getString(int columnIndex) {
	try {
	    return resultSet.getString(columnIndex + 1);
	} catch (SQLException e) {
	    logger.error("There was an error in getting the string", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public boolean next() {
	try {
	    return resultSet.next();
	} catch (SQLException e) {
	    logger.error("There was an error in moving the cursor to next", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public int getCount() {
	return getRowCount(resultSet);
    }

    @Override
    public void close() {
	try {
	    resultSet.close();
	} catch (SQLException e) {
	    logger.error("There was an error in closing the cursor", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    private int getRowCount(ResultSet resultSet) {

	if (resultSet == null) {
	    return 0;
	}

	try {
	    resultSet.last();

	    return resultSet.getRow();
	} catch (SQLException e) {
	    logger.error("There was an error counting the number of results", e);
	    throw new SQLiteRuntimeException(e);
	} finally {
	    try {
		resultSet.beforeFirst();
	    } catch (SQLException e) {
		logger.error("There was an error counting the number of results", e);
		throw new SQLiteRuntimeException(e);
	    }
	}
    }

    public void setNativeCursor(ResultSet resultSetRef) throws SQLiteException {
	try {
	    resultSet = new CachedRowSetImpl();
	    resultSet.populate(resultSetRef);
	} catch (SQLException e) {
	    logger.error("There was an error counting the number of results", e);
	    throw new SQLiteException(e);
	}

    }

}
