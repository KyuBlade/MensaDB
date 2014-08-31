package com.mensa.database.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mensa.database.sqlite.core.SQLiteException;
import com.mensa.database.sqlite.core.SQLiteRuntimeException;

public class Database implements com.mensa.database.sqlite.core.Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    
    private SQLiteDatabaseHelper helper;
    private SQLiteDatabase database;
    private Context context;
    private PreparedStatement preparedStatement;

    private final String dbName;
    private final int dbVersion;
    private final String dbOnCreateQuery;
    private final String dbOnUpgradeQuery;

    protected Database(Context context, String dbName, int dbVersion, String dbOnCreateQuery, String dbOnUpgradeQuery) {
	this.context = context;
	this.dbName = dbName;
	this.dbVersion = dbVersion;
	this.dbOnCreateQuery = dbOnCreateQuery;
	this.dbOnUpgradeQuery = dbOnUpgradeQuery;
    }

    @Override
    public void setupDatabase() {
	helper = new SQLiteDatabaseHelper(context, dbName, null, dbVersion, dbOnCreateQuery, dbOnUpgradeQuery);
    }

    @Override
    public void openOrCreateDatabase() throws SQLiteException {
	try {
	    database = helper.getWritableDatabase();
	    preparedStatement = new PreparedStatement();
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public void closeDatabase() throws SQLiteException {
	try {
	    helper.close();
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public void beginTransaction() throws SQLiteException {
	database.beginTransaction();
    }

    @Override
    public void commit() throws SQLiteException {
	database.setTransactionSuccessful();
    }

    public void endTransaction() throws SQLiteException {
	database.endTransaction();
    }

    @Override
    public void execSQL(String sql) throws SQLiteException {
	try {
	    database.execSQL(sql);
	} catch (SQLException e) {
	    throw new com.mensa.database.sqlite.core.SQLiteException(e);
	}
    }

    @Override
    public DatabaseCursor rawQuery(String sql) throws SQLiteException {
	DatabaseCursor aCursor = new DatabaseCursor();
	try {
	    Cursor tmp = database.rawQuery(sql, null);
	    aCursor.setNativeCursor(tmp);
	    return aCursor;
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public DatabaseCursor rawQuery(com.mensa.database.sqlite.core.DatabaseCursor cursor, String sql) throws SQLiteException {
	DatabaseCursor aCursor = (DatabaseCursor) cursor;
	try {
	    Cursor tmp = database.rawQuery(sql, null);
	    aCursor.setNativeCursor(tmp);
	    return aCursor;
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteException(e);
	}
    }

    @Override
    public long getLastRowId() throws SQLiteRuntimeException {
	try {
	    return rawQuery("SELECT last_insert_rowid();").getLong(0);
	} catch (SQLiteException e) {
	    logger.warn("There was an error in getting the last generated id", e);
	    throw new SQLiteRuntimeException(e);
	}
    }

    @Override
    public com.mensa.database.sqlite.core.PreparedStatement getPreparedStatement(String query) throws SQLiteRuntimeException {
	preparedStatement.setStatement(database.compileStatement(query));

	return (com.mensa.database.sqlite.core.PreparedStatement) preparedStatement;
    }

}
