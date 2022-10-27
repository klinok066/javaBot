package org.matmech.db.bll;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Groups extends BaseModelExtend {
    private String title;
    private int dictonaryId;

    public Groups(Connection connection) {
        super(connection);
    }

    public void setTitle(String group) {
        this.title = group;
    }

    public String getTitle() {
        return title;
    }

    public void setDictonaryId(int dictonaryId) {
        this.dictonaryId = dictonaryId;
    }

    public int getDictonaryId() {
        return dictonaryId;
    }

    /**
     * <p>Проверяет на существование группы в базе данных</p>
     * @param group
     */
    public boolean isExist(String group) {
        try {
            String groupsSQL = "select * from groups where title='" + group + "';";
            ResultSet response = statement.executeQuery(groupsSQL);

            return response.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Создает группу. Чтобы её создать, нужно перед этим инциализировать сеттерами поля <i>title</i> и <i>dictonaryId</i></p>
     */
    public String createGroup() {
        try {
            if (!isExist(title)) {
                String groupAddSQL = "insert into groups(title, dictonary_id) values('" + title + "', " + dictonaryId + ");";
                statement.executeUpdate(groupAddSQL);

                return "Группа успешно создана!";
            }

            return "Группа уже существует!";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Возвращает параметр <i>group_id</i> для какого-то конкретного словаря</p>
     * <p>Перед этим нужно инициализировать поле <i>dictonaryId</i> с помощью сеттера</p>
     */
    public int getGroupId() {
        int groupId = -1;

        try {
            String getGroupId = "select id from groups where dictonary_id=" + dictonaryId + ";";
            ResultSet response = statement.executeQuery(getGroupId);

            while (response.next()) {
                groupId = response.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return groupId;
    }
}
