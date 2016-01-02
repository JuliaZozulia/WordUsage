package com.juliazozulia.wordusage.UI.Chart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juliazozulia.wordusage.Utils.FrequencyHolder;
import com.juliazozulia.wordusage.R;

import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.MyColorUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;

/**
 * Created by Julia on 03.12.2015.
 */
public class ChartFragment extends Fragment {
    /**
     * A fragment containing a line chart and preview line chart.
     */

    boolean isInPrv = false;
    private LineChartView chart;
    private PreviewLineChartView previewChart;
    private LineChartData data;

    ViewportChangeListener previewListener;
    ViewportChangeListener listener;

    Boolean isAbsoluteValue = true;
    /**
     * Deep copy of data.
     */
    private LineChartData previewData;

    public ChartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);


        final FloatingActionButton mFab = (FloatingActionButton) rootView.findViewById(R.id.chart_fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAbsoluteValue = !isAbsoluteValue;

                generateData();
                removeListeners();
                setData();
                setListeners();
                changeFabIcon(mFab);
            }
        });


        chart = (LineChartView) rootView.findViewById(R.id.chart);
        previewChart = (PreviewLineChartView) rootView.findViewById(R.id.chart_preview);

        previewListener = (ViewportChangeListener) new ViewportListener();
        listener = (ViewportChangeListener) new ViewportListenerPreview();

        changeFabIcon(mFab);

        // Generate data for previewed chart and copy of that data for preview chart.
        generateData();
        setData();

        setListeners();

        chart.setZoomEnabled(true);
        chart.setScrollEnabled(true);
        chart.setZoomType(ZoomType.HORIZONTAL);
        previewChart.setZoomType(ZoomType.HORIZONTAL);

        return rootView;
    }

    private void changeFabIcon(FloatingActionButton mFab) {
        if (isAbsoluteValue) {
            mFab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_percent_white_24dp));
        } else {
            mFab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_numeric_white_24dp));
        }
    }

    private void removeListeners() {
        chart.setViewportChangeListener(null);
        previewChart.setViewportChangeListener(null);
    }

    private void setListeners() {
        chart.setViewportChangeListener(listener);
        previewChart.setViewportChangeListener(previewListener);
    }

    private void setData() {
        chart.setLineChartData(data);
        previewChart.setLineChartData(previewData);
        moveToStartPoint(false);
    }

    private void generateData() {

        Frequency f;
        List<Line> lines = new ArrayList<Line>();
        List<Line> linesPrev = new ArrayList<Line>();

        String[] names = FrequencyHolder.getNames();
        for (int i = 0; i < FrequencyHolder.getCount(); i++) {

            f = FrequencyHolder.getInstance().get(names[i]);
            int numValues = Math.min(f.getUniqueCount(), 100);
            String[] s = f.getItems();


            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numValues; ++j) {
                if (isAbsoluteValue) {
                    values.add(new PointValue(j, (float) f.getCount(s[j])));
                } else {
                    values.add(new PointValue(j, (float) f.getPct(s[j]) * 100));
                }
                values.get(j).setLabel(s[j]);
            }

            Line line = new Line(values);
            line.setColor(MyColorUtils.pickColor(getActivity()));
            line.setHasPoints(true);
            line.setHasLabels(true);
            lines.add(line);

            Line line1 = new Line(values);
            line1.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            line1.setHasPoints(false);
            line1.setHasLabels(false);

            linesPrev.add(line1);
        }

        data = new LineChartData(lines);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(true));


        // prepare preview data, is better to use separate deep copy for preview chart.
        // Set color to grey to make preview area more visible.

        LineChartData dataPrev;
        dataPrev = new LineChartData(linesPrev);
        dataPrev.setAxisXBottom(new Axis());
        dataPrev.setAxisYLeft(new Axis().setHasLines(true));

        previewData = new LineChartData(dataPrev);

    }


    private void moveToStartPoint(boolean animate) {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dx = tempViewport.width() / 10;
        tempViewport.left = 0;
        tempViewport.right = dx;

        //  Viewport tempViewport = new Viewport(0)
        if (animate) {
            previewChart.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChart.setCurrentViewport(tempViewport);
        }
    }

    private void previewXY() {
        // Better to not modify viewport of any chart directly so create a copy.
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        // Make temp viewport smaller.
        float dx = tempViewport.width() / 4;
        float dy = tempViewport.height() / 4;
        tempViewport.inset(dx, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
    }

    /**
     * Viewport listener for preview chart(lower one). in {@link #onViewportChanged(Viewport)} method change
     * viewport of upper chart.
     */
    private class ViewportListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport newViewport) {
            if (!newViewport.equals(chart.getCurrentViewport())) {
                if (!isInPrv) {
                    chart.setCurrentViewport(newViewport);
                }
                isInPrv = false;
            }
        }

    }

    private class ViewportListenerPreview implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport newViewport) {

            if (!newViewport.equals(previewChart.getCurrentViewport())) {
                isInPrv = true;
                previewChart.setCurrentViewport(newViewport);
            }
        }

    }

}
