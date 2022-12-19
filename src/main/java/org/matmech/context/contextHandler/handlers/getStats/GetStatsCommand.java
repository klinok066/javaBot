package org.matmech.context.contextHandler.handlers.getStats;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Command;
import org.matmech.db.DBHandler;
import org.matmech.userData.UserData;

import java.util.List;
import java.util.Map;

/**
 * команда /get_stats
 */
public class GetStatsCommand implements Command {
    private DBHandler db;


    public GetStatsCommand(DBHandler db){this.db = db;};
    /**
     * Главный метод, который запускает обработку команды
     *
     * @param context - информация о всех контекстов команд для всех пользователей
     * @param info    - объект DataSaver с информацией о пользователе
     * @return - возвращает список сообщений для пользователя
     */
    @Override
    public List<String> handle(Context context, UserData info) {
        try{
            final long CHAT_ID = info.getChatId();
            Map<String, String> params = context.getParams(CHAT_ID);
            return List.of(db.getStats(params.get("yyyy-MM-dd")));
        }finally{
            context.clear(info.getChatId());
        }
    }
}
