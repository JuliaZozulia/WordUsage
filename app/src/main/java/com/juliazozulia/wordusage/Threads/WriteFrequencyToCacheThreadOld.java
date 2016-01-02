/*
package com.juliazozulia.wordusage.Threads;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.google.gson.Gson;
import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.CacheUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

*/
/**
 * Created by Julia on 09.12.2015.
 *//*

public class WriteFrequencyToCacheThreadOld extends Thread {

    private String TAG = getClass().getSimpleName();
    Context context;
    Frequency frequency;
    String keyUser;

    public WriteFrequencyToCacheThreadOld(Context context, String keyUser, Frequency frequency) {
        this.context = context;
        this.frequency = frequency;
        this.keyUser = keyUser;

    }

    @Override
    public void run() {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Gson gson = new Gson();

        String FILENAME = keyUser + ".txt";


        try {
            File fileToEdit = new File(context.getExternalCacheDir().getPath() + "/" + FILENAME);
            Log.v(TAG, "fileToEdit" + fileToEdit.getName());
            FileOutputStream fos = new FileOutputStream(fileToEdit);
            OutputStreamWriter osr = new OutputStreamWriter(fos);
            BufferedWriter w = new BufferedWriter(osr);


            synchronized (this) {
                String s = gson.toJson(frequency);
                Log.v(TAG, "json text" + s);
                try {
                    w.write(s);
                    w.flush();
                    fos.getFD().sync();
                    Log.v(TAG, "successfully saved ");
                } finally {
                    w.close();
                    osr.close();
                    fos.close();
                }
            }

        } catch (IOException e) {
            Log.e(TAG, "Exception saving JSON", e);
        }
    }
}
*/
