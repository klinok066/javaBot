package TestCommands;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.requestHandler.RequestHandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestRequestHandler extends TestCase {
    private DataSaver data;
    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;
    private DBHandler db;
    private RequestHandler handler;

    @Before
    public void setUpCommandsArray() { // исправить тесты
        DB_URL = System.getenv("DB_URL");
        DB_USERNAME = System.getenv("DB_USERNAME");
        DB_PASSWORD = System.getenv("DB_PASSWORD");
        data = new DataSaver("User", "Unknown", "Unknown", -1);
        db = new DBHandler(DB_URL, DB_USERNAME, DB_PASSWORD);
        handler = new RequestHandler(db);
    }

    @Test
    public void testOnUse() {
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/group_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/group_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/group_create", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/group_create", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/word_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/word_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/test", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/test", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/stop_test", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.onUse("/stop_test", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("sdfsdfsdf", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("xzc", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("укеуке", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("привет", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("спам", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("бот", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("ничего", data));
        assertEquals("Sorry, I'm don't understand you...", handler.onUse("xcv", data));
        assertEquals("Hello, " + data.getFirstname(), handler.onUse("hello", data));
    }

    @Test
    public void testFormatCommandFromTelegram() {
        assertEquals("help", handler.formatCommandFromTelegram("/help"));
        assertEquals("start", handler.formatCommandFromTelegram("/start"));
        assertEquals("hello", handler.formatCommandFromTelegram("hello"));
    }
}
