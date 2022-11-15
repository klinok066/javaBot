package org.matmech.requests.requestHandler;

import org.matmech.cache.Cache;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.paramsHandler.ParamsHandler;
import org.matmech.requests.requestsLogic.RequestsLogic;

import java.util.ArrayList;
import java.util.List;


// все методы с выводом текста надо убрать в отдельный класс

public class RequestHandler {
    private final Cache cache;
    private final ParamsHandler paramsHandler;
    private final RequestsLogic requestsLogic;

    private boolean isCmd(String command) {
        return command.charAt(0) == '/';
    }

    private String useCommand(String command, DataSaver info, List<String> params) { // ответ на команды
        return switch (command) {
            case "help" -> requestsLogic.toHelp();
            case "start" -> requestsLogic.toStart(info);
            case "word_add" -> requestsLogic.wordAdd(params, info);
            case "translate" -> requestsLogic.translateWord(params);
            case "remove" -> requestsLogic.deleteWord(params);
            case "edit" -> requestsLogic.edit(params);
            case "get_group" -> requestsLogic.getGroup(params);
            case    "group_list",
                    "group_create",
                    "word_list",
                    "test",
                    "stop_test" -> requestsLogic.functionInProgress();

            default -> requestsLogic.toDefaultAnswer();
        };
    }

    private String toAnswer(String messageString, DataSaver info) { // просто ответ на обычные сообщения
        return switch (messageString.toLowerCase()) {
            case "hello" -> "Hello, " + info.getFirstname();
            default -> requestsLogic.toDefaultAnswer();
        };
    }

    public RequestHandler(DBHandler db, Cache cache) {
        this.cache = cache;
        this.paramsHandler = new ParamsHandler(cache, db);
        this.requestsLogic = new RequestsLogic(db);
    }

    /*
        Я думаю, что стоит оставить только paramsHandler, там будет выполняться вся основная логика
        тут будет промежуточный узел назначения контекста
        В paramsHandler будет только очищения контекста
     */
    public String processCmd(String messageString, DataSaver info) { // подумать над архитектурой этого класса
        String authentication = requestsLogic.authentication(info);

        if (requestsLogic.authentication(info) != null)
            return authentication;

        if (cache.isBusy(info.getChatId()))
            return paramsHandler.handler(info.getChatId());

        if (isCmd(messageString)) {
            List<String> params = new ArrayList<String>(List.of(messageString.split(" ")));
            String firstWord = params.get(0);
            params.remove(0);

            return useCommand(formatCommandFromTelegram(firstWord), info, params);
        }
        else
            return toAnswer(messageString, info);
    }

    public String formatCommandFromTelegram(String command) {
        if (!isCmd(command))
            return command;

        return command.substring(1);
    }
}
