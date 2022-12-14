package org.matmech.context.contextHandler.handlers.GetGroup;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Command;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetGroup implements Command {
    private List<String> params;
    private DBHandler db;

    public GetGroup(DBHandler db){
        this.db = db;
    };

    /**
     * Главный метод, который запускает обработку контекста
     *
     * @param context - информация о всех контекстов для всех пользователей
     * @param info    - объект DataSaver с информацией о пользователе
     * @return - возвращает сообщение для пользователя соответствующее
     */
    @Override
    public List<String> handle(Context context, UserData info) {
        try {
            final long CHAT_ID = info.getChatId();
            Map<String, String> params = context.getParams(CHAT_ID);
            return List.of(db.getGroup(params.get("word")));
        } finally {
            context.clear(info.getChatId());
        }
    }
}
