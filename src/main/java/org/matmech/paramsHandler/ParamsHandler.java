package org.matmech.paramsHandler;

import org.matmech.cache.Cache;
import org.matmech.db.DBHandler;

import java.util.HashMap;

public class ParamsHandler {
    private final Cache cache;
    private final DBHandler db;

    /**
     * Определяет является ли сообщение стоп-словом
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

        final String DEBUG_MODE_ANSWER = "Вы ввели не существующий режим!\n";
        final String STANDARD_COUNT_WORDS = "10";
        final String STANDARD_MODE = "easy";

        cache.addParams(chatId, params.get("processName"), "settingParams", "true"); // обязательная строка,
        // которая определяет контекст присваивания параметров

        // Валидация группы слова

        if (groups == null)
            return "Пожалуйста, введите группу слов, по которым вы хотите произвести тестирование\n" +
                    "Если хотите провести тестирование по всем группу, то напишите `Все`";

        groups = groups.toLowerCase();

        if (!db.groupIsExist(groups) && !groups.equals("все")) {
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
                params.remove("mode");
                params.put("mode", null);
                return DEBUG_MODE_ANSWER;
        }

        if (mode.equals("стандартный"))
            mode = STANDARD_MODE;

        params.putIfAbsent("currentQuestion", "0");

        cache.addParams(chatId, params.get("processName"), "settingParams", null); // обязательная строка,
        // которая говорит нам, что параметры перестали обрабатываться

        // начало тестирования

        if (countWords.equals("по всем")) {
            String currentQuestion = String.valueOf(Integer.parseInt(params.get("currentQuestion")) + 1);
            params.remove("currentQuestion");
            params.put("currentQuestion", currentQuestion);

//            return db.getTestQuestion();
            return "Вам задали вопрос. Ответьте!";
        }

        if (Integer.parseInt(params.get("currentQuestion")) <= Integer.parseInt(countWords)) {
            String currentQuestion = String.valueOf(Integer.parseInt(params.get("currentQuestion")) + 1);
            params.remove("currentQuestion");
            params.put("currentQuestion", currentQuestion);

//            return db.getTestQuestion();
            return "Вам задали вопрос. Ответьте!";
        }

        cache.clear(chatId);

        return "Тест завершен";
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
     * 3. В методе setParams написать новый кейс и соответственный метод
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
