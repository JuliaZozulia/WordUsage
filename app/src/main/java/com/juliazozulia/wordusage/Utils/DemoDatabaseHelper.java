package com.juliazozulia.wordusage.Utils;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Julia on 30.01.2016.
 */
public class DemoDatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "main.db";
    private static final int DATABASE_VERSION = 1;
    private static DemoDatabaseHelper singleton = null;

    public DemoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public synchronized static DemoDatabaseHelper getInstance(Context context) {

        if (singleton == null) {
            singleton = new DemoDatabaseHelper(context.getApplicationContext());
        }
        return (singleton);
    }


}
