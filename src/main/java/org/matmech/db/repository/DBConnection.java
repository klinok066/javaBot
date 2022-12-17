package org.matmech.db.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    private List<Map<String, String>> getDataFromResponse(ResultSet result) throws SQLException {
        try (result) {
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();

            while (result.next()) {
                int size = result.getMetaData().getColumnCount();
                Map<String, String> item = new HashMap<String, String>();

                for (int i = 0; i < size; i++) {
                    String columnName = result.getMetaData().getColumnName(i + 1);
                    String value = result.getString(columnName);
                    item.put(columnName, value);
                }

                data.add(item);
            }

            return data;
        }
    }

    /***
     * Метод подключается к базе данных (внутренний метод)
     */
    private void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к базе данных");
            throw new RuntimeException(e);
        }
    }

    /***
     * Закрываем соединение с базой данных (внутренний метод)
     */
    private void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Метод присваивает данные параметрам в PreparedStatement (внутренний метод)
     */
    private void setDataToPS(PreparedStatement preparedStatement, Map<String, String> item, int index) throws SQLException {
        String type = item.get("type");
        String value = item.get("value");

        switch (type.toLowerCase()) {
            case "int" -> preparedStatement.setInt(index + 1, Integer.parseInt(value));
            case "string" -> preparedStatement.setString(index + 1, value.toLowerCase());
            case "boolean" -> preparedStatement.setBoolean(index + 1, Boolean.getBoolean(value));
            case "date" -> preparedStatement.setDate(index + 1, Date.valueOf(value));
            default -> {
                System.out.println("Неправильный тип данных");
                throw new IllegalStateException("Unexpected value: " + type);
            }
        }
    }

    public DBConnection(String DB_URL, String DB_USER, String DB_PASSWORD) {
        this.DB_URL = DB_URL;
        this.DB_USER = DB_USER;
        this.DB_PASSWORD = DB_PASSWORD;
    }

    /***
     * Выполняет простой запрос без параметров в базу данных. Возвращает результат в ArrayList, где каждый элемент HashMap
     */
    public List<Map<String, String>> executeQuery(String sql) throws SQLException {
        try {
            connect();
            statement = connection.createStatement();

            return getDataFromResponse(statement.executeQuery(sql));
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос");
            throw new RuntimeException(e);
        } finally {
            statement.close();
            close();
        }
    }

    /***
     * Выполняет простой запрос на обновление без параметров в базу данных
     */
    public void executeUpdate(String sql) throws SQLException {
        try {
            connect();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос");
            throw new RuntimeException(e);
        } finally {
            statement.close();
            close();
        }
    }

    /***
     * Выполняет сложный запрос с параметрами в базу данных
     * @param sql - sql запрос
     * @param params - ArrayList, где каждый элемент это HashMap, с параметрами
     * HashMap состоит из двух полей:
     *  type: тип параметра
     *  value: сам параметр
     *
     * Доступные типы: int, string, date, boolean
     */
    public List<Map<String, String>> executeQueryWithParams(String sql, List<Map<String, String>> params) throws SQLException {
        try {
            connect();

            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                setDataToPS(preparedStatement, params.get(i), i);
            }

            return getDataFromResponse(preparedStatement.executeQuery());
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос!\n" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            preparedStatement.close();
            close();
        }
    }

    /***
     * Выполняет сложный запрос на обновление с параметрами в базу данных
     * @param sql - sql запрос
     * @param params - ArrayList, где каждый элемент это HashMap, с параметрами
     * HashMap состоит из двух полей:
     *  type: тип параметра
     *  value: сам параметр
     *
     * Доступные типы: int, string, date, boolean
     */
    public void executeUpdateWithParams(String sql, List<Map<String, String>> params) {
        try {
            connect();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++)
                setDataToPS(preparedStatement, params.get(i), i);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить запрос: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }
}
