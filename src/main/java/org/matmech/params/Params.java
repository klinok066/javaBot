package org.matmech.params;

import org.jetbrains.annotations.NotNull;
import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.TranslateWord.TranslateWord;
import org.matmech.db.DBHandler;
import org.matmech.db.models.Words;
import org.matmech.db.repository.DBConnection;
import org.matmech.params.testingValidation.TestingValidation;
import org.matmech.db.bll.WordsDBSource;

import java.util.HashMap;

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

        String groups = params.get("group");
        String countWords = params.get("countWords");
        String mode = params.get("mode");

        // Валидация

        TestingValidation testingValidation = new TestingValidation(groups, countWords, mode);

        String groupValidation = testingValidation.validationGroup(params, db);
        String countWordsValidation = testingValidation.validationCountWords(params);
        String modeValidation = testingValidation.validationMode(params);

        context.addParams(chatId, "testing", "settingParams", "true"); // обязательная строка,
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

        context.addParams(chatId, "testing", "settingParams", null); // обязательная строка,
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



    private String TranslateValidation(final Context context, long chatId, String message){
        HashMap<String, String> params = context.getParams(chatId);
        final String PROCESS_NAME = params.get("processName");
        if(!db.IsWordExist(message)){
            return "Ой, кажется ты ввёл слово неправильно!";
        }
        context.addParams(chatId, PROCESS_NAME , "word", message);
        return null;
    }

    private String getGroupValidation(final Context context, long chatId, String message){
        HashMap<String, String> params = context.getParams(chatId);
        final String PROCESS_NAME = params.get("processName");
        if(!db.groupIsExist(message)){
            return "Ошибка! Такой группы не существует!";
        }
        context.addParams(chatId, PROCESS_NAME , "group", message);
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
            case "translateWord" -> TranslateValidation(context,chatId,message);
            case "getGroup" -> getGroupValidation(context, chatId, message);
            default -> throw new IllegalArgumentException("Неправильное имя процесса");
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
            default -> throw new IllegalArgumentException("Нет такого процесса");
        };
    }
}
