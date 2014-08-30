package com.badlogic.gdx.sqlite;

import com.badlogic.gdx.sqlite.core.SQLiteContext;


/** @author M Rafay Aleem */
public class DatabaseManager implements com.badlogic.gdx.sqlite.core.DatabaseManager {

    @Override
    public com.badlogic.gdx.sqlite.core.Database getNewDatabase(SQLiteContext<?> context, String dbName, int dbVersion, String dbOnCreateQuery, String dbOnUpgradeQuery) {
	return new Database(dbName, dbVersion, dbOnCreateQuery, dbOnUpgradeQuery);
    }

}
