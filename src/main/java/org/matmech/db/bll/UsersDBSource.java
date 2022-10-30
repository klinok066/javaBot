package org.matmech.db.bll;

import org.matmech.db.models.Dictonary;
import org.matmech.db.models.Users;
import org.matmech.db.repository.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UsersDBSource {
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
     * <p>Проверяет на существование какого-то пользователя</p>
     * <p>Перед этим нужно проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     */
    private boolean isExist(Users users, DBConnection dbConnection) throws SQLException {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", users.getTag()));

            String getWordsSQL = "select * from users where tag=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Возврат user_id с помощью тега
     * @param users - объект с информацией о пользователе
     * @param dbConnection - объект базы данных
     */
    public int getUserIdByTag(Users users, DBConnection dbConnection) throws SQLException {
        int userId = -1;

        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", users.getTag()));

            String getWordsSQL = "select * from users where tag=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            System.out.println(response.size() != 0);

            for (HashMap<String, String> item : response)
                userId = Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userId;
    }

    /**
     * <p>Добавляет нового пользователя в систему</p>
     */
    public String regUser(Users users, Dictonary dictonary, DictonaryDBSource dictonaryDBSource, DBConnection dbConnection) {
        try {
            if (!isExist(users, dbConnection)) {
                ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", users.getFirstname()));
                params.add(createParams("string", users.getSurname()));
                params.add(createParams("string", users.getTag()));

                // add user
                String insertUserSQL = "insert into users(firstname, surname, tag) values(?, ?, ?)";
                dbConnection.executeUpdateWithParams(insertUserSQL, params);

                // create dictonary
                dictonary.setUserId(getUserIdByTag(users, dbConnection));
                dictonaryDBSource.createDictonary(dictonary, dbConnection);

                return "Привет, добро пожаловать в нашего бота для изучения английского языка!\n"
                        + "Список всех команд можете посмотреть с помощью /help";
            }

            return "Вы уже зарегистрированны в нашей системе";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Выводит всех пользователей из базы данных на экран</p>
     */
    public void getAllUsers(DBConnection dbConnection) throws SQLException {
        try {
            String getUsersSQL = "select * from users";
            ArrayList<HashMap<String, String>> response = dbConnection.executeQuery(getUsersSQL);

            for (HashMap<String, String> item : response)
                System.out.println(item.get("id") + item.get("firstname") + item.get("surname") + item.get("tag"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
