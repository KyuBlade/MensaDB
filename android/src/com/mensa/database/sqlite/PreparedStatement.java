package com.mensa.database.sqlite;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import android.database.sqlite.SQLiteStatement;

import com.mensa.database.sqlite.core.SQLiteException;

public class PreparedStatement implements com.mensa.database.sqlite.core.PreparedStatement {
    
    private SQLiteStatement statement;

    public PreparedStatement() {
    }

    @Override
    public void execute() throws SQLiteException {
	statement.execute();
    }

    @Override
    public long executeInsert() throws SQLiteException {
	long _rowId = statement.executeInsert();

	return _rowId;
    }

    @Override
    public int executeUpdateDelete() throws SQLiteException {
	int _rowsAffected = statement.executeUpdateDelete();

	return _rowsAffected;
    }
    
    @Override
    public void clearParameters() throws SQLiteException {
	statement.clearBindings();
    }
    
    @Override
    public void close() throws SQLiteException {
	statement.close();
    }

    @Override
    public void setNull(int parameterIndex, int type) throws SQLiteException {
	statement.bindNull(parameterIndex);
    }

    @Override
    public void setInt(int parameterIndex, int value) throws SQLiteException {
	setLong(parameterIndex, value);
    }

    @Override
    public void setLong(int parameterIndex, long value) throws SQLiteException {
	statement.bindLong(parameterIndex, value);
    }

    @Override
    public void setFloat(int parameterIndex, float value) throws SQLiteException {
	setDouble(parameterIndex, value);
    }

    @Override
    public void setDouble(int parameterIndex, double value) throws SQLiteException {
	statement.bindDouble(parameterIndex, value);
    }

    @Override
    public void setString(int parameterIndex, String value) throws SQLiteException {
	statement.bindString(parameterIndex, value);
    }

    @Override
    public void setBlob(int parameterIndex, Blob blob) throws SQLiteException {
	try {
	    statement.bindBlob(parameterIndex, blob.getBytes(0, (int) blob.length()));
	} catch (SQLException e) {
	    throw new SQLiteException("Can't set blob to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setBlob(int parameterIndex, InputStream stream) throws SQLiteException {
	try {
	    byte[] _bytes = new byte[stream.available()];
	    stream.read(_bytes, 0, stream.available());
	    statement.bindBlob(parameterIndex, _bytes);
	} catch (IOException e) {
	    throw new SQLiteException("Can't set blob to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setBytes(int parameterIndex, byte[] b) throws SQLiteException {
	setString(parameterIndex, new String(b));
    }

    public void setStatement(SQLiteStatement statement) {
	this.statement = statement;
    }

}
