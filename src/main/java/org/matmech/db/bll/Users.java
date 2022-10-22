package org.matmech.db.bll;

import org.matmech.db.models.BaseModel;

import java.sql.*;

public class Users extends BaseModelExtend {
    private String firstname;
    private String surname;
    private String tag;
    private Dictonary dictonary;

    private boolean isExist() {
        try {
            String isExistSQL = "select * from users where tag='" + tag + "';";
            ResultSet response = statement.executeQuery(isExistSQL);

            return response.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserIdByTag() {
        int userId = -1;

        try {
            String getUserIdSQL = "select * from users where tag='" + tag + "'";
            ResultSet response = statement.executeQuery(getUserIdSQL);

            while (response.next()) {
                userId = response.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userId;
    }

    public Users(Connection connection, Dictonary dictonary) {
        super(connection);
        this.dictonary = dictonary;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getTag() {
        return this.tag;
    }

    /**
     * <p>Добавляет нового пользователя в систему</p>
     */
    public String regUser() {
        try {
            if (!isExist()) {
                // add user
                String insertUserSQL = "insert into users(firstname, surname, tag) values('" + firstname + "', '" + surname + "', '" + tag + "');";
                statement.executeUpdate(insertUserSQL);

                // create dictonary
                dictonary.setUserId(getUserIdByTag());
                dictonary.createDictonary();

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
    public void getAllUsers() {
        try {
            String getUsersSQL = "select * from users;";
            ResultSet response = statement.executeQuery(getUsersSQL);

            while (response.next()) {
                System.out.println(response.getString("id") + " " +
                                   response.getString("firstname") + " " +
                                   response.getString("surname") + " " +
                                   response.getString("tag"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
