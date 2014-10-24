package com.mensa.database.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mensa.database.sqlite.core.SQLiteException;
import com.mensa.database.sqlite.core.SQLiteRuntimeException;
import com.sun.corba.se.spi.activation._InitialNameServiceImplBase;

public class Database implements com.mensa.database.sqlite.core.Database {

    private SQLiteDatabaseHelper helper;
    private SQLiteDatabase database;
    private Context context;
    private PreparedStatement preparedStatement;

    private boolean isResource;
    private final String dbName;
    private final int dbVersion;
    private final String dbOnCreateQuery;
    private final String dbOnUpgradeQuery;

    protected Database(Context context, boolean isResource, String dbName, int dbVersion, String dbOnCreateQuery, String dbOnUpgradeQuery) {
	this.context = context;
	this.isResource = isResource;
	this.dbName = dbName;
	this.dbVersion = dbVersion;
	this.dbOnCreateQuery = dbOnCreateQuery;
	this.dbOnUpgradeQuery = dbOnUpgradeQuery;
    }

    @Override
    public void setupDatabase() {
    }

    @Override
    public void openOrCreateDatabase() throws IOException, SQLiteException {
	String _dbName = dbName;
	if (isResource) {
	    InputStream _stream = context.getAssets().open(dbName, SQLiteDatabase.OPEN_READONLY);
	    _dbName = context.getApplicationInfo().dataDir + '/' + dbName;
	    File _dbFile = new File(_dbName);
	    if (!_dbFile.exists()) {
		if (_dbFile.createNewFile()) {
		    OutputStream _output = new FileOutputStream(_dbFile);
		    byte[] _bytes = new byte[1024];
		    while (_stream.read(_bytes) > 0) {
			_output.write(_bytes);
		    }
		    _output.flush();
		    _output.close();
		    _stream.close();
		}
	    }

	}

	helper = new SQLiteDatabaseHelper(context, _dbName, null, dbVersion, dbOnCreateQuery, dbOnUpgradeQuery);
	database = helper.getWritableDatabase();
	preparedStatement = new PreparedStatement();
    }

    @Override
    public void closeDatabase() throws SQLiteException {
	try {
	    if (helper != null) {
		helper.close();
	    }
	    if (database != null) {
		database.close();
	    }
	    if (preparedStatement != null) {
		preparedStatement.close();
	    }
	} catch (android.database.sqlite.SQLiteException e) {
	    throw new SQLiteException("Database wasn't closed properly", e);
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
	    throw new SQLiteRuntimeException("There was an error in getting the last generated id", e);
	}
    }

    @Override
    public com.mensa.database.sqlite.core.PreparedStatement getPreparedStatement(String query) throws SQLiteRuntimeException {
	preparedStatement.setStatement(database.compileStatement(query));

	return (com.mensa.database.sqlite.core.PreparedStatement) preparedStatement;
    }

}
