package com.juliazozulia.wordusage.Events;

import com.juliazozulia.wordusage.Model.UserItem;

import java.util.ArrayList;

/**
 * Created by Julia on 20.11.2015.
 */
public class UsersLoadedEvent {



    final boolean isFromModelDatabase;
    final ArrayList<UserItem> users;

    public UsersLoadedEvent(ArrayList<UserItem> users, boolean isFromModelDatabase) {
        this.isFromModelDatabase = isFromModelDatabase;
        this.users = users;
    }

    public ArrayList<UserItem> getUsers() {
        return users;
    }

    public boolean isFromModelDatabase() {
        return isFromModelDatabase;
    }
}

