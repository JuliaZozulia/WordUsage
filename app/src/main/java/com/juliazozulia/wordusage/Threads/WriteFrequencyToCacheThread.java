package com.juliazozulia.wordusage.Threads;

import android.content.Context;

import com.juliazozulia.wordusage.Utils.Frequency;
import com.juliazozulia.wordusage.Utils.GsonUtils;

/**
 * Created by Julia on 25.12.2015.
 */
public class WriteFrequencyToCacheThread extends WriteObjToCacheTread {

    Frequency frequency;

    public WriteFrequencyToCacheThread(Context context, String keyUser, Frequency frequency) {
        super(context, keyUser);
        this.frequency = frequency;
    }

    @Override
    public String getJsonObj() throws Exception {
        return GsonUtils.getInstance().toJson(frequency);
    }
}
