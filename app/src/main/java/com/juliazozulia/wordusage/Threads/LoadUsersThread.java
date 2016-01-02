package com.juliazozulia.wordusage.Threads;

import android.database.Cursor;
import android.os.Process;

import com.juliazozulia.wordusage.Utils.SkypeDatabase;
import com.juliazozulia.wordusage.UI.Users.UserItem;
import com.juliazozulia.wordusage.UI.Users.UsersLoadedEvent;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 25.11.2015.
 */
public class LoadUsersThread extends Thread {

    public LoadUsersThread() {
        super();
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Cursor c =
                SkypeDatabase.getDatabase().rawQuery("SELECT  fullname, skypename, avatar_url FROM Contacts", null);
        ArrayList<UserItem> users = new ArrayList<>();
        while (c.moveToNext()) {
            String avatar = (c.getString(2) == null) ? (makeAvatarUrl(c.getString(1))) : (c.getString(2));
            users.add(new UserItem(c.getString(0), c.getString(1), avatar));
        }
        c.close();
        Cursor owner =
                SkypeDatabase.getDatabase().rawQuery("SELECT  fullname, skypename FROM Accounts", null);

        owner.moveToFirst();
        //I hope that the first user is always echo/sound test service
        //so replace it with owner
        users.set(0, new UserItem(owner.getString(0), owner.getString(1), makeAvatarUrl(owner.getString(1))));
        EventBus.getDefault().post(new UsersLoadedEvent(users));
        owner.close();

    }

    private String makeAvatarUrl(String owner) {
        return "https://api.skype.com/users/" + owner + "/profile/avatar";
    }
}
