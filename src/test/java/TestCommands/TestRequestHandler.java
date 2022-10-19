package TestCommands;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.matmech.dbHandler.DBHandler;
import org.matmech.requestHandler.RequestHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestRequestHandler extends TestCase {
    private final Map messages = new HashMap();

    private JSONObject info = new JSONObject();
    private String DB_URL = System.getenv("DB_URL");
    private String DB_USERNAME = System.getenv("DB_USERNAME");
    private String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private DBHandler db = new DBHandler(DB_URL, DB_USERNAME, DB_PASSWORD);
    private RequestHandler handler = new RequestHandler(db);

    @Before
    public void setUpCommandsArray() { // исправить тесты
        info.put("firstName", "User");
        info.put("lastName", "Unknown");
        info.put("username", "Unknown");
        info.put("isBot", "false");
        info.put("id", "-1");
        messages.put("group_list", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("word_add", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("group_create", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("word_list", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("word_to", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("testing_all", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("test", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("test_in_group_all", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("test_in_group", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("stop_test", "So far, work is underway on this function, but in the near future it will be revived");
        messages.put("hello", "Hello, " + info.get("firstName"));
        messages.put("привет", "Sorry, I'm don't understand you...");
        messages.put("dasasdas", "Sorry, I'm don't understand you...");
    }

    @Test
    public void testOnUse() {
        for (Iterator iterator = messages.keySet().iterator(); iterator.hasNext(); ) {
            final String key = iterator.next().toString();
            final String trueAnswer = messages.get(key).toString();
            final String answer = handler.onUse(key, info);
            assertEquals(trueAnswer, answer);
        }
    }

    @Test
    public void testFormatCommandFromTelegram() {
        assertEquals("help", handler.formatCommandFromTelegram("/help"));
        assertEquals("start", handler.formatCommandFromTelegram("/start"));
        assertEquals("hello", handler.formatCommandFromTelegram("hello"));
    }
}
