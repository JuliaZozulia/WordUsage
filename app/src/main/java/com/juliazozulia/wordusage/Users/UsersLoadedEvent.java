package com.juliazozulia.wordusage.Users;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Currency;

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
   /* final Cursor model;

    public UsersLoadedEvent(Cursor cursor) {
        model = cursor;
    }

    public Cursor getCursor() {
        return model;


}
*/
