package com.juliazozulia.wordusage.Utils;

import android.content.Context;

import com.juliazozulia.wordusage.Threads.LoadFrequencyThread;

import java.util.HashMap;

/**
 * Created by Julia on 03.12.2015.
 */
public class FrequencyHolder {


    String TAG = getClass().getSimpleName();

    private FrequencyHolder() {

    }

    public static HashMap<String, Frequency> getInstance() {
        return LazyFHolderHolder.INSTANCE;
    }

    private static class LazyFHolderHolder {
        private static final HashMap<String, Frequency> INSTANCE = CreateFHolder();
    }

    private static HashMap<String, Frequency> CreateFHolder() {
        HashMap<String, Frequency> fholder = new HashMap<String, Frequency>();

        return fholder;
    }


    public static String[] getNames() {
        return getInstance().keySet().toArray(new String[getCount()]);
    }

    public static Frequency getFrequencyIfExist(String name) {
        if (FrequencyHolder.getInstance().containsKey(name)) {
            return FrequencyHolder.getInstance().get(name);
        }
        return null;

    }

    public static Frequency getFrequencyForce(Context context, String name) {
        if (FrequencyHolder.getInstance().containsKey(name)) {
            return FrequencyHolder.getInstance().get(name);
        }
        new LoadFrequencyThread(context, name).start();
        return null;

    }

    public static int getCount() {
        return getInstance().size();
    }


}
