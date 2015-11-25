package com.juliazozulia.wordusage.Users;

import android.database.Cursor;
import android.os.Process;

import com.juliazozulia.wordusage.Database;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 25.11.2015.
 */
class LoadUsersThread extends Thread {

    LoadUsersThread() {
        super();
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        String[] args = null;
        Cursor c =
                Database.getDatabase().rawQuery("SELECT  fullname, skypename, avatar_url FROM Contacts", args);
        ArrayList<UserItem> users = new ArrayList<>();
        while (c.moveToNext()) {
            users.add(new UserItem(c.getString(0), c.getString(1), c.getString(2)));
        }
        c.close();
        Cursor owner =
                Database.getDatabase().rawQuery("SELECT  fullname, skypename FROM Accounts", args);

        owner.moveToFirst();
        users.set(0, new UserItem(owner.getString(0), owner.getString(1), "have to find some icon")); //I hope that first user is always echo/sound test service
        EventBus.getDefault().post(new UsersLoadedEvent(users));
        owner.close();

    }
}