package org.matmech.tests;

import org.matmech.db.DBHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Tests implements TestsInterface {
    private HashMap<String, ArrayList<String>> cachedWords;
    private DBHandler db;

    public Tests(DBHandler db) {
        this.db = db;
        cachedWords = new HashMap<String, ArrayList<String>>();
    }

    public String getQuestion(String tag, String group, String mode) {
        String word = db.getRandomWord(tag, group);

        cachedWords.computeIfAbsent(tag, k -> new ArrayList<String>());

        ArrayList<String> userCachedWords = cachedWords.get(tag);

        if (db.getCountsWordsOfUser(tag) == userCachedWords.size())
            userCachedWords.clear();

        while (userCachedWords.contains(word))
            word = db.getRandomWord(tag, group);

        userCachedWords.add(word);

        return "Укажите перевод слова " + word + ": ";
    }

    public void clearTestQuestions(String tag) {
        cachedWords.get(tag).clear();
    }
}
