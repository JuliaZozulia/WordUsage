package com.juliazozulia.wordusage.Utils;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by Julia on 10.12.2015.
 */
public class CacheUtils {

    private static String TAG = CacheUtils.class.getSimpleName();


    public static void ClearCacheIfNecessary(Context context) {
        compareDate(context, new File(SkypeDatabase.DATABASE_NAME).lastModified());

    }

    private static void compareDate(Context context, long lastModified) {
        if (lastModified != SharedPreferencesUtils.getLastModified(context)) {
            SharedPreferencesUtils.setLastModified(context, lastModified);
            clearCache(context);
        }
    }

    private static void clearCache(Context context) {
        clearDirectory(context.getCacheDir().getAbsoluteFile());
    }

    private static boolean clearDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    clearDirectory(files[i]);
                } else {
                    files[i].delete();
                    Log.v(TAG, "deleting " + files[i].getName());
                }
            }
        }
        return true;
    }
}
