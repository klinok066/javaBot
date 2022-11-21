package org.matmech.contextManager;

import org.matmech.paramsCache.ParamsCache;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.params.Params;

public class ContextManager {
    final private ParamsCache cache;
    final private Params paramsHandler;

    /**
     * Возвращает стандартный ответ на не определенную команду
     * @return - возвращает строку, содержащую стандартный ответ
     */
    private String defaultAnswer() {
        return "Простите, я не знаю такой команды\n" +
                "Если хотите узнать полных список команд, то напишите /help";
    }

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

    public ContextManager(ParamsCache cache, DBHandler dbHandler) {
        this.cache = cache;
        paramsHandler = new Params(cache, dbHandler);
    }

    /**
     * Определяет контекст общения с пользователем. Назначаем новый контекст
     * @param message - сообщение пользователя
     * @param info - информация о пользователе
     */
    public String detectContext(String message, DataSaver info) {
        final long CHAT_ID = info.getChatId();
        final String TAG = info.getTag();
        final String CONTEXT = getContext(message);

        if (CONTEXT == null && cache.getParams(CHAT_ID).get("processName") == null)
            return defaultAnswer();

        if (!cache.isBusy(CHAT_ID))
            cache.setProcessName(CHAT_ID, CONTEXT);

        return paramsHandler.handler(CHAT_ID, TAG, message);
    }
}
