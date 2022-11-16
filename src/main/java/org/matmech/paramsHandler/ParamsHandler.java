package org.matmech.paramsHandler;

import org.matmech.cache.Cache;
import org.matmech.db.DBHandler;
import org.matmech.paramsHandler.testingValidation.TestingValidation;

import java.util.HashMap;

public class ParamsHandler {
    private final Cache cache;
    private final DBHandler db;

    /**
     * Проверяет является ли сообщение стоп-словом
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
     * Функция, которая тестирует пользователя
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает ответ пользователю
     */
    private String testing(long chatId) {
        HashMap<String, String> params = cache.getParams(chatId);

        String groups = params.get("group");
        String countWords = params.get("countWords");
        String mode = params.get("mode");

        // Валидация

        TestingValidation testingValidation = new TestingValidation(groups, countWords, mode);

        String groupValidation = testingValidation.validationGroup(params, db);
        String countWordsValidation = testingValidation.validationCountWords(params);
        String modeValidation = testingValidation.validationMode(params);

        cache.addParams(chatId, params.get("processName"), "settingParams", "true"); // обязательная строка,
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

        cache.addParams(chatId, params.get("processName"), "settingParams", null); // обязательная строка,
        // которая говорит нам, что параметры перестали обрабатываться

        // начало тестирования

        if (Integer.parseInt(params.get("currentQuestion")) > Integer.parseInt(countWords)) {
            cache.clear(chatId);
            return "Тест завершен";
        }

        String currentQuestion = String.valueOf(Integer.parseInt(params.get("currentQuestion")) + 1);
        params.remove("currentQuestion");
        params.put("currentQuestion", currentQuestion);

//            return db.getTestQuestion();
        return "Вам задали вопрос. Ответьте!";
    }

    /**
     * Присваивает параметры тестам
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setTestParams(long chatId, String message) {
        HashMap<String, String> params = cache.getParams(chatId);

        final String GROUP = params.get("group");
        final String COUNT_WORDS = params.get("countWords");
        final String MODE = params.get("mode");
        final String PROCESS_NAME = params.get("processName");

        if (GROUP == null) {
            cache.addParams(chatId, PROCESS_NAME, "group", message);
            return;
        }

        if (COUNT_WORDS == null) {
            cache.addParams(chatId, PROCESS_NAME, "countWords", message);
            return;
        }

        if (MODE == null)
            cache.addParams(chatId, PROCESS_NAME, "mode", message);
    }

    /**
     * Присваивает параметры какому-то контексту
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setParams(long chatId, String message) {
        final String PROCESS_NAME = cache.getParams(chatId).get("processName");

        switch (PROCESS_NAME) {
            case "testing" -> setTestParams(chatId, message);
            case "as" -> {
                break;
            }
            default -> throw new IllegalArgumentException("Неправильное имя процесса");
        }
    }

    public ParamsHandler(Cache cache, DBHandler db) {
        this.cache = cache;
        this.db = db;
    }

    /**
     * Обработчик параметров. Перед тем, как написать функцию для обработки какого-то контекста, нужно:
     * 1. Добавить для неё "стоп-операцию", если такая нужна
     * 2. Добавить для этой "стоп-операции" сообщение, если такая нужна
     * 3. В методе setParams написать новый кейс и соответствующий метод
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     * @return - возвращает сообщение пользователю в виде строки
     */
    public String handler(long chatId, String message) {
        final String PROCESS_NAME = cache.getParams(chatId).get("processName");

        if (isStopOperation(message)) {
            cache.clear(chatId);
            return stopOperationMessage(PROCESS_NAME);
        }

        cache.addParams(chatId, PROCESS_NAME, "message", message);

        if (cache.getParams(chatId).get("settingParams") != null)
            setParams(chatId, message);

        return switch (PROCESS_NAME) {
            case "testing" -> testing(chatId);
            default -> throw new IllegalArgumentException("Нет такого процесса");
        };
    }
}
