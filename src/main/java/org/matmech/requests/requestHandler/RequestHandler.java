package org.matmech.requests.requestHandler;

import org.matmech.context.Context;
import org.matmech.context.contextManager.ContextManager;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;
import org.matmech.requests.requestsLogic.RequestsLogic;

import java.util.List;


public class RequestHandler {
    private final ContextManager contextManager;
    private final RequestsLogic requestsLogic;

    public RequestHandler(DBHandler db, Context context) {
        this.requestsLogic = new RequestsLogic(db);
        this.contextManager = new ContextManager(context, db);
    }

    public List<String> execute(String message, UserData info) { // подумать над архитектурой этого класса
        String authentication = requestsLogic.authentication(info);

        if (authentication != null)
            return List.of(authentication);

        return contextManager.detectContext(message, info);
    }
}
