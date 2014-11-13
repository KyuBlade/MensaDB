package com.mensa.database.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mensa.database.sqlite.core.SQLiteException;
import com.mensa.database.sqlite.core.SQLiteRuntimeException;

public class Database implements com.mensa.database.sqlite.core.Database {

    private SQLiteDatabaseHelper helper;

    private final String dbName;
    private final boolean isResource;
    private final int dbVersion;
    private final String dbOnCreateQuery;
    private final String dbOnUpgradeQuery;

    private Connection connection;
    private Statement stmt;

    protected Database(String dbName, boolean isResource, int dbVersion, String dbOnCreateQuery, String dbOnUpgradeQuery) {
	this.dbName = dbName;
	this.isResource = isResource;
	this.dbVersion = dbVersion;
	this.dbOnCreateQuery = dbOnCreateQuery;
	this.dbOnUpgradeQuery = dbOnUpgradeQuery;
    }

    @Override
    public void setupDatabase() throws SQLiteException {
	try {
	    Class.forName("org.sqlite.JDBC");
	} catch (ClassNotFoundException e) {
	    throw new SQLiteException("Unable to load the SQLite JDBC driver. Their might be a problem with your build path or project setup.", e);
	}
    }

    @Override
    public void openOrCreateDatabase() throws SQLiteException {
	if (helper == null)
	    helper = new SQLiteDatabaseHelper(dbName, dbVersion, dbOnCreateQuery, dbOnUpgradeQuery);

	try {
	    connection = DriverManager.getConnection("jdbc:sqlite:" + ((isResource) ? ":resource:" : "") + dbName);
	    stmt = connection.createStatement();
	    helper.onCreate(stmt);
	} catch (SQLException e) {
	    throw new SQLiteException("Unable to open or create database " + dbName, e);
	}
    }

    @Override
    public void closeDatabase() throws SQLiteException {
	try {
	    if (stmt != null) {
		stmt.close();
	    }
	    if (connection != null) {
		connection.close();
	    }
	} catch (SQLException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public void beginTransaction() throws SQLiteException {
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    throw new SQLiteException("Error when begining transaction", e);
	}
    }

    @Override
    public void commit() throws SQLiteException {
	try {
	    connection.commit();
	} catch (SQLException e) {
	    throw new SQLiteException("Can't commit batch to database", e);
	}
    }

    @Override
    public void endTransaction() throws SQLiteException {
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    throw new SQLiteException("Error when ending transaction", e);
	}
    }

    @Override
    public void execSQL(String sql) throws SQLiteException {
	try {
	    stmt.executeUpdate(sql);
	} catch (SQLException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public DatabaseCursor rawQuery(String sql) throws SQLiteException {
	DatabaseCursor lCursor = new com.mensa.database.sqlite.DatabaseCursor();
	try {
	    ResultSet resultSetRef = stmt.executeQuery(sql);
	    lCursor.setNativeCursor(resultSetRef);
	    return lCursor;
	} catch (SQLException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public com.mensa.database.sqlite.core.DatabaseCursor rawQuery(com.mensa.database.sqlite.core.DatabaseCursor cursor, String sql) throws SQLiteException {
	DatabaseCursor lCursor = (DatabaseCursor) cursor;
	try {
	    ResultSet resultSetRef = stmt.executeQuery(sql);
	    lCursor.setNativeCursor(resultSetRef);
	    return lCursor;
	} catch (SQLException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public long getLastRowId() throws SQLiteException {
	try {
	    return stmt.getGeneratedKeys().getLong(1);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the last generated id", e);
	}
    }

    @Override
    public PreparedStatement getPreparedStatement(String query) throws SQLiteException {
	try {
	    java.sql.PreparedStatement _statement = connection.prepareStatement(query);
	    
	    return new PreparedStatement(_statement);
	} catch (SQLException e) {
	    throw new SQLiteRuntimeException("There was an error in getting the prepared statement for query : " + query, e);
	}
    }

}
