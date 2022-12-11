package org.matmech.context.contextHandler.handlers.StartCommand;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Command;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;

import java.util.List;

public class StartCommand implements Command {
    private DBHandler db;
    public StartCommand(DBHandler db){
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
        return List.of(db.usersInsert(info.getFirstname(), info.getSurname(), info.getTag()));
    }
}