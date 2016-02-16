package com.juliazozulia.wordusage.Events;

import com.juliazozulia.wordusage.Utils.Frequency;

/**
 * Created by Julia on 24.11.2015.
 */
public class FrequencyLoadedEvent {

    int userId;
    final Frequency frequency;

    public FrequencyLoadedEvent(int userID, Frequency f) {
        this.userId = userID;
        frequency = f;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public int getUserId() {
        return userId;
    }
}

