package TestCommands;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.matmech.connector.cmdLogic.CmdLogic;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCmdLogic extends TestCase {
    private final Map comandsData = new HashMap();
    private final Map usuallyMessage = new HashMap();

    private JSONObject info = new JSONObject();

    private CmdLogic handler = new CmdLogic("test firstname", "test lastname");


    @Before
    public void setUp(){
// хд что тут писать
    }

    @Test
    public void testResponseForCMD(){
// бесконечный цикла как тестить
    }

}
