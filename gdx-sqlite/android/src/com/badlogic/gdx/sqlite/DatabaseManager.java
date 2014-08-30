package com.badlogic.gdx.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;

import com.badlogic.gdx.sqlite.core.Database;
import com.badlogic.gdx.sqlite.core.SQLiteContext;

/** @author M Rafay Aleem */
public class DatabaseManager implements com.badlogic.gdx.sqlite.core.DatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    public DatabaseManager() {
    }

    @Override
    public Database getNewDatabase(SQLiteContext<?> context, String databaseName, int databaseVersion, String databaseCreateQuery, String dbOnUpgradeQuery) {
	return new com.badlogic.gdx.sqlite.Database((Context) context.getContext(), databaseName, databaseVersion, databaseCreateQuery, dbOnUpgradeQuery);
    }
}