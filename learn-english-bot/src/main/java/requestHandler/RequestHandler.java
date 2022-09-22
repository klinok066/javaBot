package requestHandler;

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

    public void useCommand(String command, StringBuilder stringAnswer) {
        switch (command) {
            case "help": {
                stringAnswer.append(toHelp());
                break;
            }
            case "start": {
                stringAnswer.append(toStart());
                break;
            }
            default: {
                stringAnswer.append(toDefaultAnswer());
                break;
            }
        }
    }

    public String formatCommandFromTelegram(String command) {
        return command.substring(1, command.length());
    }
}
