package TestCommands;

import junit.framework.TestCase;
import org.junit.Assert;
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

public class TestRequestHandler {
    private final UserData user = new UserData("User", "Unknown", "Unknown", 12243);
    private final DBHandler db = Mockito.mock(DBHandler.class);
    private final Context context = new Context();
    private RequestHandler handler = new RequestHandler(db, context);

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа запросит группу для тестирования
     */
    @Test
    public void testGetGroupForTesting() {
        when(db.userIsExist(any())).thenReturn(true);

        List<String> result = handler.execute("/test", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(
                "Пожалуйста, введите группу слов, по которым вы хотите произвести тестирование\n" +
                "Если хотите провести тестирование по всем группу, то напишите `Все`"
        );

        for (int i = 0; i < result.size(); i++)
            Assert.assertEquals(expectedResult.get(0), result.get(0));
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа скажет, чтобы повторили попытку ввода
     */
    @Test
    public void testNotExistGroupForTesting() {
        when(db.userIsExist(any())).thenReturn(true);

        context.clear(user.getChatId());

        handler.execute("/test", user);

        when(db.groupIsExist(any(String.class))).thenReturn(false);

        List<String> result = handler.execute("глаголы", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("Такой группы не существует. Пожалуйста, введите существующую группу!");

        for (int i = 0; i < result.size(); i++)
            Assert.assertEquals(expectedResult.get(0), result.get(0));
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа запросит количество слов
     */
    @Test
    public void testExecuteForTestingGetCountWord() {
        when(db.userIsExist(any())).thenReturn(true);

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

        for (int i = 0; i < result.size(); i++)
            Assert.assertEquals(expectedResult.get(0), result.get(0));
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа скажет, чтобы повторили попытку ввода
     */
    @Test
    public void testExecuteForTestingValidateCountWord() {
        when(db.userIsExist(any())).thenReturn(true);

        context.clear(user.getChatId());

        handler.execute("/test", user);
        when(db.groupIsExist(any(String.class))).thenReturn(true);
        handler.execute("глаголы", user);
        List<String> result = handler.execute("10лол", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(
                "Вы ввели недопустимое значение! Повторите ввод:"
        );

        for (int i = 0; i < result.size(); i++)
            Assert.assertEquals(expectedResult.get(0), result.get(0));
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа запросит режим тестирования
     */
    @Test
    public void testExecuteForTestingGetMode() {
        when(db.userIsExist(any())).thenReturn(true);

        context.clear(user.getChatId());

        handler.execute("/test", user);
        when(db.groupIsExist(any(String.class))).thenReturn(true);
        handler.execute("глаголы", user);
        List<String> result = handler.execute("10", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(
                "Введите режим тестирование: `Easy` - легкий, `Difficult` - сложный\n" +
                "Если хотите стандартный режим (Easy), то введите `Стандартный`"
        );

        for (int i = 0; i < result.size(); i++)
            Assert.assertEquals(expectedResult.get(0), result.get(0));
    }

    /**
     * Unit-тест метода execute: проверяет запуск команды и то, что программа скажет, чтобы повторили попытку ввода
     */
    @Test
    public void testExecuteForTestingValidateMode() {
        when(db.userIsExist(any())).thenReturn(true);

        context.clear(user.getChatId());

        handler.execute("/test", user);
        when(db.groupIsExist(any(String.class))).thenReturn(true);
        handler.execute("глаголы", user);
        handler.execute("10", user);
        List<String> result = handler.execute("10", user);

        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(
                "Вы ввели не существующий режим!\n"
        );

        for (int i = 0; i < result.size(); i++)
            Assert.assertEquals(expectedResult.get(0), result.get(0));
    }

//    /**
//     * Unit-тест метода execute: проверяет запуск команды и то, что программа скажет, чтобы повторили попытку ввода
//     */
//    @Test
//    public void testExecuteForTestingValidateMode() {
//        when(db.userIsExist(any())).thenReturn(true);
//
//        context.clear(user.getChatId());
//
//        handler.execute("/test", user);
//        when(db.groupIsExist(any(String.class))).thenReturn(true);
//        handler.execute("глаголы", user);
//        handler.execute("10", user);
//        when()
//        List<String> result = handler.execute("easy", user);
//
//        List<String> expectedResult = new ArrayList<String>();
//        expectedResult.add(
//                "Вы ввели не существующий режим!\n"
//        );
//
//        for (int i = 0; i < result.size(); i++)
//            assertEquals(expectedResult.get(0), result.get(0));
//    }
}
