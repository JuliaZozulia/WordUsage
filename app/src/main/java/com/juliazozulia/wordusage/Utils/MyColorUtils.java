package com.juliazozulia.wordusage.Utils;

import android.content.Context;
import android.graphics.Color;

import com.juliazozulia.wordusage.R;

import lecho.lib.hellocharts.util.ChartUtils;

/**
 * Created by Julia on 04.12.2015.
 */
public abstract class MyColorUtils {
    private static int COLOR_INDEX = 0;


    public static final int nextColor(Context context) {
        int[] colors = context.getResources().getIntArray(R.array.material_colors);
        if (COLOR_INDEX >= colors.length) {
            COLOR_INDEX = 0;
        }
        return colors[COLOR_INDEX++];
    }

    public static final int pickColor(Context context) {
        int[] colors = context.getResources().getIntArray(R.array.material_colors);
        return colors[(int) Math.round(Math.random() * (colors.length - 1))];
    }
}
