package com.juliazozulia.wordusage.UI.Messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.juliazozulia.wordusage.R;
import com.juliazozulia.wordusage.Database.MessageDatabaseHelper;

import de.greenrobot.event.EventBus;

public class MessageActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "user";
    public static final String EXTRA_USER_NAME = "name";
    public static final String EXTRA_WORD = "word";
    ListView mUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        bindViews();
        EventBus.getDefault().register(this);
        MessageDatabaseHelper.getInstance(this).loadMessages(getIntent().getIntExtra(EXTRA_USER, 0), getIntent().getStringExtra(EXTRA_WORD));
        setTitle((getIntent().getStringExtra(EXTRA_USER_NAME) + ": " + getIntent().getStringExtra(EXTRA_WORD)));
/*        setupViews();
        bindListeners();*/


    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(MessagesLoadedEvent event) {

        mUserListView.setAdapter(new MessageAdapter(this, event.getItems()));
    }

    private void bindViews() {
        mUserListView = (ListView) findViewById(R.id.messageListView);
    }
}
