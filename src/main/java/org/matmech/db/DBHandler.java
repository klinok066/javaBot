package org.matmech.db;

import java.sql.*;
import org.matmech.db.bll.GroupsDBSource;
import org.matmech.db.bll.UsersDBSource;
import org.matmech.db.bll.DictonaryDBSource;
import org.matmech.db.bll.WordsDBSource;
import org.matmech.db.models.Users;
import org.matmech.db.models.Groups;
import org.matmech.db.models.Dictonary;
import org.matmech.db.models.Words;
import org.matmech.db.repository.DBConnection;

public class DBHandler {
    private DBConnection dbConnection = null;
    private UsersDBSource usersDBSource;
    private GroupsDBSource groupsDBSource;
    private DictonaryDBSource dictonaryDBSource;
    private WordsDBSource wordsDBSource;

    public DBHandler(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        dbConnection = new DBConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        usersDBSource = new UsersDBSource();
        groupsDBSource = new GroupsDBSource();
        dictonaryDBSource = new DictonaryDBSource();
        wordsDBSource = new WordsDBSource();
    }

    public void setDbConnection(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        dbConnection = new DBConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    /**
     * <p>Добавляет нового пользователя в базу данных</p>
     *
     * @param firstname - имя человека
     * @param surname - фамилия человека
     * @param tag - username человека
     */
    public String usersInsert(String firstname, String surname, String tag) {
        Users user = new Users();
        Dictonary dictonary = new Dictonary();

        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setTag(tag);

        return usersDBSource.regUser(user, dictonary, dictonaryDBSource, dbConnection);
    }

    /**
     * <p>Выводите всех пользователей из базы данных</p>
     */
    public void getAllUsers() {
        try {
            usersDBSource.getAllUsers(dbConnection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Добавляет слово в базу данных</p>
     *
     * @param wordValue - слово, переданное в параметрах
     * @param wordTranslate - перевод этого слова
     * @param group - параметр, который означает
     * @param tag - username человека
     */
    public String wordAdd(String wordValue, String wordTranslate, String group, String tag) {
        try {
            Words words = new Words();
            Users users = new Users();
            Dictonary dictonary = new Dictonary();
            Groups groups = new Groups();

            words.setWordTranslate(wordTranslate);
            words.setWordValue(wordValue);

            users.setTag(tag);

            dictonary.setUserId(usersDBSource.getUserIdByTag(users, dbConnection));
            words.setDictonaryId(dictonaryDBSource.getDictonaryId(dictonary, dbConnection));

            groups.setTitle(group);
            groups.setDictonaryId(dictonaryDBSource.getDictonaryId(dictonary, dbConnection));

            groupsDBSource.createGroup(groups, dbConnection);

            words.setGroupId(groupsDBSource.getGroupId(groups, dbConnection));
            return wordsDBSource.wordAdd(words, dbConnection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Показать перевод слова</p>
     *
     * @param wordValue - слово, переданное в параметрах
     */
    public String translateWord(String wordValue) {
        Words words = new Words();

        words.setWordValue(wordValue);

        return wordsDBSource.translate(words, dbConnection);
    }

    /**
     * <p>Удаляет слово</p>
     *
     * @param wordValue - слово, переданное в параметрах
     */
    public String deleteWord(String wordValue) {
        Words words = new Words();

        words.setWordValue(wordValue);

        return wordsDBSource.deleteWord(words, dbConnection);
    }
}
