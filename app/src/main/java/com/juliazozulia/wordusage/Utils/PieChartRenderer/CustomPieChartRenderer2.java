/*
package com.juliazozulia.wordusage.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

import lecho.lib.hellocharts.formatter.PieChartValueFormatter;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.provider.PieChartDataProvider;
import lecho.lib.hellocharts.renderer.PieChartRenderer;
import lecho.lib.hellocharts.view.Chart;

*/
/**
 * Created by Julia on 13.12.2015.
 *//*

public class CustomPieChartRenderer extends PieChartRenderer {
    PieChartDataProvider dataProvider;
    private PieChartValueFormatter valueFormatter;

    public CustomPieChartRenderer(Context context, Chart chart, PieChartDataProvider dataProvider) {
        super(context, chart, dataProvider);
        this.dataProvider = dataProvider;
    }

    @Override
    public void drawLabels(Canvas canvas) {
        final PieChartData data = dataProvider.getPieChartData();
        final float sliceScale = 360f / calculateMaxViewport();
        float lastAngle = getChartRotation();
        int sliceIndex = 0;
        for (SliceValue sliceValue : data.getValues()) {
            final float angle = Math.abs(sliceValue.getValue()) * sliceScale;

            drawLabel(canvas, sliceValue, lastAngle, angle);
            lastAngle += angle;
            ++sliceIndex;
        }
    }

    private float calculateMaxViewport() {

        float maxSum = 0.0f;
        for (SliceValue sliceValue : dataProvider.getPieChartData().getValues()) {
            maxSum += Math.abs(sliceValue.getValue());
        }
        return maxSum;
    }

    private void drawLabel(Canvas canvas, SliceValue sliceValue, float lastAngle, float angle) {

        PointF sliceVector = new PointF((float) (Math.cos(Math.toRadians(lastAngle + angle / 2))),
                (float) (Math.sin(Math.toRadians(lastAngle + angle / 2))));
        normalizeVector(sliceVector);

        final int numChars = valueFormatter.formatChartValue(labelBuffer, sliceValue);

        if (numChars == 0) {
            // No need to draw empty label
            return;
        }

        final float labelWidth = labelPaint.measureText(labelBuffer, labelBuffer.length - numChars, numChars);
        final int labelHeight = Math.abs(fontMetrics.ascent);

        final float centerX = originCircleOval.centerX();
        final float centerY = originCircleOval.centerY();
        final float circleRadius = originCircleOval.width() / 2f;
        final float labelRadius;

        */
/*if (hasLabelsOutside) {
            labelRadius = circleRadius * DEFAULT_LABEL_OUTSIDE_RADIUS_FACTOR;
        } else {
            if (hasCenterCircle) {*//*

                labelRadius = circleRadius - (circleRadius - (circleRadius * centerCircleScale)) / 2;
           */
/* } else {
                labelRadius = circleRadius * DEFAULT_LABEL_INSIDE_RADIUS_FACTOR;
            }
        }*//*


        final float rawX = labelRadius * sliceVector.x + centerX;
        final float rawY = labelRadius * sliceVector.y + centerY;

        float left;
        float right;
        float top;
        float bottom;

        if (hasLabelsOutside) {
            if (rawX > centerX) {
                // Right half.
                left = rawX + labelMargin;
                right = rawX + labelWidth + labelMargin * 3;
            } else {
                left = rawX - labelWidth - labelMargin * 3;
                right = rawX - labelMargin;
            }

            if (rawY > centerY) {
                // Lower half.
                top = rawY + labelMargin;
                bottom = rawY + labelHeight + labelMargin * 3;
            } else {
                top = rawY - labelHeight - labelMargin * 3;
                bottom = rawY - labelMargin;
            }
        } else {
            left = rawX - labelWidth / 2 - labelMargin;
            right = rawX + labelWidth / 2 + labelMargin;
            top = rawY - labelHeight / 2 - labelMargin;
            bottom = rawY + labelHeight / 2 + labelMargin;
        }

        labelBackgroundRect.set(left, top, right, bottom);
        drawLabelTextAndBackground(canvas, labelBuffer, labelBuffer.length - numChars, numChars,
                sliceValue.getDarkenColor());
    }

    private void normalizeVector(PointF point) {
        final float abs = point.length();
        point.set(point.x / abs, point.y / abs);
    }


    @Override
    public void onChartDataChanged()
    {
        super.onChartDataChanged();
        valueFormatter = dataProvider.getPieChartData().getFormatter();


    }

    */
/**
     * Calculates rectangle(square) that will constraint chart circle.
     *//*

    private void calculateCircleOval() {
        Rect contentRect = computator.getContentRectMinusAllMargins();
        final float circleRadius = Math.min(contentRect.width() / 2f, contentRect.height() / 2f);
        final float centerX = contentRect.centerX();
        final float centerY = contentRect.centerY();
        final float left = centerX - circleRadius + touchAdditional;
        final float top = centerY - circleRadius + touchAdditional;
        final float right = centerX + circleRadius - touchAdditional;
        final float bottom = centerY + circleRadius - touchAdditional;
        originCircleOval.set(left, top, right, bottom);
        final float inest = 0.5f * originCircleOval.width() * (1.0f - circleFillRatio);
        originCircleOval.inset(inest, inest);
    }


}
*/
