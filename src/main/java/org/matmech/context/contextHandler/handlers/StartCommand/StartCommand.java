package org.matmech.context.contextHandler.handlers.StartCommand;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.Handler;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;

public class StartCommand implements Handler {
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
    public String handle(Context context, DataSaver info) {
        return db.usersInsert(info.getFirstname(), info.getSurname(), info.getTag());
    }
}
