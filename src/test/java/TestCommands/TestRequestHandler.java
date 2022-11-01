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
        assertEquals(handler.onUse("/group_list", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/group_list", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/group_create", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/group_create", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/word_list", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/word_list", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/test", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/test", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/stop_test", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("/stop_test", data), "So far, work is underway on this function, but in the near future it will be revived");
        assertEquals(handler.onUse("sdfsdfsdf", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("xzc", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("xcv", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("укеуке", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("привет", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("спам", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("бот", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("ничего", data), "Sorry, I'm don't understand you...");
        assertEquals(handler.onUse("hello", data), "Hello, " + data.getFirstname());
    }

    @Test
    public void testFormatCommandFromTelegram() {
        assertEquals("help", handler.formatCommandFromTelegram("/help"));
        assertEquals("start", handler.formatCommandFromTelegram("/start"));
        assertEquals("hello", handler.formatCommandFromTelegram("hello"));
    }
}
