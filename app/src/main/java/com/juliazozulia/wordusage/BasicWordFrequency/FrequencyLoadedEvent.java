package com.juliazozulia.wordusage.BasicWordFrequency;

import com.juliazozulia.wordusage.Utils.Frequency;

/**
 * Created by Julia on 24.11.2015.
 */
public class FrequencyLoadedEvent {

    final Frequency frequency;

    public FrequencyLoadedEvent(Frequency f) {
        frequency = f;
    }

    public Frequency getFrequency() {
        return frequency;
    }
}

