package TestCommands;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.context.Context;
import org.matmech.requests.requestHandler.RequestHandler;
import org.matmech.userData.UserData;
import org.matmech.db.DBHandler;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestRequestHandler extends TestCase {
    private final UserData user = new UserData("User", "Unknown", "Unknown", 12243);
    private final DBHandler db = Mockito.mock(DBHandler.class);
    private final Context context = new Context();
    private RequestHandler handler = new RequestHandler(db, context);

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа запросит группу для тестирования
     */
    @Test
    public void testGetGroupForTesting() {
        List<String> result = handler.execute("/test", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(
                "Пожалуйста, введите группу слов, по которым вы хотите произвести тестирование\n" +
                "Если хотите провести тестирование по всем группу, то напишите `Все`"
        );

        assertEquals(expectedResult, result);
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа скажет, чтобы повторили попытку ввода
     */
    @Test
    public void testNotExistGroupForTesting() {
        context.clear(user.getChatId());

        handler.execute("/test", user);

        when(db.groupIsExist(any(String.class))).thenReturn(false);

        List<String> result = handler.execute("глаголы", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("Такой группы не существует. Пожалуйста, введите существующую группу!");

        assertEquals(expectedResult, result);
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа запросит количество слов
     */
    @Test
    public void testExecuteForTestingGetCountWord() {
        context.clear(user.getChatId());

        handler.execute("/test", user);
        when(db.groupIsExist(any(String.class))).thenReturn(true);
        List<String> result = handler.execute("глаголы", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(
                "Пожалуйста, введите количество слов в тесте\n" +
                "Если хотите провести тестирование по всем группу, то напишите `По всем`\n" +
                "Если хотите стандартное количество слов (10), то напишите `Стандартное`"
        );

        assertEquals(expectedResult, result);
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа скажет, чтобы повторили попытку ввода
     */
    @Test
    public void testExecuteForTestingValidateCountWord() {
        context.clear(user.getChatId());

        handler.execute("/test", user);
        when(db.groupIsExist(any(String.class))).thenReturn(true);
        List<String> result = handler.execute("глаголы", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(
                "Пожалуйста, введите количество слов в тесте\n" +
                        "Если хотите провести тестирование по всем группу, то напишите `По всем`\n" +
                        "Если хотите стандартное количество слов (10), то напишите `Стандартное`"
        );

        assertEquals(expectedResult, result);
    }
}
