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

    public static HashMap<Integer, Frequency> getInstance() {
        return LazyFHolderHolder.INSTANCE;
    }

    private static class LazyFHolderHolder {
        private static final HashMap<Integer, Frequency> INSTANCE = CreateFHolder();
    }

    private static HashMap<Integer, Frequency> CreateFHolder() {
        HashMap<Integer, Frequency> fholder = new HashMap<Integer, Frequency>();

        return fholder;
    }


    public static Integer[] getNames() {
        return getInstance().keySet().toArray(new Integer[getCount()]);
    }

    public static Frequency getFrequencyIfExist(Integer id) {
        if (FrequencyHolder.getInstance().containsKey(id)) {
            return FrequencyHolder.getInstance().get(id);
        }
        return null;

    }

    public static Frequency getFrequencyForce(Context context, int id) {
        if (FrequencyHolder.getInstance().containsKey(id)) {
            return FrequencyHolder.getInstance().get(id);
        }
        new LoadFrequencyThread(context, id).start();
        return null;

    }

    public static int getCount() {
        return getInstance().size();
    }


}
