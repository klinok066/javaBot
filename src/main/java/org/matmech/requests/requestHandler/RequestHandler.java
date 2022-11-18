package org.matmech.requests.requestHandler;

import org.matmech.cache.Cache;
import org.matmech.contextManager.ContextManager;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.paramsHandler.ParamsHandler;
import org.matmech.requests.requestsLogic.RequestsLogic;

import java.util.ArrayList;
import java.util.List;


// все методы с выводом текста надо убрать в отдельный класс

public class RequestHandler {
    private final ContextManager contextManager;
    private final RequestsLogic requestsLogic;

    private boolean isCmd(String command) {
        return command.charAt(0) == '/';
    }

    private String toAnswer(String messageString, DataSaver info) { // просто ответ на обычные сообщения
        return switch (messageString.toLowerCase()) {
            case "hello" -> "Hello, " + info.getFirstname();
            default -> requestsLogic.toDefaultAnswer();
        };
    }

    public RequestHandler(DBHandler db, Cache cache) {
        this.requestsLogic = new RequestsLogic(db);
        this.contextManager = new ContextManager(cache, db);
    }

    public String processCmd(String message, DataSaver info) { // подумать над архитектурой этого класса
        String authentication = requestsLogic.authentication(info);

        if (requestsLogic.authentication(info) != null)
            return authentication;

        if (isCmd(message))
            return contextManager.detectContext(message, info);

        return toAnswer(message, info);
    }
}
