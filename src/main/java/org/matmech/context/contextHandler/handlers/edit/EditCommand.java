package org.matmech.context.contextHandler.handlers.edit;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Command;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;

import java.util.List;
import java.util.Map;

/**
 * Команда /edit
 */
public class EditCommand implements Command {
    private DBHandler db;
    public EditCommand(DBHandler db){
        this.db = db;
    }

    /**
     * Главный метод, который запускает обработку команды
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
            return List.of(db.edit(params.get("word"), params.get("wordParam"), params.get("paramValue")));
        } finally {
            context.clear(info.getChatId());
        }

    }
}
