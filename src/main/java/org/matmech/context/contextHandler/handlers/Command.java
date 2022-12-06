package org.matmech.context.contextHandler.handlers;

import org.matmech.context.Context;
import org.matmech.dataSaver.DataSaver;

/**
 * Описывает структуру обработчиков основного функционала команд
 */
public interface Command {
    /**
     * Главный метод, который запускает обработку команды
     * @param context - информация о всех контекстов команд для всех пользователей
     * @param info - объект DataSaver с информацией о пользователе
     * @return - возвращает сообщение для пользователя соответствующее
     */
    String handle(Context context, DataSaver info);
}
