package org.matmech.db.bll;

import org.matmech.db.models.Groups;
import org.matmech.db.repository.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupsDBSource extends DBSource {
    /**
     * Проверка на существование группы по названию
     * @param groups - объект с информацией о группе. Обязательно должен быть заполнен параметр <i>title</i>
     * @param dbConnection - репозиторий
     * @return - возвращает результат работы метода. <i>True</i> - если успешно, <i>False</i> - если не успешно
     */
    private boolean isExist(Groups groups, DBConnection dbConnection) throws SQLException {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", groups.getTitle()));

            String groupsSQL = "select * from groups where title=?";
            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(groupsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создает группу для какого-то пользователя
     * @param groups - объект с информацией о группы. Обязательно должны быть заполнены поля
     *                 <i>dictonaryId</i> и <i>title</i>
     * @param dbConnection - репозиторий
     * @return - возвращает либо <i>true</i> при успешно выполнении функции, либо <i>false</i> при неудаче
     */
    public boolean createGroup(Groups groups, DBConnection dbConnection) {
        try {
            if (!isExist(groups, dbConnection)) {
                ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", groups.getTitle()));
                params.add(createParams("int", Integer.toString(groups.getDictonaryId())));

                String groupAddSQL = "insert into groups(title, dictonary_id) values(?, ?)";
                dbConnection.executeUpdateWithParams(groupAddSQL, params);

                return true;
            }

            return false;
        } catch (SQLException e) {
            System.out.println("Не удалось создать группу\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * Возвращает groupId по id словаря и названию группы
     * @param groups - объект с информацией о группе (должны быть заполнены значения <i>dictonaryId</i> и <i>title</i>)
     * @param dbConnection - репозиторий
     * @return - возвращает либо значение group_id, либо -1 в случае если группа не нашлась
     */
    public int getGroupId(Groups groups, DBConnection dbConnection) throws SQLException {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", String.valueOf(groups.getDictonaryId())));
            params.add(createParams("string", groups.getTitle()));

            String getGroupIdSQL = "select * from groups where dictonary_id=? and title=?";
            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getGroupIdSQL, params);

            for (HashMap<String, String> item : response)
                return Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            System.out.println("Не удалось получить id группы\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return -1;
    }

    /**
     * Возвращает название группы по groupId
     * @param groupId - id словаря
     * @param dbConnection - репозиторий
     * @return - возвращает значение поля title в виде строчки, либо null при неудаче
     */
    public String getGroupTitle(int groupId, DBConnection dbConnection) throws SQLException {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", Integer.toString(groupId)));

            String getGroupTitleSQL = "select title from groups where id=?";
            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getGroupTitleSQL, params);

            for (HashMap<String, String> item : response)
                return item.get("title");
        } catch (SQLException e) {
            System.out.println("Не удалось получить заголовок группы\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }
}
