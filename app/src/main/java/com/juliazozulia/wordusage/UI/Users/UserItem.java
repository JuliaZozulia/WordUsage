package com.juliazozulia.wordusage.UI.Users;

import android.support.annotation.NonNull;

/**
 * Created by Julia on 24.11.2015.
 */
public class UserItem {

    int userID;
    String userFullName;
    String userSkypeName;
    String profileImage;

    public UserItem(int userID, String userFullName,
                     @NonNull String userSkypeName,
                    String profileImage) {

        this.userID = userID;
        this.userFullName = (userFullName == null) ? userSkypeName : userFullName;
        this.userSkypeName = userSkypeName;
        this.profileImage = profileImage;
    }


    @Override
    public String toString() {
        return userFullName;
    }

}
