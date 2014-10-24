package com.mensa.database.sqlite;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import com.mensa.database.sqlite.core.SQLiteException;

public class PreparedStatement implements com.mensa.database.sqlite.core.PreparedStatement {

    private java.sql.PreparedStatement statement;

    @Override
    public void execute() throws SQLiteException {
	try {
	    statement.execute();
	} catch (SQLException e) {
	    throw new SQLiteException("There is an error in executing the prepared statement", e);
	}
    }

    @Override
    public long executeInsert() throws SQLiteException {
	try {
	    statement.execute();
	    return statement.getGeneratedKeys().getLong(1);
	} catch (SQLException e) {
	    throw new SQLiteException("There is an error in executing the prepared statement", e);
	}
    }

    @Override
    public int executeUpdateDelete() throws SQLiteException {
	try {
	    return statement.executeUpdate();
	} catch (SQLException e) {
	    throw new SQLiteException("There is an error in executing the prepared statement", e);
	}
    }

    @Override
    public void clearParameters() throws SQLiteException {
	try {
	    statement.clearParameters();
	} catch (SQLException e) {
	    throw new SQLiteException("There is an error in clearing parameters the prepared statement", e);
	}
    }

    @Override
    public void close() throws SQLiteException {
	try {
	    statement.close();
	} catch (SQLException e) {
	    throw new SQLiteException("Prepared statement not closed correctly", e);
	}
    }

    @Override
    public void setNull(int parameterIndex, int type) throws SQLiteException {
	try {
	    statement.setNull(parameterIndex, type);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set null value tostatement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setInt(int parameterIndex, int value) throws SQLiteException {
	try {
	    statement.setInt(parameterIndex, value);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set int value to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setLong(int parameterIndex, long value) throws SQLiteException {
	try {
	    statement.setLong(parameterIndex, value);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set long value to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setFloat(int parameterIndex, float value) throws SQLiteException {
	try {
	    statement.setFloat(parameterIndex, value);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set float value to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setDouble(int parameterIndex, double value) throws SQLiteException {
	try {
	    statement.setDouble(parameterIndex, value);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set double value to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setString(int parameterIndex, String value) throws SQLiteException {
	try {
	    statement.setString(parameterIndex, value);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set string value to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setBlob(int parameterIndex, Blob blob) throws SQLiteException {
	try {
	    statement.setBlob(parameterIndex, blob);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set blob to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setBlob(int parameterIndex, InputStream stream) throws SQLiteException {
	try {
	    statement.setBlob(parameterIndex, stream);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set blob to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setBytes(int parameterIndex, byte[] b) throws SQLiteException {
	try {
	    statement.setBytes(parameterIndex, b);
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set bytes to statement for parameter index : " + parameterIndex, e);
	}
    }

    public void setStatement(java.sql.PreparedStatement statement) {
	this.statement = statement;
    }

}
