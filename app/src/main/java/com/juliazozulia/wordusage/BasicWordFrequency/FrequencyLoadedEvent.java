package com.juliazozulia.wordusage.BasicWordFrequency;

import org.apache.commons.math3.stat.Frequency;

/**
 * Created by Julia on 24.11.2015.
 */
public class FrequencyLoadedEvent {

    final Frequency words;

    public FrequencyLoadedEvent(Frequency w) {
        words = w;
    }

    public Frequency getWords() {
        return words;
    }
}

