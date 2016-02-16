package com.juliazozulia.wordusage.Threads;

import android.content.Context;
import android.database.Cursor;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.juliazozulia.wordusage.Events.FrequencyLoadStatesChanged;
import com.juliazozulia.wordusage.UI.BasicWordFrequency.FrequencyLoadedEvent;
import com.juliazozulia.wordusage.Database.MessageDatabaseHelper;
import com.juliazozulia.wordusage.Database.SkypeDatabase;
import com.juliazozulia.wordusage.UI.Users.UserItem;
import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.Utils.LMorphology;
import com.juliazozulia.wordusage.Utils.WordAndIds;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 08.12.2015.
 */
public class FrequencyCalculationThread extends Thread {

    String TAG = this.getClass().getSimpleName();
    Context context;
    UserItem keyUser;

    Frequency f = new Frequency();

    public FrequencyCalculationThread(Context context, UserItem keyUser) {
        super();
        this.context = context;
        this.keyUser = keyUser;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Log.v(TAG, "run");
        EventBus.getDefault().post(new FrequencyLoadStatesChanged(keyUser.getUserID(),
                FrequencyLoadStatesChanged.LoadState.START_CALCULATION, 0));
        String divider = "[^[a-zA-Zа-яА-ЯёЁіІЇї]]";
        String replace = "(<.+>)|&quot;";
        Cursor c;
        //  WordAndIds wordAndIds = new WordAndIds();
        int limit = 1000;
        int offset = 0;
        String[] args = {keyUser.getUserSkypeName(), Integer.toString(limit), Integer.toString(offset)};
        String[] items;
        int totalCount = getTotalCount();

        while (true) {

            args[2] = Integer.toString(offset);
            Log.v(TAG, "in while, offset = " + args[2]);
            WordAndIds wordAndIds = new WordAndIds();

            c = SkypeDatabase.getDatabase().rawQuery("SELECT body_xml, id FROM Messages WHERE author = ? " +
                    " LIMIT ? OFFSET ?", args);
            while (c.moveToNext()) {

                if (isInterrupted()) {
                    return;
                }
                try {
                    items = c.getString(0).replaceAll(replace, "").split(divider);
                    for (String item : items) {
                        if (!TextUtils.isEmpty(item)) {
                            String str = getProperString(item);
                            f.addValue(str);
                            wordAndIds.addValue(str, c.getInt(1));
                            // MessageDatabaseHelper.getInstance(context).getWritableDatabase().execSQL("INSERT OR REPLACE INTO messages (skypename, word, messages_id) VALUES( ?, ?, ?)",
                        }
                    }
                } catch (Exception e) {
                    Log.v("TAG", e.getMessage() + " some exception");
                }

            }

            MessageDatabaseHelper.getInstance(context).writeMessagesId(keyUser.getUserID(), wordAndIds);

            Double p = 100.0 * ((double) (offset + limit) / totalCount);
            int percent = p.intValue();
            EventBus.getDefault().post(new FrequencyLoadStatesChanged(keyUser.getUserID(),
                    FrequencyLoadStatesChanged.LoadState.START_CALCULATION, percent));

            if (c.getCount() < limit) {
                break;
            }
            offset += limit;


        }
        c.close();

        FrequencyHolder.getInstance().put(keyUser.getUserID(), f);
        Log.v(TAG, "added new f for " + keyUser);

        new WriteFrequencyToCacheThread(context, keyUser.getUserID(), f).start();
        //  MessageDatabaseHelper.getInstance(context).writeMessagesId(keyUser,wordAndIds);

        EventBus.getDefault().post(new FrequencyLoadedEvent(f));
    }

    private int getTotalCount() {

        String[] args = {keyUser.getUserSkypeName()};
        Cursor cursor = SkypeDatabase.getDatabase().rawQuery("SELECT COUNT (*)  FROM Messages WHERE author = ? ", args);
        int count = 0;
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        cursor.close();
        return count;
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