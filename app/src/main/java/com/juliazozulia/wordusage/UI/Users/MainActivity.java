package com.juliazozulia.wordusage.UI.Users;

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
import com.juliazozulia.wordusage.UI.Chart.ChartActivity;
import com.juliazozulia.wordusage.R;
import com.juliazozulia.wordusage.UI.BasicWordFrequency.FrequencyActivity;
import com.juliazozulia.wordusage.Threads.LoadUsersThread;
import com.juliazozulia.wordusage.Utils.CacheUtils;
import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.Utils.PieChartRenderer.SelectedUser;
import com.juliazozulia.wordusage.WordUsageApplication;


import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView mUserListView;
    //private RecyclerView mRecyclerView;
    private android.support.v7.widget.SearchView mSearchView;
    private SheetLayout mSheetLayout;
    private FloatingActionButton fab;

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

        //mRecyclerView.setAdapter(new UserAdapterRecycler(this, event.getUsers()));
        mUserListView.setAdapter(new UserAdapter(this, event.getUsers()));
        invalidateOptionsMenu();
       // FrequencyHolder.getFrequencyForce(this, event.getUsers().get(0));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void bindViews() {
        mUserListView = (ListView) findViewById(R.id.userListView);

        // mRecyclerView = (RecyclerView) findViewById(R.id.user_recycler_view);
        mSheetLayout = (SheetLayout) findViewById(R.id.bottom_sheet);
        fab = (FloatingActionButton) findViewById(R.id.user_fab);
    }

    private void setupViews() {
        mSheetLayout.setFab(fab);
        // mRecyclerView.setHasFixedSize(true);
        // mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindListeners() {
        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), FrequencyActivity.class);
                UserAdapter adapter = (UserAdapter) mUserListView.getAdapter();
                SelectedUser.getInstance().setUser(adapter.getItem(position));

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

        if (mUserListView.getAdapter() == null) {
            //  if (mRecyclerView.getAdapter() == null) {
            search.setVisible(false);
        } else {
            search.setVisible(true);
            mSearchView = (SearchView) search.getActionView();
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setOnCloseListener(this);
            mSearchView.setSubmitButtonEnabled(false);
            mSearchView.setIconifiedByDefault(true);
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

        ArrayAdapter<UserItem> adapter = (ArrayAdapter) mUserListView.getAdapter();
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
        } else {
            adapter.getFilter().filter(newText);
        }
        return true;
    }

    @Override
    public boolean onClose() {

        ArrayAdapter<UserItem> adapter = (ArrayAdapter) mUserListView.getAdapter();
        adapter.getFilter().filter("");
        return true;
    }


}
