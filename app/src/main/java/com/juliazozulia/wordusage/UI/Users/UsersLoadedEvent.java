package com.juliazozulia.wordusage.UI.Users;

import java.util.ArrayList;

/**
 * Created by Julia on 20.11.2015.
 */
public class UsersLoadedEvent {


    final ArrayList<UserItem> users;

    public UsersLoadedEvent(ArrayList<UserItem> w) {
        users = w;
    }

    public ArrayList<UserItem> getUsers() {
        return users;
    }
}

