package org.matmech.params;

import org.matmech.context.Context;
import org.matmech.db.DBHandler;
import org.matmech.params.testingValidation.TestingValidation;

import java.util.HashMap;
import java.util.Map;

public class Params {
    private final DBHandler db;
    private final String STANDARD_COUNT_WORDS;
    private final String STANDARD_MODE;

    /**
     * Функция проводит проверку параметров на правильность
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает сообщение-валидации или null, если валидация прошла успешно
     */
    private String testParamsValidation(final Context context, long chatId) {
        HashMap<String, String> params = context.getParams(chatId);

        final String PROCESS_NAME = params.get("processName");

        String groups = params.get("group");
        String countWords = params.get("countWords");
        String mode = params.get("mode");

        // Валидация

        TestingValidation testingValidation = new TestingValidation(groups, countWords, mode);

        String groupValidation = testingValidation.validationGroup(params, db);
        String countWordsValidation = testingValidation.validationCountWords(params);
        String modeValidation = testingValidation.validationMode(params);

        context.addParams(chatId, PROCESS_NAME, "settingParams", "true"); // обязательная строка,
        // которая определяет контекст присваивания параметров

        // Валидация группы слова

        if (groupValidation != null)
            return groupValidation;

        // Валидация количество слов

        if (countWordsValidation != null)
            return countWordsValidation;

        // Валидация режима тестирования

        if (modeValidation != null)
            return modeValidation;

        // присваивание стандартных значений

        if (countWords.equals("стандартное")) {
            params.remove("countWords", countWords);
            params.put("countWords", STANDARD_COUNT_WORDS);
        }

        if (mode.equals("стандартный")) {
            params.remove("mode", countWords);
            params.put("mode", STANDARD_MODE);
        }

        params.putIfAbsent("currentQuestion", "0");

        context.addParams(chatId, PROCESS_NAME, "settingParams", null); // обязательная строка,
        // которая говорит нам, что параметры перестали обрабатываться

        return null;
    }

    /**
     * Присваивает параметры тестам
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setTestParams(final Context context, long chatId, String message) {
        HashMap<String, String> params = context.getParams(chatId);

        final String GROUP = params.get("group");
        final String COUNT_WORDS = params.get("countWords");
        final String MODE = params.get("mode");
        final String PROCESS_NAME = params.get("processName");

        if (GROUP == null) {
            context.addParams(chatId, PROCESS_NAME, "group", message);
            return;
        }

        if (COUNT_WORDS == null) {
            context.addParams(chatId, PROCESS_NAME, "countWords", message);
            return;
        }

        if (MODE == null)
            context.addParams(chatId, PROCESS_NAME, "mode", message);
    }

    /**
     * Присваивает параметры переводу слова
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setTranslateParams(final Context context, long chatId, String message) {
        HashMap<String, String> params = context.getParams(chatId);

        final String WORD = params.get("word");
        final String PROCESS_NAME = params.get("processName");

        if (WORD == null)
            context.addParams(chatId, PROCESS_NAME, "word", message);
    }

    /**
     * Присваивает параметры получения группы
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setGetGroupParams(final Context context, long chatId, String message) {
        HashMap<String, String> params = context.getParams(chatId);

        final String WORD = params.get("word");
        final String PROCESS_NAME = params.get("processName");

        if (WORD == null)
            context.addParams(chatId, PROCESS_NAME, "word", message);
    }

    private String translateValidation(final Context context, long chatId){
        Map<String, String> params = context.getParams(chatId);

        final String PROCESS_NAME = params.get("processName");
        final String WORD = params.get("word");

        context.addParams(chatId, PROCESS_NAME, "settingParams", "true");

        if (WORD == null)
            return "Введи слово, которое хочешь перевести:";

        if (!db.IsWordExist(WORD)) {
            params.remove("word");
            params.put("word", null);
            return "Ой, кажется ты ввёл слово неправильно! Повтори ввод!";
        }

        context.addParams(chatId, PROCESS_NAME, "settingParams", null);

        return null;
    }

    private String getGroupValidation(final Context context, long chatId){
        HashMap<String, String> params = context.getParams(chatId);

        final String PROCESS_NAME = params.get("processName");
        final String WORD = params.get("word");

        context.addParams(chatId, PROCESS_NAME, "settingParams", "true");

        if (WORD == null)
            return "Введи слово, у которого хочешь получить группу:";

        if (!db.IsWordExist(WORD)) {
            params.remove("word");
            params.put("word", null);
            return "Ой, кажется ты ввёл слово неправильно! Повтори ввод!";
        }

        context.addParams(chatId, PROCESS_NAME, "settingParams", null);

        return null;
    }

    /**
     * Присваивает параметры какому-то контексту
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     * @param context - контекст со всеми пользователями
     */
    private void setParams(final Context context, long chatId, String message) {
        final String PROCESS_NAME = context.getParams(chatId).get("processName");

        switch (PROCESS_NAME) {
            case "testing" -> setTestParams(context, chatId, message);
            case "translating" -> setTranslateParams(context, chatId, message);
            case "getGroup" -> setGetGroupParams(context, chatId, message);
        }
    }

    public Params(DBHandler db) {
        this.db = db;
        STANDARD_COUNT_WORDS = "10";
        STANDARD_MODE = "easy";
    }

    /**
     * Обработчик параметров. Перед тем, как написать функцию для обработки какого-то контекста, нужно:
     * 1. В методе setParams написать новый кейс и соответствующий метод
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     * @return - возвращает сообщение-валидации или null, если заполнение параметров прошло успешно
     */
    public String handler(final Context context, long chatId, String message) {
        final String PROCESS_NAME = context.getParams(chatId).get("processName");

        context.addParams(chatId, PROCESS_NAME, "message", message);

        if (context.getParams(chatId).get("settingParams") != null)
            setParams(context, chatId, message);

        return switch (PROCESS_NAME) {
            case "testing" -> testParamsValidation(context, chatId);
            case "translating" -> translateValidation(context, chatId);
            case "getGroup" -> getGroupValidation(context, chatId);
            default -> null;
        };
    }
}
