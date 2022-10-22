package org.matmech.db.bll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dictonary extends BaseModelExtend {
    private int userId = -1;

    public Dictonary(Connection connection) {
        super(connection);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    /**
     * <p>Метод, который создает словарь какому-то пользователю</p>
     * <p>Перед этим надо инициализировать поле <i>userId</i> с помощью сеттера</p>
     */
    public void createDictonary() {
        try {
            if (userId == -1) {
                System.out.println("UserId equals null");
                return;
            }

            String createDictionarySQL = "insert into dictonary(user_id) values(" + userId + ");";
            statement.executeUpdate(createDictionarySQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Возвращает значение <i>dictonaryId</i> для какого-то конкретного пользователя</p>
     * <p>Перед этим надо инициализировать поле <i>userId</i> с помощью сеттера</p>
     */
    public int getDictonaryId() {
        int dictonaryId = -1;

        try {
            String getDictonaryIdSQL = "select id from dictonary where user_id=" + userId + ";";
            ResultSet response = statement.executeQuery(getDictonaryIdSQL);

            while (response.next()) {
                dictonaryId = response.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dictonaryId;
    }
}
