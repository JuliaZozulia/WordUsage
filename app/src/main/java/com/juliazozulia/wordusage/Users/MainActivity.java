package com.juliazozulia.wordusage.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.juliazozulia.wordusage.LMorphology;
import com.juliazozulia.wordusage.R;
import com.juliazozulia.wordusage.BasicWordFrequency.FrequencyActivity;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {

    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userListView = (ListView) findViewById(R.id.userListView);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), FrequencyActivity.class);
                UserAdapter adapter = (UserAdapter) userListView.getAdapter();
                intent.putExtra(FrequencyActivity.EXTRA_USER, adapter.getItem(position).userSkypeName);
                startActivity(intent);
            }

        });
        new LoadUsersThread().start();

        EventBus.getDefault().register(this);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(UsersLoadedEvent event) {

        ArrayList<UserItem> users = event.getUsers();
        ArrayList<String> list = new ArrayList<>();
        userListView.setAdapter(new UserAdapter(this, event.getUsers()));


    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
