package org.matmech.context.contextHandler.handlers.testContext;

import org.matmech.context.contextHandler.handlers.Command;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Этот класс отвечает за функционал контекста тестирования
 */
public class TestCommand implements Command {
    private HashMap<String, ArrayList<String>> cachedWords;
    private DBHandler db;

    /**
     * Формирует готовый вопрос для теста
     * @param tag - тег пользователя
     * @param group - группа, по которому производится тестирование
     * @return - возвращает Map с готовым вопросом и словом, которое загадали в вопросе
     */
    private Map<String, String> getQuestion(String tag, String group) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("word", db.getRandomWord(tag, group));

        cachedWords.computeIfAbsent(tag, k -> new ArrayList<String>());

        ArrayList<String> userCachedWords = cachedWords.get(tag);

        if (db.getCountsWordsOfUser(tag) == userCachedWords.size())
            userCachedWords.clear();

        while (userCachedWords.contains(result.get("word")))
            result.put("word", db.getRandomWord(tag, group));

        userCachedWords.add(result.get("word"));

        result.put("question", "Укажите перевод слова " + result.get("word") + ": ");

        return result;
    }

    /**
     * Формулирует ответы на вопрос
     * @param tag - тег пользователя
     * @param group - группа слов
     * @param mode - режим тестирования
     * @return - готовый текст, содержащий ответы на вопрос
     */
    private String getAnswers(String tag, String group, String mode, String trueWordTranslate) {
        final int COUNT_ANSWERS = 4;

        if (mode.equals("difficult"))
            return "";

        List<String> words = new ArrayList<String>();
        String word = db.getRandomWord(tag, group);
        words.add(word);

        for (int i = 0; i < 2; i++) {
            while(words.contains(word))
                word = db.getRandomWord(tag, group);

            words.add(word);
        }

        StringBuilder answers = new StringBuilder();
        int trueAnswerIndex = (int)(Math.random() * COUNT_ANSWERS);
        int wordIndex = 0;

        for (int i = 0; i < COUNT_ANSWERS; i++) {
            answers.append(i + 1);
            answers.append(": ");

            if (i == trueAnswerIndex) {
                answers.append(trueWordTranslate);
                answers.append("\n");
                continue;
            }

            answers.append(db.translateWordWithoutMessage(words.get(wordIndex++)));
            answers.append("\n");
        }

        return answers.toString();
    }

    /**
     * Очищает кеш-вопросов
     * @param tag - тег пользователя
     */
    private void clearTestQuestions(String tag) {
        cachedWords.get(tag).clear();
    }

    public TestCommand(DBHandler db) {
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

        final String GROUP = params.get("group").toLowerCase();
        final String COUNT_WORDS = params.get("countWords").toLowerCase();
        final String MODE = params.get("mode").toLowerCase();

        if (!COUNT_WORDS.equals("по всем") && Integer.parseInt(params.get("currentQuestion")) > Integer.parseInt(COUNT_WORDS)) {
            context.clear(CHAT_ID);
            clearTestQuestions(TAG);
            return "Тест завершен";
        }

        String currentQuestion = String.valueOf(Integer.parseInt(params.get("currentQuestion")) + 1);
        params.remove("currentQuestion");
        params.put("currentQuestion", currentQuestion);

        Map<String, String> questionData = getQuestion(TAG, GROUP);

        final String QUESTION = questionData.get("question");
        final String TRUE_WORD = questionData.get("word");
        final String ANSWERS_FOR_QUESTION = getAnswers(TAG, GROUP, MODE, db.translateWordWithoutMessage(TRUE_WORD));

        return QUESTION + "\n" + ANSWERS_FOR_QUESTION;
    }
}
