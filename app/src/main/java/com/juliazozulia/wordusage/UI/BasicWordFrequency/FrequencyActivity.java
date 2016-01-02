package com.juliazozulia.wordusage.UI.BasicWordFrequency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FrequencyActivity extends AppCompatActivity {

    private static final String MODEL_TAG = "model";
    private FrequencyFragment mFrag = null;

    public static final  String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(getIntent().getStringExtra(EXTRA_USER));


        /*mFrag = (FrequencyFragment) getFragmentManager().findFragmentByTag(MODEL_TAG);
        if (mFrag == null) {
            mFrag = new FrequencyFragment();
            getFragmentManager().beginTransaction().add(mFrag, MODEL_TAG)
                    .commit();
        }*/
        FrequencyFragment demo = (FrequencyFragment) getFragmentManager().findFragmentById(android.R.id.content);
        if (demo == null) {
            demo = FrequencyFragment.newInstance(getIntent().getStringExtra(EXTRA_USER));
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, demo).commit();
        }
       // demo.setModel(mFrag.getModel());
    }


}
