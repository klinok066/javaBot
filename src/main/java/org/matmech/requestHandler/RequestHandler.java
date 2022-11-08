package org.matmech.requestHandler;

import org.checkerframework.checker.units.qual.A;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestHandler {
    private final DBHandler db;

    private String toHelp() {
        return "Bot's commands:\n" +
                "/start - start the bot\n\n" +
                "Groups:\n" +
                "/group_list - list of all groups\n" +
                "/group_create <name> - create a group\n\n" +
                "Words:\n" +
                "/word_list - list of all paginated words\n" +
                "/word_add <word> <translation> <group> - adding a word. You can specify the translation yourself, but if you write _translate, then the translation will be automatic through the Yandex API, the group parameter is optional, all the rest are required\n" +
                "/word_to <word> <group> - redefining the group of an existing word\n\n" +
                "Testing:\n" +
                "/test_all <mode> - start testing for all words from the database\n" +
                "/test <count> <mode> - start testing on some part of the words from the entire database\n" +
                "/test_in_group_all <group> <mode> - start testing for all words from the group\n" +
                "/test_in_group <group> <count> <mode> - start testing on a certain number of words from the group\n\n" +
                "Stopped testing:\n" +
                "/stop_test - end testing (for /test_all and for /test_in_group_all)";
    }

    private boolean isCmd(String command) {
        return command.charAt(0) == '/';
    }

    private String toDefaultAnswer() {
        return "Sorry, I'm don't understand you...";
    }


    private String toStart(DataSaver data) {
        return db.usersInsert(data.getFirstname(), data.getSurname(), data.getTag());
    }

    private String functionInProgress(){
        return "So far, work is underway on this function, but in the near future it will be revived";
    }

    private String wordAdd(ArrayList<String> params, DataSaver data) {
        return db.wordAdd(params.get(0), params.get(1), params.get(2), data.getTag());
    }

    private String translateWord(ArrayList<String> params) {
        return db.translateWord(params.get(0));
    }

    private String edit(ArrayList<String> params) {
        return db.edit(params.get(0), params.get(1), params.get(2));
    }

    private String getGroup(ArrayList<String> params) {
        return db.getGroup(params.get(0));
    }

    private String deleteWord(ArrayList<String> params) {
        return db.deleteWord(params.get(0));
    }

    private String setMode(ArrayList<String> params, DataSaver data) {
        return db.setMode(params.get(0), data.getTag());
    }

    private String getMode(DataSaver data) {
        return db.getMode(data.getTag());
    }

    private String useCommand(String command, DataSaver info, ArrayList<String> params) { // ответ на команды
        return switch (command) {
            case "help" -> toHelp();
            case "start" -> toStart(info);
            case "word_add" -> wordAdd(params, info);
            case "translate" -> translateWord(params);
            case "remove" -> deleteWord(params);
            case "edit" -> edit(params);
            case "get_group" -> getGroup(params);
            case "set_mode" -> setMode(params, info);
            case "get_mode" -> getMode(info);
            case    "group_list",
                    "group_create",
                    "word_list",
                    "test",
                    "stop_test" -> functionInProgress();

            default -> toDefaultAnswer();
        };
    }

    private String toAnswer(String messageString, DataSaver info) { // просто ответ на обычные сообщения
        return switch (messageString.toLowerCase()) {
            case "hello" -> "Hello, " + info.getFirstname();
            default -> toDefaultAnswer();
        };
    }

    public RequestHandler(DBHandler db) {
        this.db = db;
    }

    public String onUse(String messageString, DataSaver info) {
        if (isCmd(messageString)) {
            ArrayList<String> params = new ArrayList<String>(List.of(messageString.split(" ")));
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
