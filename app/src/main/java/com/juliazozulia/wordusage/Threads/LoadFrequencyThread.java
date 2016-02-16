package com.juliazozulia.wordusage.Threads;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.google.gson.Gson;
import com.juliazozulia.wordusage.Events.FrequencyLoadStatesChanged;
import com.juliazozulia.wordusage.UI.BasicWordFrequency.FrequencyLoadedEvent;
import com.juliazozulia.wordusage.UI.Users.UserItem;
import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.Utils.GsonUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 09.12.2015.
 */

/**
 * Load Frequency from cache if possible,  or calculate it
 */
public class LoadFrequencyThread extends Thread {

    private String TAG = getClass().getSimpleName();
    UserItem keyUser;
    Context context;

    public LoadFrequencyThread(Context context, UserItem keyUser) {
        this.context = context;
        this.keyUser = keyUser;
    }

    @Override
    public void run() {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        EventBus.getDefault().post(new FrequencyLoadStatesChanged(keyUser.getUserID(),
                FrequencyLoadStatesChanged.LoadState.START_LOADING));
        Frequency f = null;

        String FILENAME = keyUser.getUserID() + ".txt";

        File fileToRead = new File(context.getExternalCacheDir().getPath() + "/" + FILENAME);

        Log.v(TAG, "fileToRead" + fileToRead.getName());
        if (fileToRead.exists()) {
            Log.v(TAG, fileToRead.getName() + "exists");
            try {
                FileInputStream fis = new FileInputStream(fileToRead);
                InputStreamReader osr = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(osr);

                synchronized (this) {
                    f = GsonUtils.getInstance().fromJson(reader, Frequency.class);
                }
                reader.close();
                osr.close();
                fis.close();


            } catch (Exception e) {
                Log.e(TAG, "Exception parsing JSON", e);
                //  new FrequencyCalculationThread(keyUser).start();
            }

        }

        // if ((f == null) || (f.getUniqueCount() == 0)) {
        if (f == null) {
            Log.e(TAG, "No saved instance. Starting calculation");
            new FrequencyCalculationThread(context, keyUser).start();
        } else {
            // new FrequencyCalculationThread(keyUser).start();
            FrequencyHolder.getInstance().remove(keyUser.getUserID());
            FrequencyHolder.getInstance().put(keyUser.getUserID(), f);
            Log.v(TAG, "added new f for " + keyUser);

            EventBus.getDefault().post(new FrequencyLoadedEvent(keyUser.getUserID(), f));
        }
    }

}