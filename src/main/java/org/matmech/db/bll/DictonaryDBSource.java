package org.matmech.db.bll;

import org.matmech.db.models.Dictonary;
import org.matmech.db.repository.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DictonaryDBSource {
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

    /***
     * <p>Метод, который создает словарь какому-то пользователю</p>
     * <p>Перед этим надо инициализировать поле <i>userId</i> с помощью сеттера</p>
     */
    public void createDictonary(Dictonary dictonary, DBConnection dbConnection) {
        if (dictonary.getUserId() == -1) {
            System.out.println("UserId equals null");
            return;
        }

        ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
        params.add(createParams("int", Integer.toString(dictonary.getUserId())));

        String createDictionarySQL = "insert into dictonary(user_id) values(?)";
        dbConnection.executeUpdateWithParams(createDictionarySQL, params);
    }

    /***
     * <p>Возвращает значение <i>dictonaryId</i> для какого-то конкретного пользователя</p>
     * <p>Перед этим надо инициализировать поле <i>userId</i> с помощью сеттера</p>
     */
    public int getDictonaryId(Dictonary dictonary, DBConnection dbConnection) throws SQLException {
        int dictonaryId = -1;

        try {
            ArrayList<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", Integer.toString(dictonary.getUserId())));

            String getDictonaryIdSQL = "select id from dictonary where user_id=?";
            ArrayList<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getDictonaryIdSQL, params);

            for (HashMap<String, String> item : response)
                dictonaryId = Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dictonaryId;
    }
}
