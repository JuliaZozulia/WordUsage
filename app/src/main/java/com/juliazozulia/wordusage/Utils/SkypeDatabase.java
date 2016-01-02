package com.juliazozulia.wordusage.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.*;
import android.util.Log;

import com.juliazozulia.wordusage.Utils.SharedPreferencesUtils;

import java.io.File;

/**
 * Created by Julia on 25.11.2015.
 */
public class SkypeDatabase {

    private static final String NAME = "main.db";
   // public static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Documents" + "/" + NAME;
    public static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Download" + "/" + NAME;

    private SkypeDatabase() {

    }

    public static SQLiteDatabase getDatabase() {
        return LazyDatabaseHolder.INSTANCE;
    }


    private static class LazyDatabaseHolder {
        private static final SQLiteDatabase INSTANCE = CreateDatabase();
    }

    private static SQLiteDatabase CreateDatabase() {

        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
            Log.i("SkypeDatabase", "successfully opened database " + NAME);
            return db;
        } catch (SQLiteException e)

        {
            Log.w("SkypeDatabase", "could not open database " + NAME + " - " + e.getMessage());
            return null;
        }
    }

}
