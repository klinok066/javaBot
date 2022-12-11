package org.matmech.db.bll;

import org.matmech.db.models.User;
import org.matmech.db.repository.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс-сервис, который работает с базой данных, а конкретно работает с таблицей <b>Users</b>
 * Каждый метод реализует какой-то нужный функционал.
 * На вход подается модель, а на выходе будет возвращенно какое-то значение
 */
public class UsersDBSource extends DBSource {
    /**
     * @param dbConnection - подключение к базе данных
     */
    public UsersDBSource(DBConnection dbConnection) {
        super(dbConnection);
    }

    /**
     * <p>Проверяет на существование какого-то пользователя</p>
     * <p>Перед этим нужно проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     * @return - возвращает существование пользователь в виде boolean
     */
    private boolean isExist(User user) throws SQLException {
        try {
            List<Map<String, String>> params = new ArrayList<Map<String, String>>();
            params.add(createParams("string", user.getTag()));

            String getWordsSQL = "select * from users where tag=?";

            List<Map<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            System.out.println("Не удалось проверить пользователя на существование\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /***
     * Возврат user_id с помощью тега
     * @param user - объект с информацией о пользователе. Обязательные параметры: <i>tag</i>
     * @return - возвращает userId
     */
    public int getUserIdByTag(User user) throws SQLException {
        int userId = -1;

        try {
            List<Map<String, String>> params = new ArrayList<Map<String, String>>();
            params.add(createParams("string", user.getTag()));

            String getWordsSQL = "select * from users where tag=?";

            List<Map<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            for (Map<String, String> item : response)
                userId = Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            System.out.println("Не удалось получить тег пользователя по id\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return userId;
    }

    /**
     * Добавляет нового пользователя в систему
     * @param user - объект с информацией о пользователе. Обязательные поля:
     *                <i>firstname</i>, <i>surname</i>, <i>tag</i>
     * @return - возвращает результат выполнения операции. <i>True</i> - если успешно, <i>False</i> - если не успешно
     */
    public boolean regUser(User user) {
        try {
            if (!isExist(user)) {
                List<Map<String, String>> params = new ArrayList<Map<String, String>>();
                params.add(createParams("string", user.getFirstname()));
                params.add(createParams("string", user.getSurname()));
                params.add(createParams("string", user.getTag()));

                // add user
                String insertUserSQL = "insert into users(firstname, surname, tag) values(?, ?, ?)";
                dbConnection.executeUpdateWithParams(insertUserSQL, params);

                return true;
            }

            return false;
        } catch (SQLException e) {
            System.out.println("Не удалось зарегистрировать пользователя\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Выводит всех пользователей из базы данных на экран</p>
     */
    public void getAllUsers() throws SQLException {
        try {
            String getUsersSQL = "select * from users";
            List<Map<String, String>> response = dbConnection.executeQuery(getUsersSQL);

            for (Map<String, String> item : response)
                System.out.println(item.get("id") + item.get("firstname") + item.get("surname") + item.get("tag"));

        } catch (SQLException e) {
            System.out.println("Не удалось получить всех пользователей\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Проверяет существование пользователя в системе
     * @param user - объект с информацией о пользователе. Обязательные поля: <i>tag</i>
     * @return - возвращает <i>true</i> - если пользователь существует, <i>false</i> - если пользователя не существует
     */
    public boolean userIsExist(User user) {
        try {
            return isExist(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
