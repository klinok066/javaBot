package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.db.bll.WordsDBSource;
import org.matmech.db.models.Words;
import org.matmech.db.repository.DBConnection;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestWordsDBSource extends TestCase {
    private final DBConnection dbConnection = Mockito.mock(DBConnection.class);
    private final WordsDBSource wordsDBSource = new WordsDBSource();

    @Test
    public void testWordAdd() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> wordResponse = new HashMap<String, String>();

            wordResponse.put("word_value", "kick");
            wordResponse.put("word_translate", "пнуть");
            wordResponse.put("group_id", "123");
            wordResponse.put("dictonary_id", "123");

            response.add(wordResponse);

            Words words = new Words();
            words.setWordValue("kick");
            words.setWordTranslate("пнуть");
            words.setGroupId(123);
            words.setDictonaryId(123);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertFalse(wordsDBSource.wordAdd(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    @Test
    public void testNotWordAdd() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertTrue(wordsDBSource.wordAdd(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    @Test
    public void testTranslate() {
        try {
            ArrayList<HashMap<String, String>> responseForIsExist = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> wordResponse = new HashMap<String, String>();

            wordResponse.put("word_value", "kick");
            wordResponse.put("word_translate", "пнуть");
            wordResponse.put("group_id", "123");
            wordResponse.put("dictonary_id", "123");

            responseForIsExist.add(wordResponse);

            ArrayList<HashMap<String, String>> responseForQuery = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> wordTranslateResponse = new HashMap<String, String>();

            wordTranslateResponse.put("word_translate", "пнуть");

            responseForQuery.add(wordTranslateResponse);

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForQuery);

            assertEquals("пнуть", wordsDBSource.translate(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    @Test
    public void testNotTranslate() {
        try {
            ArrayList<HashMap<String, String>> responseForIsExist = new ArrayList<HashMap<String, String>>();

            ArrayList<HashMap<String, String>> responseForQuery = new ArrayList<HashMap<String, String>>();

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

            assertNull(wordsDBSource.translate(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    @Test
    public void testDeleteWord() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> wordResponse = new HashMap<String, String>();

            wordResponse.put("word_value", "kick");
            wordResponse.put("word_translate", "пнуть");
            wordResponse.put("group_id", "123");
            wordResponse.put("dictonary_id", "123");

            response.add(wordResponse);

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertTrue(wordsDBSource.deleteWord(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    @Test
    public void testNotDeleteWord() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertFalse(wordsDBSource.deleteWord(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }
}
