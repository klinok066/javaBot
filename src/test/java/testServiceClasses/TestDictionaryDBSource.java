package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Assert;
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

public class TestDictionaryDBSource {
    private final DBConnection dbConnection = Mockito.mock(DBConnection.class);
    private final DictonaryDBSource dictonaryDBSource = new DictonaryDBSource(dbConnection);

    /**
     * Unit-тест метода createDictonary: проверяет его правильную работу
     */
    @Test
    public void testCreateDictonary() {
        Assert.assertTrue(dictonaryDBSource.createDictonary(12));
    }

    /**
     * Unit-тест метода createDictonary: проверяет его неправильную работу
     */
    @Test
    public void testNotCreateDictonary() {
        Assert.assertFalse(dictonaryDBSource.createDictonary(-1));
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

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        Assert.assertEquals(10, dictonaryDBSource.getDictonaryId(10));
    }

    /**
     * Unit-тест метода getDictonaryId: проверяет его неправильную работу
     */
    @Test
    public void testNotGetDictonaryId() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        Assert.assertEquals(-1, dictonaryDBSource.getDictonaryId(10));
    }
}
