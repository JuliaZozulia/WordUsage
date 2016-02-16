package com.juliazozulia.wordusage.Model;

/**
 * Created by Julia on 24.11.2015.
 */
public class WordItem {
    String title;
    Long absolute;
    Double relative;

    public WordItem(String title, Long absolute, Double relative) {
        this.title = title;
        this.absolute = absolute;
        this.relative = relative;
    }

    @Override
    public String toString() {
        return title;
    }
}
