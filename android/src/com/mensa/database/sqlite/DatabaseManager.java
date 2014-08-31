package com.mensa.database.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.mensa.database.sqlite.core.SQLiteContext;

/** @author M Rafay Aleem */
public class DatabaseManager implements com.mensa.database.sqlite.core.DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    public DatabaseManager() {
    }

    @Override
    public Database getNewDatabase(SQLiteContext<?> context, String databaseName, int databaseVersion, String databaseCreateQuery, String dbOnUpgradeQuery) throws IllegalArgumentException {
	if (context.getContext() instanceof Context)
	{
	    return new Database((Context) context.getContext(), databaseName, databaseVersion, databaseCreateQuery, dbOnUpgradeQuery);
	} else {
	    logger.error("The given context is not an android context");
	    throw new IllegalArgumentException("Context should be an android context");
	}
    }
}