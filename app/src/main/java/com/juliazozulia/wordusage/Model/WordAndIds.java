package com.juliazozulia.wordusage.Model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Julia on 25.12.2015.
 */
public class WordAndIds {

    private final HashMap<String, Set<Integer>> map; //word (normal form), ids

    public WordAndIds() {

        map = new HashMap<>();
    }

    public void addValue(String word, Integer id) {

        Set<Integer> ids = map.get(word);
        if (ids == null) {
            ids = new HashSet<>();
        }
        ids.add(id);

        map.put(word, ids);
    }

    public Iterator<String> getWordIterator() {
        return map.keySet().iterator();
    }

    public Iterator<Integer> getIdsIterator(String word) {
        return map.get(word).iterator();
    }

    public String getIds(String word) {

        Set<Integer> ids = map.get(word);
        StringBuilder str = new StringBuilder();
        for (Integer id : ids) {

            str.append(" ").append(String.valueOf(id));
        }
        return str.toString();
    }
}
