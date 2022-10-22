package org.matmech.db;

import org.matmech.db.bll.Dictonary;
import org.matmech.db.bll.Groups;
import org.matmech.db.bll.Users;
import org.matmech.db.bll.Words;

import java.sql.*;

public class DBHandler {
    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;
    private Connection connection = null;
    private Users users;
    private Groups groups;
    private Dictonary dictonary;
    private Words words;

    public DBHandler(String DB_URL, String DB_USERNAME, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USERNAME = DB_USERNAME;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    /**
     * <p>Соединяемся с базой данных и настраеваем этот DBHandler</p>
     */
    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            dictonary = new Dictonary(connection);
            users = new Users(connection, dictonary);
            groups = new Groups(connection);
            words = new Words(connection);
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Добавляет нового пользователя в базу данных</p>
     *
     * @param firstname
     * @param surname
     * @param tag
     */
    public String usersInsert(String firstname, String surname, String tag) {
        users.setFirstname(firstname);
        users.setSurname(surname);
        users.setTag(tag);

        return users.regUser();
    }

    /**
     * <p>Выводите всех пользователей из базы данных</p>
     */
    public void getAllUsers() {
        users.getAllUsers();
    }

    /**
     * <p>Добавляет слово в базу данных</p>
     *
     * @param wordValue
     * @param wordTranslate
     * @param group
     * @param tag
     */
    public String wordAdd(String wordValue, String wordTranslate, String group, String tag) {
        words.setWordTranslate(wordTranslate);
        words.setWordValue(wordValue);

        users.setTag(tag);
        dictonary.setUserId(users.getUserIdByTag());
        words.setDictonaryId(dictonary.getDictonaryId());

        groups.setTitle(group);
        groups.setDictonaryId(dictonary.getDictonaryId());

        groups.createGroup();

        words.setGroupId(groups.getGroupId());
        return words.wordAdd();
    }

    public String translateWord(String wordValue) {
        words.setWordValue(wordValue);
        return words.translate();
    }

    /**
     * Закрывает соединение с базой данных
     */
    public void close() {
        try {
            System.out.println("Connection closed");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
