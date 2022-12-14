package org.matmech.context.contextManager;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.ContextHandler;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;
import org.matmech.params.Params;

import java.util.List;

/**
 * Класс, который обрабатывает контекст. Контекст = команда
 */
public class ContextManager { // убрать стоп операцию из параметров и добавить её суда (сделать через контекст для каждого пользователя)
    final private Context context;
    final private Params paramsHandler;
    final private ContextHandler contextHandler;

    /**
     * Метод проверяет, является ли сообщение командой
     * @param message - сообщение пользователя
     * @return - возвращает true, если является командной, и false, если не является
     */
    private boolean isCmd(String message) {
        return message.charAt(0) == '/';
    }

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
            case "/translate" -> "translating";
            case "/start" -> "starting";
            case "/get_group" -> "getGroup";
            case "/word_add" -> "wordAdd";
            case "/edit" -> "edit";
            case "/delete_word" -> "wordDelete";
            default -> null;
        };
    }

    public ContextManager(Context context, DBHandler dbHandler) {
        this.context = context;
        paramsHandler = new Params(dbHandler);
        contextHandler = new ContextHandler(dbHandler);
    }

    /**
     * Определяет контекст общения с пользователем. Назначаем новый контекст
     * @param message - сообщение пользователя
     * @param info - информация о пользователе
     * @return возвращает список сообщений для пользователя
     */
    public List<String> detectContext(String message, UserData info) {
        final long CHAT_ID = info.getChatId();
        final String CONTEXT = getContext(message);

        if (context.getParams(CHAT_ID) == null)
            context.setProcessName(CHAT_ID, null);

        if (CONTEXT == null && context.getParams(CHAT_ID).get("processName") == null) {
            if (!isCmd(message))
                return contextHandler.handle(context, info, message);

            return List.of(defaultAnswer());
        }

        if (!context.isBusy(CHAT_ID))
            context.setProcessName(CHAT_ID, CONTEXT);

        String paramsAnswer = paramsHandler.handler(context, CHAT_ID, message);

        if (paramsAnswer == null)
            return contextHandler.handle(context, info, message);

        return List.of(paramsAnswer);
    }
}
