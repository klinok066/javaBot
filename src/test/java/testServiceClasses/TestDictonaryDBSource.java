package testServiceClasses;

import junit.framework.TestCase;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.matmech.db.bll.DictonaryDBSource;
import org.matmech.db.models.Dictonary;
import org.matmech.db.repository.DBConnection;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestDictonaryDBSource extends TestCase {
    private final DBConnection dbConnection = Mockito.mock(DBConnection.class);
    private final DictonaryDBSource dictonaryDBSource = new DictonaryDBSource();

    @Test
    public void testCreateDictonary() {
        Dictonary dictonary = new Dictonary();
        dictonary.setUserId(12);

        assertTrue(dictonaryDBSource.createDictonary(dictonary, dbConnection));
    }

    @Test
    public void testNotCreateDictonary() {
        Dictonary dictonary = new Dictonary();
        dictonary.setUserId(-1);

        assertFalse(dictonaryDBSource.createDictonary(dictonary, dbConnection));
    }

    @Test
    public void testGetDictonaryId() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            HashMap<String, String> dictonaryResponse = new HashMap<String, String>();
            dictonaryResponse.put("id", "10");

            response.add(dictonaryResponse);

            Dictonary dictonary = new Dictonary();
            dictonary.setUserId(10);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertEquals(10, dictonaryDBSource.getDictonaryId(dictonary, dbConnection));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNotGetDictonaryId() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            Dictonary dictonary = new Dictonary();
            dictonary.setUserId(10);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertEquals(-1, dictonaryDBSource.getDictonaryId(dictonary, dbConnection));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
