package com.juliazozulia.wordusage.UI.PersonalChart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import com.juliazozulia.wordusage.UI.Chart.ChartActivity;
import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.R;
import com.juliazozulia.wordusage.Utils.MyColorUtils;
import com.juliazozulia.wordusage.Utils.PieChartRenderer.SelectedUser;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;

public class PersonalChartActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = PersonalChartActivity.class.getSimpleName();
    PieChartView mChart;
    PieChartData data;
    AppCompatSeekBar mSeekBar;
    int seekBarMaxValue = 50;
    int seekBarDefaultValue = 10;
    String[] users;
    Frequency f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chart);
        this.setTitle(SelectedUser.getInstance().getUserFullName());

        mChart = (PieChartView) findViewById(R.id.pie_chart);
        mChart.setOnValueTouchListener(new ValueTouchListener());

        mSeekBar = (AppCompatSeekBar) findViewById(R.id.chart_seek_bar);
        mSeekBar.setOnSeekBarChangeListener(this);

        f = FrequencyHolder.getInstance().get(SelectedUser.getInstance().getUserID());
        mSeekBar.setMax(Math.min(f.getUniqueCount(), seekBarMaxValue));
        users = f.getItems();

        mSeekBar.setProgress(Math.min(f.getUniqueCount(), seekBarDefaultValue));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_personal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ChartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void generateData(int numValues) {

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) f.getCount(users[i]), MyColorUtils.pickColor(this));
            values.add(sliceValue.setLabel(users[i]));
        }

        data = new PieChartData(values);
        data.setHasLabels(true);
        data.setHasCenterCircle(true);
        mChart.setPieChartData(data);
        Log.v(TAG, "Count = " + values.size());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // progress += 5;
        generateData(progress);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //do nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //do nothing
    }


    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            //   Toast.makeText(getBaseContext(), "Selected: " + value., Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {

        }

    }

}
