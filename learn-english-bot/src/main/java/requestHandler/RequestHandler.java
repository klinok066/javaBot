package requestHandler;

public class RequestHandler {
    private String toHelp() {
        return "/start - start the bot\n" +
                "/group_list - list of all groups\n" +
                "/group_create <name> - create a group\n" +
                "/word_list - list of all paginated words\n" +
                "/word_add <word> <translation> <group> - adding a word. You can specify the translation yourself, but if you write _translate, then the translation will be automatic through the Yandex API, the group parameter is optional, all the rest are required\n" +
                "/word_to <word> <group> - redefining the group of an existing word\n" +
                "/test_all <mode> - start testing for all words from the database\n" +
                "/test <number of words> <mode> - start testing on some part of the words from the entire database\n" +
                "/test_in_group_all <group> <mode> - start testing for all words from the group\n" +
                "/test_in_group <group> <amount> <mode> - start testing on a certain number of words from the group\n" +
                "/stop_test - end testing (for /test_all and for test_in_group_all)";
    }

    private String toStart() {
        return toHelp();
    }

    public void useCommand(String command, String stringAnswer) {
        switch (command) {
            case "help": {
                stringAnswer = toHelp();
                break;
            }
            case "start": {
                stringAnswer = "Hello, I'm bot, which help you to learn new english words!\n" +
                               "List of commands which you can use:\n" +
                               toStart();
                break;
            }
            default: {
                stringAnswer = "Sorry, I'm don't understand you...";
                break;
            }
        }
    }
}
