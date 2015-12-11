package com.juliazozulia.wordusage.Users;

import android.support.annotation.NonNull;

/**
 * Created by Julia on 24.11.2015.
 */
public class UserItem {
    String userFullName;
    String userSkypeName;
    String profileImage;

    public UserItem(String userFullName,
                     @NonNull String userSkypeName,
                    String profileImage) {

        this.userFullName = (userFullName == null) ? userSkypeName : userFullName;
        this.userSkypeName = userSkypeName;
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return userFullName;
    }

}
