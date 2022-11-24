package org.matmech.params;

import org.matmech.context.Context;
import org.matmech.db.DBHandler;
import org.matmech.params.testingValidation.TestingValidation;

import java.util.HashMap;

public class Params {
    private final DBHandler db;

    /**
     * Функция, которая проверяет является ли сообщение стоп-словом
     * @param message - сообщение, которое отправил пользователь
     * @return - возвращается <i>true</i> - если message стоп-слово, <i>false</i> - если message не является стоп-словом
     */
    private boolean isStopOperation(String message) {
        return switch (message) {
            case "/stop" -> true;
            default -> false;
        };
    }

    /**
     * Возвращает стоп-сообщение под конкретный контекст
     * @param processName - название контекста
     * @return - стоп-сообщение в виде строки
     */
    private String stopOperationMessage(String processName) {
        return switch (processName) {
            case "testing" -> "Поздравляем с успешным прохождением теста!";
            default -> throw new IllegalArgumentException("Неправильное имя процесса");
        };
    }

    /**
     * Функция, которая заполняет кеш контекста необходимыми параметрами
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает сообщение-валидации или null, если валидация прошла успешно
     */
    private String testParams(final Context context, long chatId) {
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

    /**
     * Присваивает параметры какому-то контексту
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     * @param context -
     */
    private void setParams(final Context context, long chatId, String message) {
        final String PROCESS_NAME = context.getParams(chatId).get("processName");

        switch (PROCESS_NAME) {
            case "testing" -> setTestParams(context, chatId, message);
            case "as" -> {
                break;
            }
            default -> throw new IllegalArgumentException("Неправильное имя процесса");
        }
    }

    public Params(DBHandler db) {
        this.db = db;
    }

    /**
     * Обработчик параметров. Перед тем, как написать функцию для обработки какого-то контекста, нужно:
     * 1. Добавить для неё "стоп-операцию", если такая нужна
     * 2. Добавить для этой "стоп-операции" сообщение, если такая нужна
     * 3. В методе setParams написать новый кейс и соответствующий метод
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     * @return - возвращает сообщение-валидации или null, если заполнение параметров прошло успешно
     */
    public String handler(final Context context, long chatId, String message) {
        final String PROCESS_NAME = context.getParams(chatId).get("processName");

        if (isStopOperation(message)) {
            context.clear(chatId);
            return stopOperationMessage(PROCESS_NAME);
        }

        context.addParams(chatId, PROCESS_NAME, "message", message);

        if (context.getParams(chatId).get("settingParams") != null)
            setParams(context, chatId, message);

        return switch (PROCESS_NAME) {
            case "testing" -> testParams(context, chatId);
            default -> throw new IllegalArgumentException("Нет такого процесса");
        };
    }
}
