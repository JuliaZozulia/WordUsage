package com.juliazozulia.wordusage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.*;
import android.util.Log;

/**
 * Created by Julia on 25.11.2015.
 */
public class Database {

    private static final String NAME = "main.db";
    public static String AVATAR = "avatar_url";


    private Database() {

    }

    public static SQLiteDatabase getDatabase() {
        return LazyDatabaseHolder.INSTANCE;
    }


    private static class LazyDatabaseHolder {
        private static final SQLiteDatabase INSTANCE = CreateDatabase();
    }


 /*   public static String getAVATAR(){

        String[] args = {avatar};
        Cursor c =
                //    db.rawQuery("SELECT  " + Database.avatar + " , skypename FROM Contacts WHERE skypename = 'echo123'", null);
                getDatabase().rawQuery("SELECT  ? FROM Contacts", args);
        c.moveToFirst();
        if (c.getString(0) == null) {
            avatar = "avatar_url";
        }
        c.close();
        return avatar;
    }*/
    private static SQLiteDatabase CreateDatabase() {

        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory() + "/Documents" + "/" + NAME, null, SQLiteDatabase.OPEN_READONLY);
            Log.i("Database", "successfully opened database " + NAME);
            return db;
        } catch (SQLiteException e)

        {
            Log.w("Database", "could not open database " + NAME + " - " + e.getMessage());
            return null;
        }
    }

}
