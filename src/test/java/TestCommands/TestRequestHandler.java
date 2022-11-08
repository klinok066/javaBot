package TestCommands;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.matmech.dataSaver.DataSaver;
import org.matmech.db.DBHandler;
import org.matmech.requestHandler.RequestHandler;
import org.mockito.Mockito;

public class TestRequestHandler extends TestCase {
    private final DataSaver data = new DataSaver("User", "Unknown", "Unknown");
    private final DBHandler dbMock = Mockito.mock(DBHandler.class);
    private RequestHandler handler = new RequestHandler(dbMock);

    @Before
    public void setUpCommandsArray() { // исправить тесты
        handler = new RequestHandler(dbMock);
    }

    @Test
    public void testOnUseGeneral() {
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
