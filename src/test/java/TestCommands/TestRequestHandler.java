package TestCommands;

import junit.framework.TestCase;
import org.checkerframework.checker.units.qual.C;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.matmech.cache.Cache;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.requestHandler.RequestHandler;
import org.mockito.Mockito;

public class TestRequestHandler extends TestCase {
    private final DataSaver data = new DataSaver("User", "Unknown", "Unknown", -1);
    private final DBHandler dbMock = Mockito.mock(DBHandler.class);
    private final Cache cache = new Cache();
    private RequestHandler handler;

    @Before
    public void setUpCommandsArray() {
        handler = new RequestHandler(dbMock, cache);
    }

    @Test
    public void testOnUseGeneral() {
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/group_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/group_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/group_create", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/group_create", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/word_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/word_list", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/test", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/test", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/stop_test", data));
        assertEquals("So far, work is underway on this function, but in the near future it will be revived", handler.processCmd("/stop_test", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("sdfsdfsdf", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("xzc", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("укеуке", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("привет", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("спам", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("бот", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("ничего", data));
        assertEquals("Sorry, I'm don't understand you...", handler.processCmd("xcv", data));
        assertEquals("Hello, " + data.getFirstname(), handler.processCmd("hello", data));
    }

    @Test
    public void testFormatCommandFromTelegram() {
        assertEquals("help", handler.formatCommandFromTelegram("/help"));
        assertEquals("start", handler.formatCommandFromTelegram("/start"));
        assertEquals("hello", handler.formatCommandFromTelegram("hello"));
    }
}
