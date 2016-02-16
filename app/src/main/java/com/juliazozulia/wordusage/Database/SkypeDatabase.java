package com.juliazozulia.wordusage.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.*;
import android.util.Log;

import com.juliazozulia.wordusage.Utils.Contextor;

/**
 * Created by Julia on 25.11.2015.
 */
public class SkypeDatabase {

    private static final String TAG = SkypeDatabase.class.getSimpleName();
    private static final String NAME = "main.db";
    private static boolean isModelDatabase = false;
    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Documents" + "/" + NAME;
   // public static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Download" + "/" + NAME; //for more convenient testing using emulator

    private SkypeDatabase() {

    }

    public static SQLiteDatabase getDatabase() {
        return LazyDatabaseHolder.INSTANCE;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static boolean isModelDatabase() {
        return isModelDatabase;
    }

    private static class LazyDatabaseHolder {
        private static final SQLiteDatabase INSTANCE = openSkypeOrDemoDatabase();
    }

    private static SQLiteDatabase openSkypeOrDemoDatabase() {

        //try to open user db from Documents.
        //if it doesn't exist, open db from assets (demo)

        SQLiteDatabase db = createDatabase(DATABASE_NAME);
        if (db == null) {
            isModelDatabase = true;
            Log.v(TAG, "could not open user database, trying to open demo db");
            db = DemoDatabaseHelper.getInstance(Contextor.getInstance().getContext()).getReadableDatabase();
        }
        return db;

    }

    private static SQLiteDatabase createDatabase(String name) {

        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(name, null, SQLiteDatabase.OPEN_READONLY);
            Log.i(TAG, "successfully opened database " + name);
            return db;
        } catch (SQLiteException e)

        {
            Log.w(TAG, "could not open database " + name + " - " + e.getMessage());
            return null;
        }
    }

}
