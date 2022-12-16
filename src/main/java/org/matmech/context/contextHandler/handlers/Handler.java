package org.matmech.context.contextHandler.handlers;

import org.matmech.context.Context;
import org.matmech.userData.UserData;

/**
 * Описывает структуру обработчиков основного функционала контекстов
 */
public interface Handler {
    /**
     * Главный метод, который запускает обработку контекста
     * @param context - информация о всех контекстов для всех пользователей
     * @param info - объект DataSaver с информацией о пользователе
     * @return - возвращает сообщение для пользователя соответствующее
     */
    String handle(Context context, UserData info);
}
