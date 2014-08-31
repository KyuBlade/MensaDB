package com.badlogic.gdx.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.sqlite.core.SQLiteException;
import com.badlogic.gdx.sqlite.core.SQLiteRuntimeException;

public class Database implements com.badlogic.gdx.sqlite.core.Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private SQLiteDatabaseHelper helper;

    private final String dbName;
    private final int dbVersion;
    private final String dbOnCreateQuery;
    private final String dbOnUpgradeQuery;

    private Connection connection;
    private Statement stmt;
    private PreparedStatement preparedStatement;

    protected Database(String dbName, int dbVersion, String dbOnCreateQuery, String dbOnUpgradeQuery) {
	this.dbName = dbName;
	this.dbVersion = dbVersion;
	this.dbOnCreateQuery = dbOnCreateQuery;
	this.dbOnUpgradeQuery = dbOnUpgradeQuery;
    }

    @Override
    public void setupDatabase() {
	try {
	    Class.forName("org.sqlite.JDBC");
	} catch (ClassNotFoundException e) {
	    logger.error("Unable to load the SQLite JDBC driver. Their might be a problem with your build path or project setup.", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public void openOrCreateDatabase() throws SQLiteException {
	if (helper == null)
	    helper = new SQLiteDatabaseHelper(dbName, dbVersion, dbOnCreateQuery, dbOnUpgradeQuery);

	try {
	    connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
	    stmt = connection.createStatement();
	    preparedStatement = new PreparedStatement();
	    helper.onCreate(stmt);
	} catch (SQLException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public void closeDatabase() throws SQLiteException {
	try {
	    stmt.close();
	    connection.close();
	} catch (SQLException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public void beginTransaction() throws SQLiteException {
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    logger.error("Error when begining transaction", e);
	    throw new SQLiteException(e);
	}
    }

    @Override
    public void commit() throws SQLiteException {
	try {
	    connection.commit();
	} catch (SQLException e) {
	    logger.error("Can't commit batch to database", e);
	    throw new SQLiteException(e);
	}
    }

    @Override
    public void endTransaction() throws SQLiteException {
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    logger.error("Error when ending transaction", e);
	    throw new SQLiteException(e);
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
	DatabaseCursor lCursor = new com.badlogic.gdx.sqlite.DatabaseCursor();
	try {
	    ResultSet resultSetRef = stmt.executeQuery(sql);
	    lCursor.setNativeCursor(resultSetRef);
	    return lCursor;
	} catch (SQLException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public com.badlogic.gdx.sqlite.core.DatabaseCursor rawQuery(com.badlogic.gdx.sqlite.core.DatabaseCursor cursor, String sql) throws SQLiteException {
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
	    logger.error("There was an error in getting the last generated id", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public PreparedStatement getPreparedStatement(String query) throws SQLiteException {
	try {
	    java.sql.PreparedStatement _statement = connection.prepareStatement(query);
	    preparedStatement.setStatement(_statement);

	} catch (SQLException e) {
	    logger.error("There was an error in getting the prepared statement for query : " + query, e);
	    throw new SQLiteException(e);
	}

	return preparedStatement;
    }

}
