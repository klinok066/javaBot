package org.matmech.context.contextHandler.handlers.startCommand;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Command;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;

import java.util.List;

public class StartCommandCommand implements Command {
    private DBHandler db;
    public StartCommandCommand(DBHandler db){
        this.db = db;
    }

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
            return List.of(db.usersInsert(info.getFirstname(), info.getSurname(), info.getTag()));
        } finally {
            context.clear(info.getChatId());
        }
    }
}