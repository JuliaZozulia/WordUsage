package com.juliazozulia.wordusage.Chart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChartFragment demo = (ChartFragment) getFragmentManager().findFragmentById(android.R.id.content);
        if (demo == null) {
            demo = new ChartFragment();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, demo).commit();
        }
    }

}

