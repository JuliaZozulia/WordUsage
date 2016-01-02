package com.juliazozulia.wordusage.UI.BasicWordFrequency;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.github.fabtransitionactivity.SheetLayout;
import com.juliazozulia.wordusage.UI.Messages.MessageActivity;
import com.juliazozulia.wordusage.UI.Messages.MessageAdapter;
import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.UI.PersonalChart.PersonalChartActivity;
import com.juliazozulia.wordusage.R;

import com.juliazozulia.wordusage.Utils.Frequency;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrequencyFragment extends Fragment implements
        SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private static final String TAG = FrequencyFragment.class.getSimpleName();
    private static final String KEY_USER = "user";
    private Frequency f = null;
    private String keyUser;

    ListView mListView;
    ProgressBar mProgressBar;
    SheetLayout mSheetLayout;
    FloatingActionButton fab;
    Snackbar mSnackbar;
    SearchView mSearchView;
    CharSequence initialQuery;


    public FrequencyFragment() {
    }

    public static FrequencyFragment newInstance(String user) {
        FrequencyFragment fragment = new FrequencyFragment();
        Bundle args = new Bundle();
        args.putString(KEY_USER, user);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_word_frequency, container, false);

        bindViews(result);
        setupViews();
        bindListeners();

        View header = inflater.inflate(R.layout.list_header, null);
        mListView.addHeaderView(header);


        if (f == null) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            actionDataExist();
        }

        //setHasOptionsMenu(true);
        return result;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setRetainInstance(true);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            keyUser = getArguments().getString(KEY_USER);
            f = FrequencyHolder.getFrequencyForce(getActivity(), keyUser);
        }
    }

   /* @Override
    public void onResume() {
        super.onResume();

        if (mSnackbar != null) {
            mSnackbar.dismiss();
            mSheetLayout.contractFab();
            mSnackbar.show();
        }
    }*/

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        if (mSearchView != null) {
            if (!mSearchView.isIconified()) {
                initialQuery = mSearchView.getQuery();
            }
        }
    }


  /*  @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
        } else {
            initialQuery = savedInstanceState.getCharSequence(STATE_QUERY);
        }
    }*/


    @Override
    public void onAttach(Activity host) {
        super.onAttach(host);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Context host) {
        super.onAttach(host);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        Log.v(TAG, "onDetach");
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }


    @SuppressWarnings("unused")
    public void onEventMainThread(FrequencyLoadedEvent event) {

        /**
         * TODO maybe remove FrequencyLoadedEvent event, switch to keyUser
         */
        Log.v(TAG, "onEventMainThread");
        f = event.getFrequency();
        getActivity().invalidateOptionsMenu();
        actionDataExist();
    }

    private void bindViews(View result) {
        mListView = (ListView) result.findViewById(R.id.word_list);
        mSheetLayout = (SheetLayout) result.findViewById(R.id.frequency_bottom_sheet);
        mProgressBar = (ProgressBar) result.findViewById(R.id.progressBar1);
        fab = (FloatingActionButton) result.findViewById(R.id.frequency_fab);
    }

    private void setupViews() {
        mSheetLayout.setFab(fab);
        mListView.setFastScrollEnabled(true);
        mListView.setScrollbarFadingEnabled(false);
    }

    private void bindListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalChartActivity.class);
                intent.putExtra(PersonalChartActivity.EXTRA_USER, keyUser);
                startActivity(intent);
                //mSheetLayout.expandFab();
            }
        });

        mSheetLayout.setFabAnimationEndListener(new SheetLayout.OnFabAnimationEndListener() {
            @Override
            public void onFabAnimationEnd() {
                Intent intent = new Intent(getActivity(), PersonalChartActivity.class);
                intent.putExtra(PersonalChartActivity.EXTRA_USER, keyUser);
                startActivity(intent);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                intent.putExtra(MessageActivity.EXTRA_USER, keyUser);

                FrequencyAdapter adapter = ((FrequencyAdapter) ((HeaderViewListAdapter) mListView.getAdapter()).getWrappedAdapter());
                intent.putExtra(MessageActivity.EXTRA_WORD, adapter.getItem(position - 1)); //is it because of header? find out and do it properly
                startActivity(intent);
            }
        });
    }

    private void actionDataExist() {

        fab.setVisibility(View.VISIBLE);
        mListView.setAdapter(new FrequencyAdapter(getActivity(), f));
        mProgressBar.setVisibility(View.GONE);
        //Frequency f = FrequencyHolder.getInstance().get(keyUser);
        mSnackbar = Snackbar.make(fab,
                String.format(
                        getActivity().getResources().getString(R.string.size_template),
                        f.getUniqueCount(), f.getTotalCount()),
                Snackbar.LENGTH_INDEFINITE
        );
        mSnackbar.setAction(R.string.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "fab action");
            }
        });
        mSnackbar.show();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.v(TAG, "onCreateOptionsMenu");
        inflater.inflate(R.menu.menu_main, menu);

        configureSearchView(menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void configureSearchView(Menu menu) {

        MenuItem search = menu.findItem(R.id.search);

        if (f == null) {
            search.setVisible(false);
            Log.v(TAG, "onCreateOptionsMenu set INVISIBLE");

        } else {
            search.setVisible(true);
            Log.v(TAG, "onCreateOptionsMenu set VISIBLE");
            mSearchView = (SearchView) search.getActionView();
            mSearchView.setOnQueryTextListener(this);
            mSearchView.setOnCloseListener(this);
            mSearchView.setSubmitButtonEnabled(false);
            mSearchView.setIconifiedByDefault(true);

            if (initialQuery != null) {
                mSearchView.setIconified(false);
                search.expandActionView();
                mSearchView.setQuery(initialQuery, true);
                initialQuery = null;
            }
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        ArrayAdapter<String> adapter = ((FrequencyAdapter) ((HeaderViewListAdapter) mListView.getAdapter()).getWrappedAdapter());
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
        } else {
            adapter.getFilter().filter(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onClose() {
        ArrayAdapter<String> adapter = (ArrayAdapter) mListView.getAdapter();
        adapter.getFilter().filter("");
        return true;
    }

}