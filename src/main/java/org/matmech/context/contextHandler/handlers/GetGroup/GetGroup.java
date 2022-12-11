package org.matmech.context.contextHandler.handlers.GetGroup;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Handler;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;

import java.util.HashMap;
import java.util.List;

public class GetGroup implements Handler {
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
    public String handle(Context context, DataSaver info) {
        final long CHAT_ID = info.getChatId();
        HashMap<String, String> params = context.getParams(CHAT_ID);


        return db.getGroup(params.get("group"));
    }


}
