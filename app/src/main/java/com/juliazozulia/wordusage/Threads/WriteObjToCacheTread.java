package com.juliazozulia.wordusage.Threads;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.google.gson.Gson;
import com.juliazozulia.wordusage.Utils.Frequency;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Julia on 25.12.2015.
 */
public abstract class WriteObjToCacheTread extends Thread {

    private String TAG = getClass().getSimpleName();
    Context context;
    int keyUser;
    String filename;

    public WriteObjToCacheTread(Context context, int keyUser) {
        this.context = context;
        this.keyUser = keyUser;
        filename = Integer.toString(keyUser);

    }

    @Override
    public void run() {

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        try {
            File fileToEdit = new File(context.getExternalCacheDir().getPath() + "/" + filename + ".txt");
            Log.v(TAG, "fileToEdit" + fileToEdit.getName());
            FileOutputStream fos = new FileOutputStream(fileToEdit);
            OutputStreamWriter osr = new OutputStreamWriter(fos);
            BufferedWriter w = new BufferedWriter(osr);


            synchronized (this) {
               // String s = gson.toJson(frequency);
                String s = getJsonObj();
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

        } catch (Exception e) {
            Log.e(TAG, "Exception saving JSON", e);
        }
    }

    public abstract String getJsonObj() throws Exception ;
}
