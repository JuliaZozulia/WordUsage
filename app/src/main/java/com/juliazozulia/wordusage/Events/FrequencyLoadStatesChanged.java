package com.juliazozulia.wordusage.Events;

/**
 * Created by Julia on 14.02.2016.
 */
public class FrequencyLoadStatesChanged {

    int userId;
    final LoadState state;
    float percent;

    public FrequencyLoadStatesChanged(LoadState s) {
        state = s;
    }
    public FrequencyLoadStatesChanged(int userId, LoadState s) {
        this.userId = userId;
        state = s;
    }

    public FrequencyLoadStatesChanged(int userId, LoadState s, float percent) {
        this.userId = userId;
        state = s;
        this.percent = percent;
    }

    public float getPercent() {
        return percent;
    }

    public LoadState getState() {
        return state;
    }

    public int getUserId() {
        return userId;
    }

    public enum LoadState {START_CALCULATION, START_LUCENE, FINISH_LUCENE, START_LOADING}
}
