package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.db.bll.WordsDBSource;
import org.matmech.db.models.Word;
import org.matmech.db.repository.DBConnection;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestWordDBSource extends TestCase {
    private final DBConnection dbConnection = Mockito.mock(DBConnection.class);
    private final WordsDBSource wordsDBSource = new WordsDBSource(dbConnection);

    /**
     * Unit-тест метода wordAdd: проверяет его некорректную работу
     */
    @Test
    public void testNotWordAdd() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();
        Map<String, String> wordResponse = new HashMap<String, String>();

        wordResponse.put("word_value", "kick");
        wordResponse.put("word_translate", "пнуть");
        wordResponse.put("group_id", "123");
        wordResponse.put("dictonary_id", "123");

        response.add(wordResponse);

        Word word = new Word();
        word.setWordValue("kick");
        word.setWordTranslate("пнуть");
        word.setGroupId(123);
        word.setDictonaryId(123);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertFalse(wordsDBSource.wordAdd(word));
    }

    /**
     * Unit-тест метода wordAdd: проверяет его правильную работу
     */
    @Test
    public void testWordAdd() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertTrue(wordsDBSource.wordAdd(word));
    }


    /**
     * Unit-тест метода translate: проверяет его правильную работу
     */
    @Test
    public void testTranslate() throws SQLException {
        List<Map<String, String>> responseForIsExist = new ArrayList<Map<String, String>>();
        Map<String, String> wordResponse = new HashMap<String, String>();

        wordResponse.put("word_value", "kick");
        wordResponse.put("word_translate", "пнуть");
        wordResponse.put("group_id", "123");
        wordResponse.put("dictonary_id", "123");

        responseForIsExist.add(wordResponse);

        ArrayList<HashMap<String, String>> responseForQuery = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> wordTranslateResponse = new HashMap<String, String>();

        wordTranslateResponse.put("word_translate", "пнуть");

        responseForQuery.add(wordTranslateResponse);

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForQuery);

        assertEquals("пнуть", wordsDBSource.translate(word));
    }

    /**
     * Unit-тест метода translate: проверяет его некорректную работу
     */
    @Test
    public void testNotTranslate() throws SQLException {
        List<Map<String, String>> responseForIsExist = new ArrayList<Map<String, String>>();
        List<Map<String, String>> responseForQuery = new ArrayList<Map<String, String>>();

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

        assertNull(wordsDBSource.translate(word));
    }

    /**
     * Unit-тест метода getDictonaryId: проверяет его правильную работу
     */
    @Test
    public void testGetDictonaryId() throws SQLException {
        List<Map<String, String>> responseForIsExist = new ArrayList<Map<String, String>>();
        Map<String, String> wordResponse = new HashMap<String, String>();

        wordResponse.put("word_value", "kick");
        wordResponse.put("word_translate", "пнуть");
        wordResponse.put("group_id", "123");
        wordResponse.put("dictonary_id", "123");

        responseForIsExist.add(wordResponse);

        List<Map<String, String>> responseForQuery = new ArrayList<Map<String, String>>();
        Map<String, String> wordTranslateResponse = new HashMap<String, String>();

        wordTranslateResponse.put("dictonary_id", "123");

        responseForQuery.add(wordTranslateResponse);

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForQuery);

        assertEquals(123, wordsDBSource.getDictonaryId(word));
    }

    /**
     * Unit-тест метода getDictonaryId: проверяет его неправильную работу
     */
    @Test
    public void testNotGetDictonaryId() throws SQLException {
        List<Map<String, String>> responseForIsExist = new ArrayList<Map<String, String>>();

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

        assertEquals(-1, wordsDBSource.getDictonaryId(word));
    }

    /**
     * Unit-тест метода getGroupId: проверяет его правильную работу
     */
    @Test
    public void testGetGroupId() throws SQLException {
        List<Map<String, String>> responseForIsExist = new ArrayList<Map<String, String>>();
        HashMap<String, String> wordResponse = new HashMap<String, String>();

        wordResponse.put("word_value", "kick");
        wordResponse.put("word_translate", "пнуть");
        wordResponse.put("group_id", "123");
        wordResponse.put("dictonary_id", "123");

        responseForIsExist.add(wordResponse);

        List<Map<String, String>> responseForQuery = new ArrayList<Map<String, String>>();
        Map<String, String> wordTranslateResponse = new HashMap<String, String>();

        wordTranslateResponse.put("group_id", "123");

        responseForQuery.add(wordTranslateResponse);

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForQuery);

        assertEquals(123, wordsDBSource.getGroupId(word));
    }

    /**
     * Unit-тест метода getGroupId: проверяет его неправильную работу
     */
    @Test
    public void testNotGetGroupId() throws SQLException {
        List<HashMap<String, String>> responseForIsExist = new ArrayList<HashMap<String, String>>();

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(responseForIsExist);

        assertEquals(-1, wordsDBSource.getDictonaryId(word));
    }

    /**
     * Unit-тест метода editTranslation: проверяет его правильную работу
     */
    @Test
    public void testEditTranslation() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();
        Map<String, String> wordResponse = new HashMap<String, String>();

        wordResponse.put("word_value", "kick");
        wordResponse.put("word_translate", "пнуть");
        wordResponse.put("group_id", "123");
        wordResponse.put("dictonary_id", "123");

        response.add(wordResponse);

        Word word = new Word();
        word.setWordTranslate("ударить ногой");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertTrue(wordsDBSource.editTranslation(word));
    }

    /**
     * Unit-тест метода editTranslation: проверяет его неправильную работу
     */
    @Test
    public void testNotEditTranslation() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        Word word = new Word();
        word.setWordTranslate("ударить ногой");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertFalse(wordsDBSource.editTranslation(word));
    }

    /**
     * Unit-тест метода editGroupId: проверяет его правильную работу
     */
    @Test
    public void testEditGroupId() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();
        Map<String, String> wordResponse = new HashMap<String, String>();

        wordResponse.put("word_value", "kick");
        wordResponse.put("word_translate", "пнуть");
        wordResponse.put("group_id", "123");
        wordResponse.put("dictonary_id", "123");

        response.add(wordResponse);

        Word word = new Word();
        word.setGroupId(321);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertTrue(wordsDBSource.editGroupId(word));
    }

    /**
     * Unit-тест метода editGroupId: проверяет его неправильную работу
     */
    @Test
    public void testNotEditGroupId() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        Word word = new Word();
        word.setGroupId(321);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertFalse(wordsDBSource.editGroupId(word));
    }

    /**
     * Unit-тест метода deleteWord: проверяет его правильную работу
     */
    @Test
    public void testDeleteWord() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();
        Map<String, String> wordResponse = new HashMap<String, String>();

        wordResponse.put("word_value", "kick");
        wordResponse.put("word_translate", "пнуть");
        wordResponse.put("group_id", "123");
        wordResponse.put("dictonary_id", "123");

        response.add(wordResponse);

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertTrue(wordsDBSource.deleteWord(word));
    }

    /**
     * Unit-тест метода deleteWord: проверяет его некорректную работу
     */
    @Test
    public void testNotDeleteWord() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        Word word = new Word();
        word.setWordValue("kick");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertFalse(wordsDBSource.deleteWord(word));
    }
}
