package org.matmech.requests.requestHandler;

import org.matmech.context.Context;
import org.matmech.context.contextManager.ContextManager;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.requests.requestsLogic.RequestsLogic;


// все методы с выводом текста надо убрать в отдельный класс

public class RequestHandler {
    private final ContextManager contextManager;
    private final RequestsLogic requestsLogic;

    public RequestHandler(DBHandler db, Context cache) {
        this.requestsLogic = new RequestsLogic(db);
        this.contextManager = new ContextManager(cache, db);
    }

    public String processCmd(String message, DataSaver info) { // подумать над архитектурой этого класса
        String authentication = requestsLogic.authentication(info);

        if (requestsLogic.authentication(info) != null)
            return authentication;

        return contextManager.detectContext(message, info);
    }
}
