package org.matmech.context.contextHandler;

import org.matmech.context.Context;
import org.matmech.context.contextHandler.handlers.usuallyMessage.UsuallyMessage;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.context.contextHandler.handlers.testContext.TestContext;

/**
 * В этом классе исполняется основной код контекстов после получения и валидации всех параметров
 */
public class ContextHandler {
    final private TestContext TEST_CONTEXT;
    final private UsuallyMessage USUALLY_MESSAGE;

    public ContextHandler(DBHandler db) {
        TEST_CONTEXT = new TestContext(db);
        USUALLY_MESSAGE = new UsuallyMessage();
    }

    /**
     * Вызывает нужную функцию, которая обрабатывает соответствующий контекст
     * @param context - информация о всех контекстов для всех пользователей
     * @param info - объект DataSaver с информацией о пользователе
     * @return - возвращает соответствующее сообщение для пользователя
     */
    public String handle(Context context, DataSaver info, String message) {
        return switch (context.getParams(info.getChatId()).get("processName")) {
            case "testing" -> TEST_CONTEXT.handle(context, info);
            case null -> USUALLY_MESSAGE.handle(info, message);
            default -> throw new IllegalStateException("Unexpected value: " + context.getParams(info.getChatId()).get("processName"));
        };
    }
}
