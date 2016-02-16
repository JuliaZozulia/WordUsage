package com.juliazozulia.wordusage.Utils;

import android.util.Log;

import com.juliazozulia.wordusage.Events.FrequencyLoadStatesChanged;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import de.greenrobot.event.EventBus;

/**
 * Created by Julia on 27.11.2015.
 */
public class LMorphology {

    private LMorphology() {

    }

    public static LuceneMorphology getRussianInstance() {
        return LazyRussianMorphologyHolder.INSTANCE;
    }


    private static class LazyRussianMorphologyHolder {
        private static final LuceneMorphology INSTANCE = CreateRussianMorphology();
    }


    private static LuceneMorphology CreateRussianMorphology() {
        EventBus.getDefault().post(new FrequencyLoadStatesChanged(FrequencyLoadStatesChanged.LoadState.START_LUCENE));
        LuceneMorphology luceneMorph = null;
        try {
            luceneMorph = new RussianLuceneMorphology();
        } catch (Exception e) {
            Log.v("LMorphology", "Error creating Russian Morphology");
        }
        EventBus.getDefault().post(new FrequencyLoadStatesChanged(FrequencyLoadStatesChanged.LoadState.FINISH_LUCENE));
        return luceneMorph;
    }

    public static LuceneMorphology getEnglishInstance() {
        return LazyEnglishMorphologyHolder.INSTANCE;
    }


    private static class LazyEnglishMorphologyHolder {
        private static final LuceneMorphology INSTANCE = CreateEnglishMorphology();
    }


    private static LuceneMorphology CreateEnglishMorphology() {
        EventBus.getDefault().post(new FrequencyLoadStatesChanged(FrequencyLoadStatesChanged.LoadState.START_LUCENE));
        LuceneMorphology luceneMorph = null;
        try {
            luceneMorph = new EnglishLuceneMorphology();
        } catch (Exception e) {
            Log.v("LMorphology", "Error creating English Morphology");
        }
        EventBus.getDefault().post(new FrequencyLoadStatesChanged(FrequencyLoadStatesChanged.LoadState.FINISH_LUCENE));
        return luceneMorph;
    }
}
