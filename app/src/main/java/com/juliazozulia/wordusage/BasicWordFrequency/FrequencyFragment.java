package com.juliazozulia.wordusage.BasicWordFrequency;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.juliazozulia.wordusage.Database;
import com.juliazozulia.wordusage.R;

import org.apache.commons.math3.stat.Frequency;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrequencyFragment extends Fragment {

    private static final String KEY_USER = "user";
    private Frequency f = new Frequency();
    private String key_user;
    ListView mListView;
    FrequencyThread frequencyThread;
    ProgressBar mProgressBar;
    private boolean isStarted = false;

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
        mListView = (ListView) result.findViewById(R.id.word_list);
        View header = inflater.inflate(R.layout.list_header, null);
        mListView.addHeaderView(header);
        mProgressBar = (ProgressBar) result.findViewById(R.id.progressBar1);
        if (f.getUniqueCount() == 0) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else  mProgressBar.setVisibility(View.GONE);
        return result;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {

        Log.v(this.toString(), "onViewCreated");
        super.onViewCreated(v, savedInstanceState);
        mListView.setScrollbarFadingEnabled(false);
        mListView.setAdapter(new FrequencyAdapter(getActivity(), f));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            key_user = getArguments().getString(KEY_USER);
        }
        if (!isStarted) {
            isStarted = true;
            frequencyThread = new FrequencyThread();
            frequencyThread.start();
        }
    }

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
        Log.v(this.toString(), "onDetach");
        EventBus.getDefault().unregister(this);
        //frequencyThread.interrupt();
        super.onDetach();
    }


    @SuppressWarnings("unused")
    public void onEventMainThread(FrequencyLoadedEvent event) {

        Log.v(this.toString(), "onEventMainThread");
        FrequencyAdapter mAdapter = new FrequencyAdapter(getActivity(), event.getWords());
        mAdapter.setNotifyOnChange(true);
        mListView.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(),String.format(getActivity().getResources().getString(R.string.size_template), event.getWords().getUniqueCount(), event.getWords().getTotalCount()),Toast.LENGTH_LONG).show();
    }

    private class FrequencyThread extends Thread {

        FrequencyThread() {
            super();
        }

        @Override
        public void run() {
            Log.v(this.toString(), "run");
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            String divider = "[^[a-zA-Zа-яА-ЯёЁіІЇї]]";
            // f = new Frequency(String.CASE_INSENSITIVE_ORDER);
            Cursor c;
            int limit = 10000;
            int offset = 0;
            String[] args = {key_user, Integer.toString(limit), Integer.toString(offset)};

            while (true) {

                args[2] = Integer.toString(offset);
                Log.v("FrequencyThread", "in while, offset = " + args[2]);

               // c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT body_xml FROM Messages WHERE author = ? LIMIT ? OFFSET ?", args);
                c = Database.getDatabase().rawQuery("SELECT body_xml FROM Messages WHERE author = ? LIMIT ? OFFSET ?", args);
                while (c.moveToNext()) {

                    if (isInterrupted()) {
                        return;
                    }
                    try {
                        String[] items = c.getString(0).split(divider);
                        for (String item : items) {
                            if (!TextUtils.isEmpty(item)) {
                                f.addValue(item.toLowerCase());
                                //EventBus.getDefault().post(new FrequencyLoadedEvent(f));
                                //  Log.v("FrequencyThread", "Added value " + item);
                            }
                        }
                    } catch (Exception e) {
                        Log.v("FrequencyThread", e.getMessage() + "Some Exception");
                    }

                }
                if (c.getCount() < limit) {
                    break;
                }
                offset += limit;
                //

            }
            c.close();

            EventBus.getDefault().post(new FrequencyLoadedEvent(f));
            Log.v("FrequencyThread", "after EventBus.getDefault().post");
        }
    }


}
