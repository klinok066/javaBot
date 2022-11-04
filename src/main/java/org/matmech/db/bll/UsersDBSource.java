package org.matmech.db.bll;

import org.matmech.db.models.Dictonary;
import org.matmech.db.models.Users;
import org.matmech.db.repository.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UsersDBSource extends DBSource {
    /**
     * <p>Проверяет на существование какого-то пользователя</p>
     * <p>Перед этим нужно проинициализировать поле <i>wordValue</i> с помощью сеттера</p>
     * @return - возвращает существует пользователь в виде boolean
     */
    private boolean isExist(Users users, DBConnection dbConnection) throws SQLException {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", users.getTag()));

            String getWordsSQL = "select * from users where tag=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            System.out.println("Не удалось проверить пользователя на существование\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /***
     * Возврат user_id с помощью тега
     * @param users - объект с информацией о пользователе
     * @param dbConnection - объект базы данных
     * @return - возвращает userId
     */
    public int getUserIdByTag(Users users, DBConnection dbConnection) throws SQLException {
        int userId = -1;

        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", users.getTag()));

            String getWordsSQL = "select * from users where tag=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getWordsSQL, params);

            for (HashMap<String, String> item : response)
                userId = Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            System.out.println("Не удалось получить тег пользователя по id\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return userId;
    }

    /**
     * Добавляет нового пользователя в систему
     * @param users - объект с информацией о пользователе. Обязательные поля:
     *                <i>firstname</i>, <i>surname</i>, <i>tag</i>
     * @param dbConnection - репозиторий
     * @return - возвращает результат выполнения операции. <i>True</i> - если успешно, <i>False</i> - если не успешно
     */
    public boolean regUser(Users users, DBConnection dbConnection) {
        try {
            if (!isExist(users, dbConnection)) {
                ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", users.getFirstname()));
                params.add(createParams("string", users.getSurname()));
                params.add(createParams("string", users.getTag()));

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
    public void getAllUsers(DBConnection dbConnection) throws SQLException {
        try {
            String getUsersSQL = "select * from users";
            ArrayList<HashMap<String, String>> response = dbConnection.executeQuery(getUsersSQL);

            for (HashMap<String, String> item : response)
                System.out.println(item.get("id") + item.get("firstname") + item.get("surname") + item.get("tag"));

        } catch (SQLException e) {
            System.out.println("Не удалось получить всех пользователей\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Устанавливает режим тестирования для пользователя
     * @param users - объект с информацией о пользователе. Обязательные поля: <i>tag</i>, <i>testMode</i>
     * @param dbConnection - репозиторий
     * @return - возвращает строчку с готовым текстом о результате выполнения операции
     */
    public String setMode(Users users, DBConnection dbConnection) {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", String.valueOf(users.getTestMode())));
            params.add(createParams("string", users.getTag()));

            if (this.getMode(users, dbConnection).equals(users.getTestMode()))
                return "У вас уже итак стоит этот режим, вы можете поменять его на другой";

            String setTestModeSQL = "update users set test_mode=? where tag=?";

            dbConnection.executeUpdateWithParams(setTestModeSQL, params);

            return "Режим тестирования был изменен на " + users.getTestMode();
        } catch (Exception e) {
            System.out.println("Не удалось установить режим тестирования\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает режим тестирования из базы данных
     * @param users - объект с информацией о пользователе. Обязательные поля: <i>tag</i>
     * @param dbConnection - репозиторий
     * @return - возвращает значение поля test_mode в виде строки
     */
    public String getMode(Users users, DBConnection dbConnection) {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", users.getTag()));

            String getTestMode = "select test_mode from users where tag=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getTestMode, params);

            for (HashMap<String, String> item : response)
                return item.get("test_mode");
        } catch (SQLException e) {
            System.out.println("Не удалось получить режим тестирования\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Возвращает значение поля testing
     * @param users - объект с информацией о пользователе. Обязательные поля: <i>tag</i>
     * @param dbConnection - репозиторий
     * @return - возвращает поле testing в виде строки
     */
    public String getTesting(Users users, DBConnection dbConnection) {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", users.getTag()));

            String getTestingSQL = "select testing from users where tag=?";

            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getTestingSQL, params);

            for (HashMap<String, String> item : response)
                return item.get("testing");
        } catch (SQLException e) {
            System.out.println("Не удалось получить поле testing у пользователя");
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * Устанавливает значение полю testing
     * @param users - объект с информацией о пользователе. Обязательные поля: <i>testing</i>, <i>tag</i>
     * @param dbConnection - репозиторий
     * @return - возвращает результат выполнения операции. <i>True</i> - выполнилась успешно, и наоборот <i>False</i>
     */
    public boolean setTesting(Users users, DBConnection dbConnection) {
        try {
            if (isExist(users, dbConnection))
                return false;

            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", String.valueOf(users.getTesting())));
            params.add(createParams("string", users.getTag()));

            String setTestingSQL = "update users set testing=? where tag=?";

            dbConnection.executeUpdateWithParams(setTestingSQL, params);

            return true;
        } catch (SQLException e) {
            System.out.println("Не удалось установить значение полю testing");
            throw new RuntimeException(e);
        }
    }
}
