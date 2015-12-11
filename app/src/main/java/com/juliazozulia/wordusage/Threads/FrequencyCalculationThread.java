package com.juliazozulia.wordusage.Threads;

import android.content.Context;
import android.database.Cursor;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.juliazozulia.wordusage.BasicWordFrequency.FrequencyLoadedEvent;
import com.juliazozulia.wordusage.Utils.Database;
import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.Utils.LMorphology;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 08.12.2015.
 */
public class FrequencyCalculationThread extends Thread {

    String TAG = this.getClass().getSimpleName();
    Context context;
    String keyUser;

    Frequency f = new Frequency();
    public FrequencyCalculationThread(Context context, String keyUser) {
        super();
        this.context = context;
        this.keyUser = keyUser;
    }

    @Override
    public void run() {
        Log.v(TAG, "run");
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        String divider = "[^[a-zA-Zа-яА-ЯёЁіІЇї]]";
        String replace = "(<.+>)|&quot;";
        Cursor c;
        int limit = 10000;
        int offset = 0;
        String[] args = {keyUser, Integer.toString(limit), Integer.toString(offset)};

        while (true) {

            args[2] = Integer.toString(offset);
            Log.v(TAG, "in while, offset = " + args[2]);

            c = Database.getDatabase().rawQuery("SELECT body_xml FROM Messages WHERE author = ? LIMIT ? OFFSET ?", args);
            while (c.moveToNext()) {

                if (isInterrupted()) {
                    return;
                }
                try {
                    String[] items = c.getString(0).replaceAll(replace, "").split(divider);
                    for (String item : items) {
                        if (!TextUtils.isEmpty(item)) {
                            f .addValue(getProperString(item));
                        }
                    }
                } catch (Exception e) {
                    Log.v("TAG", e.getMessage() + " some exception");
                }

            }
            if (c.getCount() < limit) {
                break;
            }
            offset += limit;

        }
        c.close();

        FrequencyHolder.getInstance().remove(keyUser);
        FrequencyHolder.getInstance().put(keyUser, f);
        Log.v(TAG, "added new f for " + keyUser);

        new WriteFrequencyToCacheThread(context, keyUser, f).start();

        EventBus.getDefault().post(new FrequencyLoadedEvent(f));
    }

    private String getProperString(String str) {

        str = str.toLowerCase();
        List<String> wordBaseForms;
        try {
            wordBaseForms = LMorphology.getRussianInstance().getNormalForms(str);
            return getOriginalStringIfExist(wordBaseForms, str);
        } catch (Exception e) {
            try {
                wordBaseForms = LMorphology.getEnglishInstance().getNormalForms(str);
                return getOriginalStringIfExist(wordBaseForms, str);
            } catch (Exception e1) {
                return str;
            }
        }

    }

    private String getOriginalStringIfExist(List<String> wordBaseForms, String word) {
        for (String str : wordBaseForms) {
            if (str.equals(word)) {
                return word;
            }
        }
        return wordBaseForms.get(0);
    }
}