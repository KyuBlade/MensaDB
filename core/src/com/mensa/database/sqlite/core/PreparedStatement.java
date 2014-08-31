package com.mensa.database.sqlite.core;

import java.io.InputStream;
import java.sql.Blob;

public interface PreparedStatement {

    public void execute() throws SQLiteException;

    public long executeInsert() throws SQLiteException;

    public int executeUpdateDelete() throws SQLiteException;
    
    public void clearParameters() throws SQLiteException;
    
    public void close() throws SQLiteException;

    public void setNull(int parameterIndex, int type) throws SQLiteException;

    public void setInt(int parameterIndex, int value) throws SQLiteException;

    public void setLong(int parameterIndex, long value) throws SQLiteException;

    public void setFloat(int parameterIndex, float value) throws SQLiteException;

    public void setDouble(int parameterIndex, double value) throws SQLiteException;

    public void setString(int parameterIndex, String value) throws SQLiteException;

    public void setBlob(int parameterIndex, Blob blob) throws SQLiteException;

    public void setBlob(int parameterIndex, InputStream stream) throws SQLiteException;

    public void setBytes(int parameterIndex, byte[] b) throws SQLiteException;

}
