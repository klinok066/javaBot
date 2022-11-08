package org.matmech.db.bll;

import org.matmech.db.models.Dictonary;
import org.matmech.db.repository.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DictonaryDBSource extends DBSource {
    /***
     * <p>Метод, который создает словарь какому-то пользователю</p>
     * @param dictonary - объект с информацией о словаре. Обязательные поля: <i>userId</i>
     * @return - возвращает <i>true</i> в случае успеха или <i>false</i> в случае неудачи
     */
    public boolean createDictonary(Dictonary dictonary, DBConnection dbConnection) {
        if (dictonary.getUserId() == -1)
            return false;

        List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
        params.add(createParams("int", Integer.toString(dictonary.getUserId())));

        String createDictionarySQL = "insert into dictonary(user_id) values(?)";
        dbConnection.executeUpdateWithParams(createDictionarySQL, params);

        return true;
    }

    /***
     * <p>Возвращает значение <i>dictonaryId</i> для какого-то конкретного пользователя</p>
     * @param dictonary - объект с информацией о словаре. Обязательные поля: <i>userId</i>
     * @return - возвращает либо <i>dictonaryId</i>, либо -1 в случае если словаря не существует
     */
    public int getDictonaryId(Dictonary dictonary, DBConnection dbConnection) throws SQLException {
        try {
            List<HashMap<String, String>> params = new ArrayList<HashMap<String, String>>();
            params.add(createParams("int", Integer.toString(dictonary.getUserId())));

            String getDictonaryIdSQL = "select id from dictonary where user_id=?";
            List<HashMap<String, String>> response = dbConnection.executeQueryWithParams(getDictonaryIdSQL, params);

            for (HashMap<String, String> item : response)
                return Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            System.out.println("Не удалось получить dictonary_id.\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return -1;
    }
}
