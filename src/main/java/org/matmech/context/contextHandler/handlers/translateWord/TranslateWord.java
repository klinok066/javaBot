package org.matmech.context.contextHandler.handlers.translateWord;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Command;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;

import java.util.List;

public class TranslateWord implements Command {
    private DBHandler db;

    public TranslateWord(DBHandler db){
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
        final long CHAT_ID = info.getChatId();
        HashMap<String, String> params = context.getParams(CHAT_ID);

        context.clear(info.getChatId());

        return List.of(db.translateWord(params.get("word")));
    }
}
