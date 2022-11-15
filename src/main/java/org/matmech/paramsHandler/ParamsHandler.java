package org.matmech.paramsHandler;

import org.matmech.cache.Cache;
import org.matmech.db.DBHandler;
import org.matmech.requests.requestsLogic.RequestsLogic;

import java.util.HashMap;

public class ParamsHandler {
    private final Cache cache;
    private final DBHandler db;
    // прикрутить суда реквестсЛоджик и выкрутить его с реквестХендлер

    private String testing(long chatId) {
        HashMap<String, String> params = cache.getParams(chatId);

        String groups = params.get("group");
        String countWords = params.get("countWords");
        String mode = params.get("mode");

        final String DEBUG_MODE_ANSWER = "Вы ввели не существующий режим!\n";
        final String STANDARD_COUNT_WORDS = "10";
        final String STANDARD_MODE = "easy";

        // Валидация группы слова

        if (groups == null)
            return "Пожалуйста, введите группу слов, по которым вы хотите произвести тестирование\n" +
                   "Если хотите провести тестирование по всем группу, то напишите `Все`";

        if (!db.groupIsExist(groups)) {
            params.remove("group");
            params.put("group", null);

            return "Такой группы не существует. Пожалуйста, введите существующую группу!";
        }

        // Валидация количество слов

        if (countWords == null)
            return "Пожалуйста, введите количество слов в тесте\n" +
                    "Если хотите провести тестирование по всем группу, то напишите `По всем`\n" +
                    "Если хотите стандартное количество слов (10), то напишите `Стандартное`";

        countWords = countWords.toLowerCase();

        if (countWords.equals("стандартное"))
            countWords = STANDARD_COUNT_WORDS;

        if (!countWords.equals("по всем")) {
            try {
                Integer testForCorrectNumber = Integer.valueOf(countWords);
            } catch (NumberFormatException e) {
                params.remove("countWords");
                params.put("countWords", null);

                return "Вы ввели не число! Повторите ввод:";
            }
        }

        // Валидация режима тестирования

        if (mode == null)
            return "Введите режим тестирование: `Easy` - легкий, `Difficult` - сложный\n" +
                    "Если хотите стандартный режим (Easy), то введите `Стандартный`";

        mode = mode.toLowerCase();

        switch (mode) {
            case "easy":
            case "difficult":
            case "стандартный":
                break;
            default:
                return DEBUG_MODE_ANSWER;
        }

        if (mode.equals("стандартный"))
            mode = STANDARD_MODE;

        params.putIfAbsent("currentQuestion", "0");

        if (
                Integer.parseInt(params.get("currentQuestion")) <= Integer.parseInt(countWords)
                || countWords.equals("по всем")
        ) {
            String currentQuestion = String.valueOf(Integer.parseInt(params.get("currentQuestion")) + 1);
            params.remove("currentQuestion");
            params.put("currentQuestion", currentQuestion);

//            return db.getTestQuestion();
            return "question";
        }

        return "error";
    }

    public ParamsHandler(Cache cache, DBHandler db) {
        this.cache = cache;
        this.db = db;
    }

    public String handler(long chatId) {
        HashMap<String, String> params = cache.getParams(chatId);

        return switch (params.get("processName")) {
            case "testing" -> testing(chatId);
            default -> throw new IllegalArgumentException("Нет такого процесса");
        };
    }
}
