package org.matmech.db.bll;

import org.matmech.db.models.Stat;
import org.matmech.db.repository.DBConnection;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Прослойка, которая работает с таблицей Stats
 */
public class StatsDBSource extends DBSource {
    /**
     * @param dbConnection - подключение к базе данных
     */
    public StatsDBSource(DBConnection dbConnection) {
        super(dbConnection);
    }

    /**
     * Добавляет результат теста
     * @param stat - объект Stat (все поля должны быть заполнены)
     */
    public void addStat(Stat stat) {
        List<Map<String, String>> params = new ArrayList<Map<String, String>>();
        params.add(createParams("int", stat.getUserId().toString()));
        params.add(createParams("string", stat.getCompleteDate()));
        params.add(createParams("int", stat.getResult().toString()));

        String addStatSQL = "insert into stats(user_id, complete_date, result) values(?, ?, ?)";

        dbConnection.executeUpdateWithParams(addStatSQL, params);
    }

    /**
     * Получение статистики для пользователя
     * @param stat - объект Stat: достаточно заполнить только поле
     * @return - возвращает всю статистику по пользователю
     */
    public List<Stat> getAllStat(Stat stat) {
        try {
            List<Map<String, String>> params = new ArrayList<Map<String, String>>();
            params.add(createParams("int", stat.getUserId().toString()));

            String getStatSQL = "select * from stats where user_id=?";

            List<Map<String, String>> response = dbConnection.executeQueryWithParams(getStatSQL, params);

            List<Stat> result = new ArrayList<Stat>();

            for (Map<String, String> item : response) {
                Stat resultItem = new Stat();
                resultItem.setResult(Integer.parseInt(item.get("result")));
                resultItem.setUserId(Integer.parseInt(item.get("id")));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                resultItem.setCompleteDate(dateFormat.parse(item.get("complete_date")));

                result.add(resultItem);
            }

            return result;
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
