package com.juliazozulia.wordusage.Model;

import android.support.annotation.NonNull;

/**
 * Created by Julia on 24.11.2015.
 */
public class UserItem {

    int userID;
    String userFullName;
    String userSkypeName;
    String profileImage;

    public String getProfileImage() {
        return profileImage;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserSkypeName() {
        return userSkypeName;
    }

    public UserItem() {
    }

    public void setUser(UserItem userItem) {
        this.userID = userItem.userID;
        this.userFullName = userItem.userFullName;
        this.userSkypeName = userItem.userSkypeName;
        this.profileImage = userItem.profileImage;

    }

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
