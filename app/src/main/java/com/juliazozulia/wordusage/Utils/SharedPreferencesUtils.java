package com.juliazozulia.wordusage.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Julia on 10.12.2015.
 */
public class SharedPreferencesUtils {
    private static final String SHARED_PREFERENCES_APP = "wordusage.shared.preferences";
    private static final String SHARED_PREFERENCES_LAST_MODIFIED_DB = SHARED_PREFERENCES_APP + ".last_modified";

    private static SharedPreferences getPrefs(Context c) {
        return c.getSharedPreferences(SHARED_PREFERENCES_APP, Context.MODE_PRIVATE);
    }

    public synchronized static void setLastModified(Context c, long date) {
        getPrefs(c)
                .edit()
                .putLong(SHARED_PREFERENCES_LAST_MODIFIED_DB, date)
                .apply();
    }

    public synchronized static Long getLastModified(Context c) {
        return getPrefs(c)
                .getLong(SHARED_PREFERENCES_LAST_MODIFIED_DB, 0);
    }
}
