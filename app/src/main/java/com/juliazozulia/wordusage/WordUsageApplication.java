package com.juliazozulia.wordusage;

import android.app.Application;

import com.juliazozulia.wordusage.Utils.Contextor;

/**
 * Created by Julia on 29.01.2016.
 */
public class WordUsageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());

    }

}
