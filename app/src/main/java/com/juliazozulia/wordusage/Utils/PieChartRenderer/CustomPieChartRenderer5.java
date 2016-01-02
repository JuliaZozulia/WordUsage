/*
package com.juliazozulia.wordusage.Utils;

import android.content.Context;
import android.graphics.Canvas;

import lecho.lib.hellocharts.provider.PieChartDataProvider;
import lecho.lib.hellocharts.renderer.PieChartRenderer;
import lecho.lib.hellocharts.view.Chart;

*/
/**
 * Created by Julia on 14.12.2015.
 *//*

public class CustomPieChartRenderer5 extends PieChartRenderer {

    public CustomPieChartRenderer5(Context context, Chart chart, PieChartDataProvider dataProvider) {
        super(context, chart, dataProvider);
    }

    */
/**
     * Draws label text and label background if isValueLabelBackgroundEnabled is true.
     *//*

    @Override
    protected void drawLabelTextAndBackground(Canvas canvas, char[] labelBuffer, int startIndex, int numChars,
                                              int autoBackgroundColor) {
        final float textX;
        final float textY;

        canvas.save();
        canvas.rotate(-90);

        if (isValueLabelBackgroundEnabled) {

            if (isValueLabelBackgroundAuto) {
                labelBackgroundPaint.setColor(autoBackgroundColor);
            }

            canvas.drawRect(labelBackgroundRect, labelBackgroundPaint);

            textX = labelBackgroundRect.left + labelMargin;
            textY = labelBackgroundRect.bottom - labelMargin;
        } else {
            textX = labelBackgroundRect.left;
            textY = labelBackgroundRect.bottom;
        }

        canvas.drawText(labelBuffer, startIndex, numChars, textX, textY, labelPaint);


        canvas.restore();

    }


}
*/
