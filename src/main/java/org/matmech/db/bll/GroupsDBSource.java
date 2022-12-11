package org.matmech.db.bll;

import org.matmech.db.models.Group;
import org.matmech.db.repository.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс-сервис, который работает с базой данных, а конкретно работает с таблицей <b>Groups</b>
 * Каждый метод реализует какой-то нужный функционал.
 * На вход подается модель, а на выходе будет возвращенно какое-то значение
 */
public class GroupsDBSource extends DBSource {
    /**
     * @param dbConnection - подключение к базе данных
     */
    public GroupsDBSource(DBConnection dbConnection) {
        super(dbConnection);
    }

    /**
     * Проверка на существование группы по названию
     * @param group - объект с информацией о группе. Обязательно должен быть заполнен параметр <i>title</i>
     * @return - возвращает результат работы метода. <i>True</i> - если успешно, <i>False</i> - если не успешно
     */
    private boolean isExist(Group group) throws SQLException {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", group.getTitle()));

            String groupsSQL = "select * from groups where title=?";
            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(groupsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создает группу для какого-то пользователя
     * @param group - объект с информацией о группы. Обязательно должны быть заполнены поля
     *                 <i>dictonaryId</i> и <i>title</i>
     * @return - возвращает либо <i>true</i> при успешно выполнении функции, либо <i>false</i> при неудаче
     */
    public boolean createGroup(Group group) {
        try {
            if (!isExist(group)) {
                ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", group.getTitle()));
                params.add(createParams("int", Integer.toString(group.getDictonaryId())));

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
     * @param group - объект с информацией о группе (должны быть заполнены значения <i>dictonaryId</i> и <i>title</i>)
     * @return - возвращает либо значение group_id, либо -1 в случае если группа не нашлась
     */
    public int getGroupId(Group group) throws SQLException {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", String.valueOf(group.getDictonaryId())));
            params.add(createParams("string", group.getTitle()));

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
     * @param group - объект с информацией о группах. Обязательные поля: <i>id</i>
     * @return - возвращает значение поля title в виде строчки, либо null при неудаче
     */
    public String getGroupTitle(Group group) throws SQLException {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", Integer.toString(group.getId())));

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

    /**
     * Проверяет существование группы по названию
     * @param group - объект с информацией о группе. Обязательные параметры: <i>title</i>
     * @return - возвращает <i>true</i>, если группа существует, и <i>false</i>, если группа не существует
     */
    public boolean groupIsExist(Group group) {
        try {
            return isExist(group);
        } catch (SQLException e) {
            System.out.println("Не удалось проверить существование группы\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
