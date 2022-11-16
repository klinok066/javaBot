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

    /**
     * Unit-тест метода wordAdd: проверяет его некорректную работу
     */
    @Test
    public void testNotWordAdd() {
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

    /**
     * Unit-тест метода wordAdd: проверяет его правильную работу
     */
    @Test
    public void testWordAdd() {
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


    /**
     * Unit-тест метода translate: проверяет его правильную работу
     */
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

    /**
     * Unit-тест метода translate: проверяет его некорректную работу
     */
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

    /**
     * Unit-тест метода getDictonaryId: проверяет его правильную работу
     */
    @Test
    public void testGetDictonaryId(){
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

            wordTranslateResponse.put("dictonary_id", "123");

            responseForQuery.add(wordTranslateResponse);

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForQuery);

            assertEquals(123, wordsDBSource.getDictonaryId(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода getDictonaryId: проверяет его неправильную работу
     */
    @Test
    public void testNotGetDictonaryId(){
        try {
            ArrayList<HashMap<String, String>> responseForIsExist = new ArrayList<HashMap<String, String>>();

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

            assertEquals(-1, wordsDBSource.getDictonaryId(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода getGroupId: проверяет его правильную работу
     */
    @Test
    public void testGetGroupId(){
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

            wordTranslateResponse.put("group_id", "123");

            responseForQuery.add(wordTranslateResponse);

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForQuery);

            assertEquals(123, wordsDBSource.getGroupId(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода getGroupId: проверяет его неправильную работу
     */
    @Test
    public void testNotGetGroupId(){
        try {
            ArrayList<HashMap<String, String>> responseForIsExist = new ArrayList<HashMap<String, String>>();

            Words words = new Words();
            words.setWordValue("kick");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

            assertEquals(-1, wordsDBSource.getDictonaryId(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода editTranslation: проверяет его правильную работу
     */
    @Test
    public void testEditTranslation(){
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> wordResponse = new HashMap<String, String>();

            wordResponse.put("word_value", "kick");
            wordResponse.put("word_translate", "пнуть");
            wordResponse.put("group_id", "123");
            wordResponse.put("dictonary_id", "123");

            response.add(wordResponse);

            Words words = new Words();
            words.setWordTranslate("ударить ногой");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertTrue(wordsDBSource.editTranslation(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода editTranslation: проверяет его неправильную работу
     */
    @Test
    public void testNotEditTranslation(){
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            Words words = new Words();
            words.setWordTranslate("ударить ногой");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertFalse(wordsDBSource.editTranslation(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода editGroupId: проверяет его правильную работу
     */
    @Test
    public void testEditGroupId(){
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> wordResponse = new HashMap<String, String>();

            wordResponse.put("word_value", "kick");
            wordResponse.put("word_translate", "пнуть");
            wordResponse.put("group_id", "123");
            wordResponse.put("dictonary_id", "123");

            response.add(wordResponse);

            Words words = new Words();
            words.setGroupId(321);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertTrue(wordsDBSource.editGroupId(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода editGroupId: проверяет его неправильную работу
     */
    @Test
    public void testNotEditGroupId(){
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            Words words = new Words();
            words.setGroupId(321);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertFalse(wordsDBSource.editGroupId(words, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тест провалился!\n" + e.getMessage());
        }
    }

    /**
     * Unit-тест метода deleteWord: проверяет его правильную работу
     */
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

    /**
     * Unit-тест метода deleteWord: проверяет его некорректную работу
     */
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
