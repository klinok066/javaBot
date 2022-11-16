package org.matmech.contextManager;

import org.checkerframework.checker.units.qual.C;
import org.matmech.cache.Cache;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.paramsHandler.ParamsHandler;

public class ContextManager {
    final private Cache cache;
    final private ParamsHandler paramsHandler;

    /**
     * Возвращает название контекста
     * @param message - сообщение, которое отправил
     * @return - возвращает контекст
     */
    private String getContext(String message) {
        return switch (message) {
            case "/test" -> "testing";
            default -> null;
        };
    }

    public ContextManager(Cache cache, DBHandler dbHandler) {
        this.cache = cache;
        paramsHandler = new ParamsHandler(cache, dbHandler);
    }

    /**
     * Определяет контекст общения с пользователем. Назначаем новый контекст
     * @param message - сообщение пользователя
     * @param info - информация о пользователе
     */
    public String detectContext(String message, DataSaver info) {
        final long CHAT_ID = info.getChatId();

        if (!cache.isBusy(CHAT_ID))
            cache.setProcessName(CHAT_ID, getContext(message));

        return paramsHandler.handler(CHAT_ID, message);
    }
}
