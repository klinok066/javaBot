package org.matmech.context.contextHandler.handlers.testContext;

import org.matmech.context.contextHandler.handlers.Handler;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.context.Context;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Этот класс отвечает за функционал контекста тестирования
 */
public class TestContext implements Handler {
    private HashMap<String, ArrayList<String>> cachedWords;
    private DBHandler db;

    /**
     * Формирует готовый вопрос для теста
     * @param tag - тег пользователя
     * @param group - группа, по которому производится тестирование
     * @return - готовый вопрос
     */
    private String getQuestion(String tag, String group) {
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

    /**
     * Очищает кеш-вопросов
     * @param tag - тег пользователя
     */
    private void clearTestQuestions(String tag) {
        cachedWords.get(tag).clear();
    }

    public TestContext(DBHandler db) {
        this.db = db;
        cachedWords = new HashMap<String, ArrayList<String>>();
    }

    /**
     * Запускает тест
     * @param context - информация о всех контекстов для всех пользователей
     * @param info - объект DataSaver с информацией о пользователе
     * @return - возвращает сообщение пользователю (Вопрос или Завершающее тестирование)
     */
    public String handle(Context context, DataSaver info) {
        final long CHAT_ID = info.getChatId();
        final String TAG = info.getTag();

        HashMap<String, String> params = context.getParams(CHAT_ID);

        String groups = params.get("group");
        String countWords = params.get("countWords");
        String mode = params.get("mode");

        if (!countWords.equals("по всем") && Integer.parseInt(params.get("currentQuestion")) > Integer.parseInt(countWords)) {
            context.clear(CHAT_ID);
            clearTestQuestions(TAG);
            return "Тест завершен";
        }

        String currentQuestion = String.valueOf(Integer.parseInt(params.get("currentQuestion")) + 1);
        params.remove("currentQuestion");
        params.put("currentQuestion", currentQuestion);

        return getQuestion(TAG, groups);
    }
}
