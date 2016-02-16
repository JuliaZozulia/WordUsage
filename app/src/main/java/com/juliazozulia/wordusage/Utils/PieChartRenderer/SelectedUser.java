package com.juliazozulia.wordusage.Utils.PieChartRenderer;

import com.juliazozulia.wordusage.Model.UserItem;

/**
 * Created by Julia on 13.02.2016.
 */
public class SelectedUser {

    private SelectedUser() {
    }

    public static UserItem getInstance() {
        return LazyUserHolder.INSTANCE;
    }

    private static UserItem createUser() {
        return new UserItem();
    }

    private static class LazyUserHolder {
        private static final UserItem INSTANCE = SelectedUser.createUser();
    }
}
