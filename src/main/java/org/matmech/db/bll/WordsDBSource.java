package org.matmech.db.bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.matmech.db.models.Words;
import org.matmech.db.repository.DBConnection;

public class WordsDBSource extends DBSource {
    /**
     * <p>Проверяет на существование какого-то слова</p>
     * @param words - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @param dbConnection - репозиторий
     * @return - возвращает существование слова в бд
     */
    private boolean isExist(Words words, DBConnection dbConnection) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String getWordsSQL = "select * from words where word_value=?";

            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            System.out.println("Не удалось проверить на существование слово\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Добавляет слово в базу данных</p>
     * @param words - объект с информацией о слове. Обязательные поля для заполнения:
     *                                                  <i>dictonaryId</i>
     *                                                  <i>groupId</i>
     *                                                  <i>wordValue</i>
     *                                                  <i>wordTranslate</i>
     * @param dbConnection - репозиторий
     * @return - возвращает <i>true</i> при успешном выполнении метода и <i>false</i> при случае, когда слово уже существует
     */
    public boolean wordAdd(Words words, DBConnection dbConnection) {
        if (isExist(words, dbConnection))
            return false;

        List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
        params.add(createParams("int", Integer.toString(words.getDictonaryId())));
        params.add(createParams("int", Integer.toString(words.getGroupId())));
        params.add(createParams("string", words.getWordValue()));
        params.add(createParams("string", words.getWordTranslate()));

        String wordAddSQL = "insert into words(dictonary_id, group_id, word_value, word_translate) values(?, ?, ?, ?)";
        dbConnection.executeUpdateWithParams(wordAddSQL, params);

        return true;
    }

    /**
     * <p>Достает слово из базы данных</p>
     * @param words - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @param dbConnection - репозиторий
     * @return - возвращает строчку с ошибкой или со значением поля translate (в красивом виде)
     */
    public String translate(Words words, DBConnection dbConnection) {
        try {
            if (isExist(words, dbConnection)) {
                List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", words.getWordValue()));

                String translateValueSQL = "select word_translate from words where word_value=?";

                List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(translateValueSQL, params);

                for (HashMap<String, String> item : response)
                    return item.get("word_translate");
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить перевод слова\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Возвращает dictonary_id слова из базы данных
     * @param words - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @param dbConnection - репозиторий
     * @return - возвращает dictonary_id или -1 в случае не существования словаря
     */
    public int getDictonaryId(Words words, DBConnection dbConnection) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String getDictionaryIdSQL = "select * from words where word_value=?";

            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getDictionaryIdSQL, params);

            for (HashMap<String, String> item : response)
                return Integer.parseInt(item.get("dictonary_id"));
        } catch (SQLException e) {
            System.out.println("Не удалось получить dictonary_id\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return -1;
    }

    /**
     * Достает groupId с базы данных
     * @param words - объект с информацией о слове. Обязательно нужно заполнить поле <i>wordValue</i>
     * @param dbConnection - репозиторий
     * @return - возвращает group_id или -1 в случае не существования словаря
     */
    public int getGroupId(Words words, DBConnection dbConnection) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String getGroupIdSQL = "select * from words where word_value=?";

            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getGroupIdSQL, params);

            for (HashMap<String, String> item : response)
                return Integer.parseInt(item.get("group_id"));
        } catch (SQLException e) {
            System.out.println("Не удалось получить id группы по слову\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return -1;
    }


    /**
     * <p>Меняет параметр word_translate</p>
     * @param words - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>, <i>wordTranslate</i>
     * @param dbConnection - репозиторий
     * @return - возвращает <i>true</i> в случае успеха или <i>false</i> в случае неудачи
     */
    public boolean editTranslation(Words words, DBConnection dbConnection) {
        if (isExist(words, dbConnection)) {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordTranslate()));
            params.add(createParams("string", words.getWordValue()));

            String translateValueSQL = "UPDATE words SET word_translate=? WHERE word_value=?";

            dbConnection.executeUpdateWithParams(translateValueSQL, params);

            return true;
        }

        return false;
    }


    /**
     * <p>Меняет параметр group_id</p>
     * @param words Объект с информацией о слове. Обязательные поля для заполнения: <i>groupId</i>, <i>wordValue</i>
     * @param dbConnection Репозиторий
     * @return - возвращает <i>true</i> в случае успеха или <i>false</i> в случае неудачи
     */
    public boolean editGroupId(Words words, DBConnection dbConnection){
        if (isExist(words, dbConnection)){
            List<HashMap<String,String>> params = new ArrayList<HashMap<String,String>>();
            params.add(createParams("int", String.valueOf(words.getGroupId())));
            params.add(createParams("string", words.getWordValue()));

            String groupIdValueSQL = "UPDATE words SET group_id=? WHERE word_value=?";


            dbConnection.executeUpdateWithParams(groupIdValueSQL, params);

            return true;
        }

        return false;

    }

    /**
     * <p>Удаляет слово из базы данных</p>
     * @param words - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @param dbConnection - репозиторий
     * @return - возвращает <i>true</i> при успеха и <i>false</i> при неудаче
     */
    public boolean deleteWord(Words words, DBConnection dbConnection) {
        if (isExist(words, dbConnection)) {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", words.getWordValue()));

            String deleteWordSQL = "delete from words where word_value=?";

            dbConnection.executeUpdateWithParams(deleteWordSQL, params);

            return true;
        }

        return false;
    }
}

