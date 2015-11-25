/*
package com.juliazozulia.wordusage;

import android.content.Context;
import android.database.Cursor;
import android.os.Process;

import com.juliazozulia.wordusage.Users.UserItem;
import com.juliazozulia.wordusage.Users.UsersLoadedEvent;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

*/
/**
 * Created by JuliaZozulya on 09.11.2015.
 *//*

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "main.db";
    private static DatabaseHelper mDatabaseHelper = null;
*/
/*    public static final String USER_FULLNAME = "fullname";
    public static final String PROFILE_IMAGE = "avatar_url";
    public static final String USER_SKYPENAME = "skypename";*//*


    public synchronized static DatabaseHelper getInstance(Context ctxt) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(ctxt.getApplicationContext());
        }
        return (mDatabaseHelper);
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, 1);
    }


    public void loadUsers() {
        new LoadUserThread().start();
    }


    private class LoadUserThread extends Thread {

        LoadUserThread() {
            super();
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            String[] args = null;
            Cursor c =
                    getReadableDatabase().rawQuery("SELECT  fullname, skypename, avatar_url FROM Contacts", args);
            ArrayList<UserItem> users = new ArrayList<>();
            while (c.moveToNext()) {
                users.add(new UserItem(c.getString(0), c.getString(1), c.getString(2)));


            }
            c.close();
            Cursor owner =
                    getReadableDatabase().rawQuery("SELECT  fullname, skypename FROM Accounts", args);

            owner.moveToFirst();
            users.set(0, new UserItem(owner.getString(0), owner.getString(1), "have to find some icon")); //I hope that first user is always echo/sound test service
                    EventBus.getDefault().post(new UsersLoadedEvent(users));
            owner.close();

        }
    }


}

*/
