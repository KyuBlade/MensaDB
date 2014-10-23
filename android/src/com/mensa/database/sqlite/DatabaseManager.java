package com.mensa.database.sqlite;

import android.content.Context;

import com.mensa.database.sqlite.core.SQLiteContext;

/** @author M Rafay Aleem */
public class DatabaseManager implements com.mensa.database.sqlite.core.DatabaseManager {

    public DatabaseManager() {
    }

    @Override
    public Database getNewDatabase(SQLiteContext<?> context, String databaseName, int databaseVersion, String databaseCreateQuery, String dbOnUpgradeQuery) throws IllegalArgumentException {
	if (context.getContext() instanceof Context) {
	    return new Database((Context) context.getContext(), databaseName, databaseVersion, databaseCreateQuery, dbOnUpgradeQuery);
	} else {
	    throw new IllegalArgumentException("Context should be an android context");
	}
    }
}