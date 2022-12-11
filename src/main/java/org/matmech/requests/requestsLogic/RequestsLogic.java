package org.matmech.requests.requestsLogic;

import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;

import java.util.List;

public class RequestsLogic {
    private final DBHandler db;

    public RequestsLogic(DBHandler db) {
        this.db = db;
    }

    public String toHelp() {
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

    public String toDefaultAnswer() {
        return "Sorry, I'm don't understand you...";
    }

    public String toStart(UserData data) {
        return db.usersInsert(data.getFirstname(), data.getSurname(), data.getTag());
    }

    public String functionInProgress(){
        return "So far, work is underway on this function, but in the near future it will be revived";
    }

    public String wordAdd(List<String> params, UserData data) {
        return db.wordAdd(params.get(0), params.get(1), params.get(2), data.getTag());
    }

    public String translateWord(List<String> params) {return db.translateWord(params.get(0));}

    public String edit(List<String> params) {
        return db.edit(params.get(0), params.get(1), params.get(2));
    }

    public String getGroup(List<String> params) {
        return db.getGroup(params.get(0));
    }

    public String deleteWord(List<String> params) {
        return db.deleteWord(params.get(0));
    }

    public String authentication(UserData info) {
        if (!db.userIsExist(info.getTag()))
            return "Вы не зарегистрированы в системе! Чтобы зарегистрироваться в системе напишите /start";

        return null;
    }
}
