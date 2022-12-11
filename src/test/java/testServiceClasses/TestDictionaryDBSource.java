package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.db.bll.DictonaryDBSource;
import org.matmech.db.models.Dictionary;
import org.matmech.db.repository.DBConnection;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestDictionaryDBSource extends TestCase {
    private final DBConnection dbConnection = Mockito.mock(DBConnection.class);
    private final DictonaryDBSource dictonaryDBSource = new DictonaryDBSource(dbConnection);

    /**
     * Unit-тест метода createDictonary: проверяет его правильную работу
     */
    @Test
    public void testCreateDictonary() {
        Dictionary dictionary = new Dictionary();
        dictionary.setUserId(12);

        assertTrue(dictonaryDBSource.createDictonary(dictionary));
    }

    /**
     * Unit-тест метода createDictonary: проверяет его неправильную работу
     */
    @Test
    public void testNotCreateDictonary() {
        Dictionary dictionary = new Dictionary();
        dictionary.setUserId(-1);

        assertFalse(dictonaryDBSource.createDictonary(dictionary));
    }

    /**
     * Unit-тест метода getDictonaryId: проверяет его правильную работу
     */
    @Test
    public void testGetDictonaryId() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        Map<String, String> dictonaryResponse = new HashMap<String, String>();
        dictonaryResponse.put("id", "10");

        response.add(dictonaryResponse);

        Dictionary dictionary = new Dictionary();
        dictionary.setUserId(10);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertEquals(10, dictonaryDBSource.getDictonaryId(dictionary));
    }

    /**
     * Unit-тест метода getDictonaryId: проверяет его неправильную работу
     */
    @Test
    public void testNotGetDictonaryId() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        Dictionary dictionary = new Dictionary();
        dictionary.setUserId(10);

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertEquals(-1, dictonaryDBSource.getDictonaryId(dictionary));
    }
}