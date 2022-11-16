package org.matmech.paramsHandler.testingValidation;

import org.matmech.db.DBHandler;

import java.util.HashMap;
import java.util.Objects;

/**
 * В этом классе происходит валидация параметров теста
 */
public class TestingValidation {
    private String groups;
    private String countWords;
    private String mode;
    private final String DEBUG_MODE_ANSWER;
    private final String STANDARD_COUNT_WORDS;
    private final String STANDARD_MODE;

    public TestingValidation(String groups, String countWords, String mode) {
        this.groups = Objects.requireNonNullElseGet(groups, groups::toLowerCase);
        this.countWords = Objects.requireNonNullElseGet(countWords, countWords::toLowerCase);
        this.mode = Objects.requireNonNullElseGet(mode, mode::toLowerCase);

        DEBUG_MODE_ANSWER = "Вы ввели не существующий режим!\n";
        STANDARD_COUNT_WORDS = "10";
        STANDARD_MODE = "easy";
    }

    /**
     * Этот метод делает валидацию параметра <b>группы</b>
     * @param params - все параметры контекста "тестирования"
     * @param db - база данных (DBHandler)
     * @return - возвращает сообщение пользователю об ошибке или null, если все верно
     */
    public String validationGroup(final HashMap<String, String> params, final DBHandler db) {
        if (groups == null)
            return "Пожалуйста, введите группу слов, по которым вы хотите произвести тестирование\n" +
                    "Если хотите провести тестирование по всем группу, то напишите `Все`";

        if (!db.groupIsExist(groups) && !groups.equals("все")) {
            params.remove("group");
            params.put("group", null);

            return "Такой группы не существует. Пожалуйста, введите существующую группу!";
        }

        return null;
    }

    /**
     * Этот метод делает валидацию параметры <b>количества слов</b>
     * @param params - все параметры контекста "тестирования"
     * @return - возвращает сообщение пользователю об ошибке или null, если все верно
     */
    public String validationCountWords(final HashMap<String, String> params) {
        if (countWords == null)
            return "Пожалуйста, введите количество слов в тесте\n" +
                    "Если хотите провести тестирование по всем группу, то напишите `По всем`\n" +
                    "Если хотите стандартное количество слов (10), то напишите `Стандартное`";

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

        return null;
    }

    /**
     * Этот метод делает валидацию параметры <b>режима тестирования</b>
     * @param params - все параметры контекста "тестирования"
     * @return - возвращает сообщение пользователю об ошибке или null, если все верно
     */
    public String validationMode(final HashMap<String, String> params) {
        if (mode == null)
            return "Введите режим тестирование: `Easy` - легкий, `Difficult` - сложный\n" +
                    "Если хотите стандартный режим (Easy), то введите `Стандартный`";

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

        return null;
    }
}
