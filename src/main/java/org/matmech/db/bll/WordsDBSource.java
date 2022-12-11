package org.matmech.db.bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.matmech.db.models.Word;
import org.matmech.db.repository.DBConnection;

/**
 * Класс-сервис, который работает с базой данных, а конкретно работает с таблицей <b>Words</b>
 * Каждый метод реализует какой-то нужный функционал.
 * На вход подается модель, а на выходе будет возвращенно какое-то значение
 */
public class WordsDBSource extends DBSource {
    /**
     * @param dbConnection - подключение к базе данных
     */
    public WordsDBSource(DBConnection dbConnection) {
        super(dbConnection);
    }

    /**
     * <p>Проверяет на существование какого-то слова</p>
     * @param word - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @return - возвращает существование слова в бд
     */
    private boolean isExist(Word word) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", word.getWordValue()));

            String getWordsSQL = "select * from words where word_value=?";

            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            System.out.println("Не удалось проверить на существование слово\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean WordIsExist(Words words, DBConnection dbConnection){
        return isExist(words, dbConnection);
    }

    /**
     * <p>Добавляет слово в базу данных</p>
     * @param word - объект с информацией о слове. Обязательные поля для заполнения:
     *                                                  <i>dictonaryId</i>
     *                                                  <i>groupId</i>
     *                                                  <i>wordValue</i>
     *                                                  <i>wordTranslate</i>
     * @return - возвращает <i>true</i> при успешном выполнении метода и <i>false</i> при случае, когда слово уже существует
     */
    public boolean wordAdd(Word word) {
        if (isExist(word))
            return false;

        List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
        params.add(createParams("int", Integer.toString(word.getDictonaryId())));
        params.add(createParams("int", Integer.toString(word.getGroupId())));
        params.add(createParams("string", word.getWordValue()));
        params.add(createParams("string", word.getWordTranslate()));

        String wordAddSQL = "insert into words(dictonary_id, group_id, word_value, word_translate) values(?, ?, ?, ?)";
        dbConnection.executeUpdateWithParams(wordAddSQL, params);

        return true;
    }

    /**
     * <p>Достает слово из базы данных</p>
     * @param word - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @return - возвращает строчку с ошибкой или со значением поля translate (в красивом виде)
     */
    public String translate(Word word) {
        try {
            if (isExist(word)) {
                List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", word.getWordValue()));

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
     * @param word - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @return - возвращает dictonary_id или -1 в случае не существования словаря
     */
    public int getDictonaryId(Word word) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", word.getWordValue()));

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
     * @param word - объект с информацией о слове. Обязательно нужно заполнить поле <i>wordValue</i>
     * @return - возвращает group_id или -1 в случае не существования словаря
     */
    public int getGroupId(Word word) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", word.getWordValue()));

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
     * @param word - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>, <i>wordTranslate</i>
     * @return - возвращает <i>true</i> в случае успеха или <i>false</i> в случае неудачи
     */
    public boolean editTranslation(Word word) {
        if (isExist(word)) {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", word.getWordTranslate()));
            params.add(createParams("string", word.getWordValue()));

            String translateValueSQL = "UPDATE words SET word_translate=? WHERE word_value=?";

            dbConnection.executeUpdateWithParams(translateValueSQL, params);

            return true;
        }

        return false;
    }


    /**
     * <p>Меняет параметр group_id</p>
     * @param word Объект с информацией о слове. Обязательные поля для заполнения: <i>groupId</i>, <i>wordValue</i>
     * @return - возвращает <i>true</i> в случае успеха или <i>false</i> в случае неудачи
     */
    public boolean editGroupId(Word word){
        if (isExist(word)){
            List<HashMap<String,String>> params = new ArrayList<HashMap<String,String>>();
            params.add(createParams("int", String.valueOf(word.getGroupId())));
            params.add(createParams("string", word.getWordValue()));

            String groupIdValueSQL = "UPDATE words SET group_id=? WHERE word_value=?";


            dbConnection.executeUpdateWithParams(groupIdValueSQL, params);

            return true;
        }

        return false;

    }

    /**
     * <p>Удаляет слово из базы данных</p>
     * @param word - объект с информацией о слове. Обязательные поля для заполнения: <i>wordValue</i>
     * @return - возвращает <i>true</i> при успехе и <i>false</i> при неудаче
     */
    public boolean deleteWord(Word word) {
        if (isExist(word)) {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", word.getWordValue()));

            String deleteWordSQL = "delete from words where word_value=?";

            dbConnection.executeUpdateWithParams(deleteWordSQL, params);

            return true;
        }

        return false;
    }

    /**
     * Возвращает случайное слово для какого-то пользователя по группе
     * @param word - модель Words. Обязательные поля: <i>dictonaryId</i> и <i>group_id</i>
     * @return - возвращает случайное слово
     */
    public String getRandomWordByGroup(Word word) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", String.valueOf(word.getDictonaryId())));
            params.add(createParams("int", String.valueOf(word.getGroupId())));

            String getRandomWord = "select word_value from words where dictonary_id=? and group_id=? order by random() limit 1";

            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getRandomWord, params);

            if (response.size() == 0)
                return null;

            for (HashMap<String, String> item : response)
                return item.get("word_value");
        } catch (SQLException e) {
            System.out.println("Не удалось получить случайное слово по словарю и группе\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Возвращает случайное слово и его перевод для какого-то пользователя по ВСЕМ группам
     * @param word - модель Words. Обязательные поля: <i>dictonaryId</i>
     * @return - возвращает случайное слово
     */
    public String getRandomWord(Word word) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", String.valueOf(word.getDictonaryId())));

            String getRandomWord = "select word_value from words where dictonary_id=? order by random() limit 1";

            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getRandomWord, params);

            if (response.size() == 0)
                return null;

            for (HashMap<String, String> item : response)
                return item.get("word_value");
        } catch (SQLException e) {
            System.out.println("Не удалось получить случайное слово по словарю\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Возвращает количество слов в словаре у пользователя
     * @param word - модель Words. Обязательные поля: <i>dictonaryId</i>
     * @return - количество слов
     */
    public int getCountWords(Word word) {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", String.valueOf(word.getDictonaryId())));

            String getRandomWord = "select * from words where dictonary_id=?";

            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getRandomWord, params);

            return response.size();
        } catch (SQLException e) {
            System.out.println("Не удалось получить количество слов для пользователя\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

