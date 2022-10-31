package org.matmech.db.bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.matmech.db.models.Words;
import org.matmech.db.repository.DBConnection;

public class WordsDBSource {
    /***
     * Создает HashMap объект с параметром
     * @param type - тип параметра
     * @param value - значение параметра
     */
    private HashMap<String, String> createParams(String type, String value) {
        HashMap<String, String> item = new HashMap<String, String>();

        item.put("type", type);
        item.put("value", value);

        return item;
    }

    /**
     * <p>Проверяет на существование какого-то слова</p>
     * <p>Перед этим нужно проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     */
    private boolean isExist(Words words, DBConnection dbConnection) {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String getWordsSQL = "select * from words where word_value=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Добавляет слово в базу данных. Если слово уже существует, то будет возвращаенно соответствующее сообщение</p>
     */
    public String wordAdd(Words words, DBConnection dbConnection) {
        if (isExist(words, dbConnection))
            return "Ошибка! Слово уже существует!\nЕсли хотите что-то поменять в слове, то воспользуйтесь командой /edit";

        ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
        params.add(createParams("int", Integer.toString(words.getDictonaryId())));
        params.add(createParams("int", Integer.toString(words.getGroupId())));
        params.add(createParams("string", words.getWordValue()));
        params.add(createParams("string", words.getWordTranslate()));

        String wordAddSQL = "insert into words(dictonary_id, group_id, word_value, word_translate) values(?, ?, ?, ?)";
        dbConnection.executeUpdateWithParams(wordAddSQL, params);

        return "Слово было успешно добавлено!";
    }

    /**
     * <p>Достает слово из базы данных</p>
     * <p>Перед этим надо проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     */
    public String translate(Words words, DBConnection dbConnection) {
        try {
            if (isExist(words, dbConnection)) {
                ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", words.getWordValue()));

                String translateValueSQL = "select word_translate from words where word_value=?";

                ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(translateValueSQL, params);

                for (HashMap<String, String> item : response)
                    return "Перевод слова " + words.getWordValue() + " - " + item.get("word_translate");
            }

            return "Ошибка! В словаре нет этого слова!\n Полный список команд можете посмотреть с помощью /help";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает dictonary_id слова из базы данные. Возвращает строку - dictonary_id
     */
    public String getDictonaryId(Words words, DBConnection dbConnection) {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String geDictonaryIdSQL = "select * from words where word_value=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(geDictonaryIdSQL, params);

            for (HashMap<String, String> item : response)
                return item.get("dictonary_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Не удалось получить группу";
    }

    /**
     * <p>Меняет параметр word_translate</p>
     * <p>Перед этим надо проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     */
    public String editTranslation(Words words, DBConnection dbConnection) {
        if (isExist(words, dbConnection)) {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String translateValueSQL = "UPDATE words SET word_translate = '" + words.getWordTranslate() + "' WHERE word_value = ?";


            dbConnection.executeUpdateWithParams(translateValueSQL, params);
            return "вы успешно изменили translation";
        }

        return "Ошибка, редактирование не удалось выполнить, так как слова нет в словаре - прежде чем поменять что-то в нем, добавьте его в словарь";

    }

    /**
     * <p>Удаляет слово из базы данных</p>
     * <p>Перед этим надо проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     */
    public String deleteWord(Words words, DBConnection dbConnection) {
        if (isExist(words, dbConnection)) {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String deleteWordSQL = "delete from words where word_value=?";

            dbConnection.executeUpdateWithParams(deleteWordSQL, params);

            return "Слово было удалено из базы данных!";
        }

        return "Слова нет в базе данных!";
    }
}
