package com.juliazozulia.wordusage.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.github.fabtransitionactivity.SheetLayout;
import com.juliazozulia.wordusage.Chart.ChartActivity;
import com.juliazozulia.wordusage.R;
import com.juliazozulia.wordusage.BasicWordFrequency.FrequencyActivity;
import com.juliazozulia.wordusage.Threads.LoadUsersThread;
import com.juliazozulia.wordusage.Utils.CacheUtils;


import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    ListView userListView;
    android.support.v7.widget.SearchView sv;
    String initialQuery;
    SheetLayout mSheetLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bindViews();
        setupViews();
        bindListeners();

        CacheUtils.ClearCacheIfNecessary(this);
        new LoadUsersThread().start();
        EventBus.getDefault().register(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mSheetLayout.contractFab();
    }


    @SuppressWarnings("unused")
    public void onEventMainThread(UsersLoadedEvent event) {
        userListView.setAdapter(new UserAdapter(this, event.getUsers()));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void bindViews() {
        userListView = (ListView) findViewById(R.id.userListView);
        mSheetLayout = (SheetLayout) findViewById(R.id.bottom_sheet);
        fab = (FloatingActionButton) findViewById(R.id.user_fab);
    }

    private void setupViews() {
        mSheetLayout.setFab(fab);
    }

    private void bindListeners() {
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), FrequencyActivity.class);
                UserAdapter adapter = (UserAdapter) userListView.getAdapter();
                intent.putExtra(FrequencyActivity.EXTRA_USER, adapter.getItem(position).userSkypeName);
                startActivity(intent);
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getBaseContext(), ChartActivity.class);
                //startActivity(intent);
                mSheetLayout.expandFab();
            }
        });

        mSheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
            @Override
            public void onFabAnimationEnd() {
                Intent intent = new Intent(getBaseContext(), ChartActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        configureSearchView(menu);
        return true;
    }


    private void configureSearchView(Menu menu) {
        MenuItem search = menu.findItem(R.id.search);
        sv = (SearchView) search.getActionView();
        sv.setOnQueryTextListener(this);
        sv.setOnCloseListener(this);
        sv.setSubmitButtonEnabled(false);
        sv.setIconifiedByDefault(true);

        if (initialQuery != null) {
            sv.setIconified(false);
            search.expandActionView();
            sv.setQuery(initialQuery, true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        ArrayAdapter<UserItem> adapter = (ArrayAdapter) userListView.getAdapter();
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
        } else {
            adapter.getFilter().filter(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onClose() {

        ArrayAdapter<UserItem> adapter = (ArrayAdapter) userListView.getAdapter();
        adapter.getFilter().filter("");
        return true;
    }


}
