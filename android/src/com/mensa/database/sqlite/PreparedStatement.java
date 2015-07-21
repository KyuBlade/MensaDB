package com.mensa.database.sqlite;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteStatement;

import com.mensa.database.sqlite.core.DatabaseCursor;
import com.mensa.database.sqlite.core.SQLiteException;

public class PreparedStatement implements com.mensa.database.sqlite.core.PreparedStatement {

    private static final int BUFFER_SIZE = 1024;

    private Database database;
    private SQLiteStatement statement;

    private String sql;
    private boolean isCompiled;
    private Map<Integer, Object> parameters;

    protected PreparedStatement(String sql, Database database) {
	this.statement = database.getDatabase().compileStatement(sql);
	this.database = database;
	this.sql = sql;
	this.parameters = new HashMap<Integer, Object>();
    }

    @Override
    public DatabaseCursor executeQuery() throws SQLiteException {
	Cursor _nativeCursor = database.getDatabase().rawQuery(sql,
		(String[]) (parameters.values().toArray()));
	DatabaseCursor _cursor = new com.mensa.database.sqlite.DatabaseCursor(_nativeCursor);

	return _cursor;
    }

    @Override
    public void execute() throws SQLiteException {
	compileStatement();

	statement.execute();
    }

    @Override
    public long executeInsert() throws SQLiteException {
	compileStatement();

	long _rowId = statement.executeInsert();

	return _rowId;
    }

    @Override
    public int executeUpdateDelete() throws SQLiteException {
	compileStatement();

	int _rowsAffected = statement.executeUpdateDelete();

	return _rowsAffected;
    }

    @Override
    public void clearParameters() throws SQLiteException {
	parameters.clear();

	if (statement == null)
	    return;

	statement.clearBindings();
    }

    @Override
    public void close() throws SQLiteException {
	if (statement != null) {
	    statement.close();
	}
    }

    @Override
    public void setNull(int parameterIndex, int type) throws SQLiteException {
	parameters.put(parameterIndex, null);
    }

    @Override
    public void setInt(int parameterIndex, int value) throws SQLiteException {
	parameters.put(parameterIndex, Integer.toString(value));
    }

    @Override
    public void setLong(int parameterIndex, long value) throws SQLiteException {
	parameters.put(parameterIndex, Long.toString(value));
    }

    @Override
    public void setFloat(int parameterIndex, float value) throws SQLiteException {
	parameters.put(parameterIndex, Float.toString(value));
    }

    @Override
    public void setDouble(int parameterIndex, double value) throws SQLiteException {
	parameters.put(parameterIndex, Double.toString(value));
    }

    @Override
    public void setString(int parameterIndex, String value) throws SQLiteException {
	parameters.put(parameterIndex, value);
    }

    @Override
    public void setBlob(int parameterIndex, Blob value) throws SQLiteException {
	parameters.put(parameterIndex, value);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream stream) throws SQLiteException {
	try {
	    ByteArrayOutputStream _output = new ByteArrayOutputStream(BUFFER_SIZE);
	    byte[] _buffer = new byte[BUFFER_SIZE];
	    int _readSize = 0;
	    while ((_readSize = stream.read(_buffer, 0, BUFFER_SIZE)) != -1) {
		_output.write(_buffer, 0, _readSize);
	    }
	    _output.flush();

	    setBytes(parameterIndex, _output.toByteArray());
	    _output.close();
	} catch (IOException e) {
	    throw new SQLiteException(
		    "Can't set blob to statement for parameter index : " + parameterIndex, e);
	}
    }

    @Override
    public void setBytes(int parameterIndex, byte[] b) throws SQLiteException {
	parameters.put(parameterIndex, b);
    }

    private void compileStatement() {
	if (!isCompiled) {
	    isCompiled = true;

	    statement = database.getDatabase().compileStatement(sql);
	    for (int i = 0; i < parameters.size(); i++) {
		Object _value = parameters.get(i);
		DatabaseUtils.bindObjectToProgram(statement, i, _value);
	    }
	}
    }

    @Override
    public String toString() {
	return statement.toString();
    }
}
