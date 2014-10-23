package com.mensa.database.sqlite.core;


/**
 * A factory class that creates new database objects and returns references to them. See {@link DatabaseFactory#getNewDatabase(String, int, String, String)} for more details.
 * 
 * @author M Rafay Aleem
 */
public class DatabaseFactory {

    private static DatabaseManager manager;

    /**
     * This is a factory method that will return a reference to an existing or a not-yet-created database. You will need to
     * manually call methods on the {@link Database} object to setup, open/create or close the database. See {@link Database} for
     * more details. <b> Note: </b> dbOnUpgradeQuery will only work on an Android device. It will be executed when you increment
     * your database version number. First, dbOnUpgradeQuery will be executed (Where you will generally perform activities such as
     * dropping the tables, etc.). Then dbOnCreateQuery will be executed. However, dbOnUpgradeQuery won't be executed on
     * downgrading the database version.
     * 
     * @param context (For android only) the application context or null for non android application
     * @param dbName the name of the database.
     * @param dbVersion number of the database (starting at 1); if the database is older, dbOnUpgradeQuery will be used to upgrade
     * the database (on Android only)
     * @param dbOnCreateQuery The query that should be executed on the creation of the database. This query would usually create
     * the necessary tables in the database.
     * @param dbOnUpgradeQuery The query that should be executed on upgrading the database from an old version to a new one.
     * @return Returns a {@link Database} object pointing to an existing or not-yet-created database.
     */
    public static Database getNewDatabase(SQLiteContext<?> context, String dbName, int dbVersion, String dbOnCreateQuery, String dbOnUpgradeQuery) throws SQLiteException {
	try {
	    manager = (DatabaseManager) Class.forName("com.mensa.database.sqlite.DatabaseManager").newInstance();
	} catch (ClassNotFoundException e) {
	    throw new SQLiteException("Unable to load database driver", e);
	} catch (InstantiationException e) {
	    throw new SQLiteException("Unable to load database driver", e);
	} catch (IllegalAccessException e) {
	    throw new SQLiteException("Unable to load database driver", e);
	}

	return manager.getNewDatabase(context, dbName, dbVersion, dbOnCreateQuery, dbOnUpgradeQuery);
    }

    private DatabaseFactory() {
    }

}
