package requestHandler;

import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RequestHandler {
    private String toHelp() {
        return  "Start bot:\n" +
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

    private String toDefaultAnswer() {
        return "Sorry, I'm don't understand you...";
    }

    private String toStart() {
        return  "Hello, I'm bot, which help you to learn new english words!\n" +
                "List of commands which you can use:\n" +
                toHelp();
    }

    private String toHello() {
        return "Hello, friend!";
    }
    //есть ли смысл от этой функции? Если я добавил ответ на простое hello, врядли кто-то будет писать именно /hello

    private String functionInProgress(){
        return "So far, work is underway on this function, but in the near future it will be revived";
    }

    public String useCommand(String command) {
        return switch (command) {
            case "help" -> toHelp();
            case "start" -> toStart();
            case "hello" -> toHello();
            case "group_list", "word_add", "group_create", "word_list", "word_to", "testing_all", "test", "test_in_group_all", "test_in_group", "stop_test" -> functionInProgress();

            default -> toDefaultAnswer();
        };
    }

    public String toAnswer(String messageString, JSONObject info) {
        return switch (messageString.toLowerCase()) {
            case "hello" -> "Hello, " + info.get("firstName");
//            case "" -> "Hello, " + info.get("firstName");
            default -> "Sorry, I'm don't understand you...\n" +
                       "You can write /help for get all commands which you can use!";
        };
    }

    public String formatCommandFromTelegram(String command) {
        return command.substring(1, command.length());
    }
}
