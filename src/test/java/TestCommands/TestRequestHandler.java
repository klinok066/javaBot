package TestCommands;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.context.Context;
import org.matmech.requests.requestHandler.RequestHandler;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;
import org.mockito.Mockito;

public class TestRequestHandler extends TestCase {
    private final UserData data = new UserData("User", "Unknown", "Unknown", 12243);
    private final DBHandler dbMock = Mockito.mock(DBHandler.class);
    private final Context context = new Context();
    private RequestHandler handler = new RequestHandler(dbMock, context);

    /**
     * Unit-тест метода onUse: проверяет его правильную работу
     */
    @Test
    public void testOnUseGeneral() {

    }
}
