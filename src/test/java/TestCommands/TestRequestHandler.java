package TestCommands;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import requestHandler.RequestHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestRequestHandler extends TestCase {
    private final Map commandsData = new HashMap();
    private final Map usuallyMessage = new HashMap();

    private JSONObject info = new JSONObject();
    private RequestHandler handler = new RequestHandler();

    @Before
    public void setUpCommandsArray() {
        commandsData.put("start", "Hello, I'm bot, which help you to learn new english words!\n" +
                                  "List of commands which you can use just write: /help");
        commandsData.put("help", "Bot\'s commands:\n" +
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
                "/stop_test - end testing (for /test_all and for /test_in_group_all)");
        commandsData.put("group_list", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("word_add", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("group_create", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("word_list", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("word_to", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("testing_all", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("test", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("test_in_group_all", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("test_in_group", "So far, work is underway on this function, but in the near future it will be revived");
        commandsData.put("stop_test", "So far, work is underway on this function, but in the near future it will be revived");
        info = handler.getInfo("User", "Unknown", "unknown", "false", "-1");
        usuallyMessage.put("hello", "Hello, " + info.get("firstName"));
        usuallyMessage.put("привет", "Sorry, I'm don't understand you...");
        usuallyMessage.put("dasasdas", "Sorry, I'm don't understand you...");
    }

    @Test
    public void testUseCommand() {
        for (Iterator iterator = commandsData.keySet().iterator(); iterator.hasNext(); ) {
            final String key = iterator.next().toString();
            final String trueAnswer = commandsData.get(key).toString();
            final String answer = handler.toAnswer(key, info);
            assertEquals(trueAnswer, answer);
        }
    }
    @Test
    public void testToAnswer() {
        for (Iterator iterator = usuallyMessage.keySet().iterator(); iterator.hasNext(); ) {
            final String key = iterator.next().toString();
            final String trueAnswer = usuallyMessage.get(key).toString();
            final String answer = handler.toAnswer(key, info);
            assertEquals(trueAnswer, answer);
        }
    }
}
