package com.juliazozulia.wordusage.Utils;

import android.util.Log;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

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
        LuceneMorphology luceneMorph = null;
        try {
            luceneMorph = new RussianLuceneMorphology();
        } catch (Exception e) {
            Log.v("LMorphology", "Error creating Russian Morphology");
        }
        return  luceneMorph;
    }

    public static LuceneMorphology getEnglishInstance() {
        return LazyEnglishMorphologyHolder.INSTANCE;
    }


    private static class LazyEnglishMorphologyHolder {
        private static final LuceneMorphology INSTANCE = CreateEnglishMorphology();
    }


    private static LuceneMorphology CreateEnglishMorphology() {
        LuceneMorphology luceneMorph = null;
        try {
            luceneMorph = new EnglishLuceneMorphology();
        } catch (Exception e) {
            Log.v("LMorphology", "Error creating English Morphology");
        }
        return  luceneMorph;
    }
}
