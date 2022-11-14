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
        final String IN_FUTURE = "So far, work is underway on this function, but in the near future it will be revived";
        final String WRONG_CMD = "Sorry, I'm don't understand you...";

        assertEquals(IN_FUTURE, handler.onUse("/group_list", data));
        assertEquals(IN_FUTURE, handler.onUse("/group_list", data));
        assertEquals(IN_FUTURE, handler.onUse("/group_create", data));
        assertEquals(IN_FUTURE, handler.onUse("/group_create", data));
        assertEquals(IN_FUTURE, handler.onUse("/word_list", data));
        assertEquals(IN_FUTURE, handler.onUse("/word_list", data));
        assertEquals(IN_FUTURE, handler.onUse("/test", data));
        assertEquals(IN_FUTURE, handler.onUse("/test", data));
        assertEquals(IN_FUTURE, handler.onUse("/stop_test", data));
        assertEquals(IN_FUTURE, handler.onUse("/stop_test", data));
        assertEquals(wrongCmd, handler.onUse("sdfsdfsdf", data));
        assertEquals(wrongCmd, handler.onUse("xzc", data));
        assertEquals(wrongCmd, handler.onUse("укеуке", data));
        assertEquals(wrongCmd, handler.onUse("привет", data));
        assertEquals(wrongCmd, handler.onUse("спам", data));
        assertEquals(wrongCmd, handler.onUse("бот", data));
        assertEquals(wrongCmd, handler.onUse("ничего", data));
        assertEquals(wrongCmd, handler.onUse("xcv", data));
        assertEquals("Hello, " + data.getFirstname(), handler.onUse("hello", data));
    }

    @Test
    public void testFormatCommandFromTelegram() {
        assertEquals("help", handler.formatCommandFromTelegram("/help"));
        assertEquals("start", handler.formatCommandFromTelegram("/start"));
        assertEquals("hello", handler.formatCommandFromTelegram("hello"));
    }
}
