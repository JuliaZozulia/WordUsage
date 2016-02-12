package com.juliazozulia.wordusage.Threads;

import android.content.Context;
import android.database.Cursor;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.juliazozulia.wordusage.UI.BasicWordFrequency.FrequencyLoadedEvent;
import com.juliazozulia.wordusage.Database.MessageDatabaseHelper;
import com.juliazozulia.wordusage.Database.SkypeDatabase;
import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.Utils.LMorphology;
import com.juliazozulia.wordusage.Utils.WordAndIds;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 08.12.2015.
 */
public class  FrequencyCalculationThread extends Thread {

    String TAG = this.getClass().getSimpleName();
    Context context;
    int keyUser;

    Frequency f = new Frequency();
    public FrequencyCalculationThread(Context context, int keyUser) {
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
      //  WordAndIds wordAndIds = new WordAndIds();
        int limit = 1000;
        int offset = 0;
        String[] args = {Integer.toString(keyUser), Integer.toString(limit), Integer.toString(offset)};
        String[] items;

        while (true) {

            args[2] = Integer.toString(offset);
            Log.v(TAG, "in while, offset = " + args[2]);
            WordAndIds wordAndIds = new WordAndIds();

            c = SkypeDatabase.getDatabase().rawQuery("SELECT body_xml, id FROM Messages WHERE author IN (SELECT skypename FROM Contacts WHERE id  = ? ) LIMIT ? OFFSET ?", args);
            while (c.moveToNext()) {

                if (isInterrupted()) {
                    return;
                }
                try {
                    items = c.getString(0).replaceAll(replace, "").split(divider);
                    for (String item : items) {
                        if (!TextUtils.isEmpty(item)) {
                            String str = getProperString(item);
                            f .addValue(str);
                            wordAndIds.addValue(str, c.getInt(1));
                           // String[] argsID = {keyUser, str,  Integer.toString(c.getInt(1))};
                           // MessageDatabaseHelper.getInstance(context).getWritableDatabase().execSQL("INSERT OR REPLACE INTO messages (skypename, word, messages_id) VALUES( ?, ?, ?)",
                           //         argsID);
                        }
                    }
                } catch (Exception e) {
                    Log.v("TAG", e.getMessage() + " some exception");
                }

            }

            MessageDatabaseHelper.getInstance(context).writeMessagesId(keyUser,wordAndIds);
            if (c.getCount() < limit) {
                break;
            }
            offset += limit;


        }
        c.close();

        FrequencyHolder.getInstance().put(keyUser, f);
        Log.v(TAG, "added new f for " + keyUser);

        new WriteFrequencyToCacheThread(context, keyUser, f).start();
      //  MessageDatabaseHelper.getInstance(context).writeMessagesId(keyUser,wordAndIds);

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