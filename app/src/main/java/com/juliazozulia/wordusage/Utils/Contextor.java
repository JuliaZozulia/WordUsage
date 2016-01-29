package com.juliazozulia.wordusage.Utils;

import android.content.Context;

/**
 * Created by Julia on 29.01.2016.
 */
public class Contextor {

    private static Contextor instance;
    private Context mContext;

    public static Contextor getInstance() {
        if (instance == null)
            instance = new Contextor();
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }


}
