package org.matmech.params;

import org.matmech.context.Context;
import org.matmech.db.DBHandler;
import org.matmech.params.testingValidation.TestingValidation;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, который отвечает за получение и обработку входящих параметров
 */
public class Params {
    private final DBHandler db;
    private final String STANDARD_COUNT_WORDS;
    private final String STANDARD_MODE;

    /**
     * Обрабатывает полученные параметры для команды /test
     * @param context - контекст для всех пользователей
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает текст ошибки для пользователя или null при успехе
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
     * Присваивает параметры для контекста команды /test
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
     * Присваивает параметры для контекста команды /transalte
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
     * Присваивает параметры для контекста команды /get_group
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

    /**
     * Обрабатывает полученные параметры для команды /translate
     * @param context - контекст для всех пользователей
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает текст ошибки для пользователя или null при успехе
     */
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

    /**
     * Обрабатывает полученные параметры для команды /get_group
     * @param context - контекст для всех пользователей
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает текст ошибки для пользователя или null при успехе
     */
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
     * Присваивает параметры для контекста команды /word_add
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setWordAddParams(Context context, long chatId, String message) {
        HashMap<String, String> params = context.getParams(chatId);

        final String WORD = params.get("word");
        final String TRANSLATE = params.get("translate");
        final String GROUP = params.get("group");
        final String PROCESS_NAME = params.get("processName");

        if (WORD == null){
            context.addParams(chatId, PROCESS_NAME, "word", message);
            return;
        }

        if (GROUP == null){
            context.addParams(chatId, PROCESS_NAME, "group", message);
            return;
        }

        if (TRANSLATE == null)
            context.addParams(chatId, PROCESS_NAME, "translate", message);
    }

    /**
     * Обрабатывает полученные параметры для команды /word_add
     * @param context - контекст для всех пользователей
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает текст ошибки для пользователя или null при успехе
     */
    private String wordAddValidation(Context context, Long chatId){
        Map<String, String> params = context.getParams(chatId);

        final String PROCESS_NAME = params.get("processName");
        final String WORD = params.get("word");
        final String GROUP = params.get("group");
        final String TRANSLATE = params.get("translate");

        context.addParams(chatId, PROCESS_NAME, "settingParams", "true");

        if (WORD == null)
            return "Введи слово, которое хочешь добавить:";

        if (GROUP == null)
            return "Введи группу слова, которое хочешь добавить:";

        if (TRANSLATE == null)
            return "Введи перевод слова, которое хочешь добавить:";

        context.addParams(chatId, PROCESS_NAME, "settingParams", null);

        return null;
    }

    /**
     * Присваивает параметры для контекста команды /edit
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setEditParams(Context context, long chatId, String message) {
        HashMap<String, String> params = context.getParams(chatId);

        final String WORD = params.get("word");
        final String WORD_PARAM = params.get("wordParam");
        final String PARAM_VALUE = params.get("paramValue");
        final String PROCESS_NAME = params.get("processName");

        if (WORD == null){
            context.addParams(chatId, PROCESS_NAME, "word", message);
            return;
        }

        if (WORD_PARAM == null){
            context.addParams(chatId, PROCESS_NAME, "wordParam", message);
            return;
        }

        if (PARAM_VALUE == null)
            context.addParams(chatId, PROCESS_NAME, "paramValue", message);
    }

    /**
     * Обрабатывает полученные параметры для команды /edit
     * @param context - контекст для всех пользователей
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает текст ошибки для пользователя или null при успехе
     */
    private String editValidation(Context context, long chatId) {
        HashMap<String, String> params = context.getParams(chatId);

        final String PROCESS_NAME = params.get("processName");
        final String WORD = params.get("word");
        final String WORD_PARAM = params.get("wordParam");
        final String PARAM_VALUE = params.get("paramValue");

        context.addParams(chatId, PROCESS_NAME, "settingParams", "true");

        if (WORD == null)
            return "Введи слово, которое хочешь изменить:";

        if (WORD_PARAM == null)
            return "Введи параметр слова, которое хочешь изменить:";

        switch (WORD_PARAM){
            case "group", "translation" -> {}
            default -> {
                params.remove("wordParam");
                params.put("wordParam", null);
                return "Ой, кажется ты ввёл параметр неправильно, либо этот параметр не подлежит изменению! Повтори ввод!";
            }
        }

        if (PARAM_VALUE == null)
            return "Введи значение данного параметра:";

        context.addParams(chatId, PROCESS_NAME, "settingParams", null);

        return null;
    }

    /**
     * Обрабатывает полученные параметры для команды /delete_word
     * @param context - контекст для всех пользователей
     * @param chatId - идентификатор чата с пользователем
     * @return - возвращает текст ошибки для пользователя или null при успехе
     */
    private String deleteWordValidation(Context context, long chatId) {

        HashMap<String, String> params = context.getParams(chatId);

        final String PROCESS_NAME = params.get("processName");
        final String WORD = params.get("word");

        context.addParams(chatId, PROCESS_NAME, "settingParams", "true");

        if (WORD == null)
            return "Введи слово, которое ты хочешь удалить:";

        if (!db.IsWordExist(WORD)) {
            params.remove("word");
            params.put("word", null);
            return "Ой, кажется ты ввёл слово неправильно! Повтори ввод!";
        }

        context.addParams(chatId, PROCESS_NAME, "settingParams", null);

        return null;
    }

    /**
     * Присваивает параметры для контекста команды /delete_word
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     */
    private void setDeleteWordParams(Context context, long chatId, String message) {
        HashMap<String, String> params = context.getParams(chatId);

        final String WORD = params.get("word");
        final String PROCESS_NAME = params.get("processName");

        if (WORD == null)
            context.addParams(chatId, PROCESS_NAME, "word", message);
    }

    public Params(DBHandler db) {
        this.db = db;
        STANDARD_COUNT_WORDS = "10";
        STANDARD_MODE = "easy";
    }

    /**
     * Обработчик параметров
     * @param chatId - идентификатор чата с пользователем
     * @param message - сообщение, которое отправил пользователь
     * @return - возвращает сообщение-валидации или null, если заполнение параметров прошло успешно
     */
    public String handler(final Context context, long chatId, String message) {
        final String PROCESS_NAME = context.getParams(chatId).get("processName");

        context.addParams(chatId, PROCESS_NAME, "message", message);

        return switch (PROCESS_NAME) {
            case "testing" -> {
                if (context.getParams(chatId).get("settingParams") != null)
                    setTestParams(context, chatId, message);

                yield testParamsValidation(context, chatId);
            }
            case "translating" -> {
                if (context.getParams(chatId).get("settingParams") != null)
                    setTranslateParams(context, chatId, message);

                yield translateValidation(context, chatId);
            }
            case "getGroup" -> {
                if (context.getParams(chatId).get("settingParams") != null)
                    setGetGroupParams(context, chatId, message);

                yield getGroupValidation(context, chatId);
            }
            case "wordAdd" -> {
                if (context.getParams(chatId).get("settingParams") != null)
                    setWordAddParams(context, chatId, message);

                yield wordAddValidation(context, chatId);
            }
            case "edit" -> {
                if (context.getParams(chatId).get("settingParams") != null)
                    setEditParams(context, chatId, message);

                yield editValidation(context, chatId);
            }
            case "deleteWord" -> {
                if (context.getParams(chatId).get("settingParams") != null)
                    setDeleteWordParams(context, chatId, message);

                yield deleteWordValidation(context, chatId);
            }
            default -> null;
        };
    }
}
