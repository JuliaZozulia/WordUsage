package com.juliazozulia.wordusage.Threads;

import android.content.Context;

import com.juliazozulia.wordusage.Utils.GsonUtils;
import com.juliazozulia.wordusage.Model.WordAndIds;

/**
 * Created by Julia on 25.12.2015.
 */
//unused
public class WriteWordAndIdsToCacheTread extends WriteObjToCacheTread {

    WordAndIds obj;

    public WriteWordAndIdsToCacheTread(Context context, int keyUser, WordAndIds obj) {
        super(context, keyUser);
        this.obj = obj;
        filename = filename + "_messages";
    }

    @Override
    public String getJsonObj() throws Exception {
        return GsonUtils.getInstance().toJson(obj);
    }
}
