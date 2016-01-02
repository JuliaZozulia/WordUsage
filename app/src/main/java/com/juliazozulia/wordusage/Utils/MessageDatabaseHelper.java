package com.juliazozulia.wordusage.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Process;

import com.juliazozulia.wordusage.UI.Messages.MessageItem;
import com.juliazozulia.wordusage.UI.Messages.MessagesLoadedEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 22.12.2015.
 */
public class MessageDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SkypeMessage.db";
    private static final int SCHEMA_VERSION = 1;
    private static MessageDatabaseHelper singleton = null;

    public synchronized static MessageDatabaseHelper getInstance(Context context) {

        if (singleton == null) {
            singleton = new MessageDatabaseHelper(context.getApplicationContext());
        }
        return (singleton);
    }

    private MessageDatabaseHelper(Context ctxt) {

        super(ctxt, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //  db.execSQL("CREATE TABLE messages (_id INTEGER PRIMARY KEY, skypename TEXT, word TEXT, messages_ids TEXT);");
        db.execSQL("CREATE TABLE messages (_id INTEGER PRIMARY KEY, skypename TEXT, word TEXT, message_id INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        throw new RuntimeException("This should not be called");
    }

    public void loadMessages(String user, String word) {
        new LoadMessageThread(user, word).start();
    }

    public void writeMessagesId(String user, WordAndIds wordAndIds) {
        new UpdateThread(user, wordAndIds).start();
    }


    public class LoadMessageThread extends Thread {
        private String user;
        private String word;

        LoadMessageThread(String user, String word) {
            super();
            this.user = user;
            this.word = word;
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            int limit = 200;
            int offset = 0;
            Cursor c, c1;
            //loading ids
            String[] args = {user, word, Integer.toString(limit), Integer.toString(offset)};
            // String[] ids;
            int id;
            ArrayList<MessageItem> messages = new ArrayList<>();

            while (true) {
                args[3] = Integer.toString(offset);
                c = getReadableDatabase().rawQuery("SELECT message_id FROM messages WHERE skypename = ? AND  word = ? LIMIT ? OFFSET ?", args);
            /*if (c.getCount() > 0) {

                c.moveToFirst();*/
                while (c.moveToNext()) {
                    // ids = c.getString(0).split(" ");


                    //loading messages by ids


                    //   for (String id : ids) {
                    id = c.getInt(0);

                    c1 =
                            SkypeDatabase.getDatabase().rawQuery("SELECT body_xml, timestamp FROM Messages WHERE id = ?", new String[]{Integer.toString(id)});
                    if (c1.getCount() > 0) {
                        c1.moveToFirst();
                        messages.add(new MessageItem(c1.getString(0), new Date(c1.getInt(1)*1000L)));
                    }
                    c1.close();
                    //  }
                    //  EventBus.getDefault().post(new MessagesLoadedEvent(messages));
                }
                if (c.getCount() < limit) {
                    break;
                }
                offset += limit;
            }

            c.close();
            //   c1.close();
            EventBus.getDefault().post(new MessagesLoadedEvent(messages));

        }
    }

    private class UpdateThread extends Thread {

        private String user;
        WordAndIds wordAndIds;

        UpdateThread(String user, WordAndIds wordAndIds) {
            super();
            this.user = user;
            this.wordAndIds = wordAndIds;

        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            String[] args = {user, null, null};
            Iterator<String> wordIterator = wordAndIds.getWordIterator();
            Iterator<Integer> idsIterator;
            while (wordIterator.hasNext()) {
                args[1] = wordIterator.next();
                idsIterator = wordAndIds.getIdsIterator(args[1]);
                while (idsIterator.hasNext()) {
                    args[2] = Integer.toString(idsIterator.next());
                    getWritableDatabase().execSQL("INSERT OR REPLACE INTO messages (skypename, word, message_id) VALUES( ?, ?, ?)",
                            args);
                }
            }

        }
    }
}


