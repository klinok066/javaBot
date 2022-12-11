package org.matmech.db.bll;

import org.matmech.db.models.Dictionary;
import org.matmech.db.repository.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс-сервис, который работает с базой данных, а конкретно работает с таблицей <b>Dictonary</b>
 * Каждый метод реализует какой-то нужный функционал.
 * На вход подается модель, а на выходе будет возвращенно какое-то значение
 */
public class DictonaryDBSource extends DBSource {
    /**
     * @param dbConnection - подключение к базе данных
     */
    public DictonaryDBSource(DBConnection dbConnection) {
        super(dbConnection);
    }

    /***
     * <p>Метод, который создает словарь какому-то пользователю</p>
     * @param dictionary - объект с информацией о словаре. Обязательные поля: <i>userId</i>
     * @return - возвращает <i>true</i> в случае успеха или <i>false</i> в случае неудачи
     */
    public boolean createDictonary(Dictionary dictionary) {
        if (dictionary.getUserId() == -1)
            return false;

        List<Map<String, String>> params = new ArrayList<Map<String, String>>();
        params.add(createParams("int", Integer.toString(dictionary.getUserId())));

        String createDictionarySQL = "insert into dictonary(user_id) values(?)";
        dbConnection.executeUpdateWithParams(createDictionarySQL, params);

        return true;
    }

    /***
     * <p>Возвращает значение <i>dictonaryId</i> для какого-то конкретного пользователя</p>
     * @param dictionary - объект с информацией о словаре. Обязательные поля: <i>userId</i>
     * @return - возвращает либо <i>dictonaryId</i>, либо -1 в случае если словаря не существует
     */
    public int getDictonaryId(Dictionary dictionary) throws SQLException {
        try {
            List<Map<String, String>> params = new ArrayList<Map<String, String>>();
            params.add(createParams("int", Integer.toString(dictionary.getUserId())));

            String getDictonaryIdSQL = "select id from dictonary where user_id=?";
            List<Map<String, String>> response = dbConnection.executeQueryWithParams(getDictonaryIdSQL, params);

            for (Map<String, String> item : response)
                return Integer.parseInt(item.get("id"));
        } catch (SQLException e) {
            System.out.println("Не удалось получить dictonary_id.\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return -1;
    }
}
