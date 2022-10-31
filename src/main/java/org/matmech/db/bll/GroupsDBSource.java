package org.matmech.db.bll;

import org.matmech.db.models.Groups;
import org.matmech.db.repository.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupsDBSource {
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

    private boolean isExist(Groups groups, DBConnection dbConnection) throws SQLException {
        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("string", groups.getTitle()));

            String groupsSQL = "select * from groups where title=?";
            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(groupsSQL, params);

            return response.size() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String createGroup(Groups groups, DBConnection dbConnection) {
        try {
            if (!isExist(groups, dbConnection)) {
                ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
                params.add(createParams("string", groups.getTitle()));
                params.add(createParams("int", Integer.toString(groups.getDictonaryId())));

                String groupAddSQL = "insert into groups(title, dictonary_id) values(?, ?)";
                dbConnection.executeUpdateWithParams(groupAddSQL, params);

                return "Группа успешно создана!";
            }

            return "Группа уже существует!";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getGroupId(Groups groups, DBConnection dbConnection) throws SQLException {
        int groupId = -1;

        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", Integer.toString(groups.getDictonaryId())));
            params.add(createParams("string", groups.getTitle()));

            String getGroupIdSQL = "select id from groups where dictonary_id=? and where group_title=?";
            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getGroupIdSQL, params);

            for (HashMap<String, String> item : response)
                groupId = Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return groupId;
    }

    public String getGroupTitle(int DictonaryId, DBConnection dbConnection) throws SQLException {
        String groupTitle = "";

        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", Integer.toString(DictonaryId)));

            String getGroupTitleSQL = "select title from groups where dictonary_id=?";
            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getGroupTitleSQL, params);

            for (HashMap<String, String> item : response)
                groupTitle = item.get("title");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupTitle;
    }
}
