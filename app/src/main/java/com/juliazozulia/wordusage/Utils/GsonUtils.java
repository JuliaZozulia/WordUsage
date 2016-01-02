package com.juliazozulia.wordusage.Utils;

import com.google.gson.Gson;

/**
 * Created by Julia on 25.12.2015.
 */
public class GsonUtils {

    private GsonUtils() {

    }

    public static Gson getInstance() {
        return LazyGsonHolder.INSTANCE;
    }

    private static Gson createGson() {
        return new Gson();
    }

    private static class LazyGsonHolder {
        private static final Gson INSTANCE = GsonUtils.createGson();
    }
}
