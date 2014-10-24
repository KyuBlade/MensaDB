package com.mensa.database.sqlite;

import com.mensa.database.sqlite.core.SQLiteContext;

/** @author M Rafay Aleem */
public class DatabaseManager implements com.mensa.database.sqlite.core.DatabaseManager {

    @Override
    public Database getNewDatabase(SQLiteContext<?> context, boolean isResource, String dbName, int dbVersion, String dbOnCreateQuery, String dbOnUpgradeQuery) throws IllegalArgumentException {
	return new Database(dbName, isResource, dbVersion, dbOnCreateQuery, dbOnUpgradeQuery);
    }

}
