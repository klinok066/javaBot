package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.db.bll.UsersDBSource;
import org.matmech.db.models.User;
import org.matmech.db.repository.DBConnection;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestUserDBSource extends TestCase {
    private final DBConnection dbConnection = Mockito.mock(DBConnection.class);
    private final UsersDBSource usersDBSource = new UsersDBSource(dbConnection);

    /**
     * Unit-тест метода getUserIdByTag: проверяет его правильную работу
     */
    @Test
    public void testGetUserIdByTag() throws SQLException {
        List<Map<String, String>> response  = new ArrayList<Map<String, String>>();
        Map<String, String> userResponse = new HashMap<String, String>();
        userResponse.put("id", "10");
        userResponse.put("tag", "userTag");
        response.add(userResponse);

        when(dbConnection.executeQueryWithParams(any(String.class), any(List.class))).thenReturn(response);

        User user = new User();
        user.setTag("iffomko");

        assertEquals(10, usersDBSource.getUserIdByTag(user));
    }

    /**
     * Unit-тест метода getUserIdByTag: проверяет его неправильную работу
     */
    @Test
    public void testNotGetUserIdByTag() throws SQLException {
        List<Map<String, String>> response  = new ArrayList<Map<String, String>>();

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        User user = new User();
        user.setTag("iffomko");

        assertEquals(-1, usersDBSource.getUserIdByTag(user));
    }

    /**
     * Unit-тест метода regUser: проверяет его неправильную работу
     */
    @Test
    public void testNotRegUser() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();
        Map<String, String> userResponse = new HashMap<String, String>();
        userResponse.put("id", "10");
        userResponse.put("firstname", "саша");
        userResponse.put("surname", "иванов");
        userResponse.put("tag", "userTag");
        response.add(userResponse);

        User user = new User();
        user.setTag("userTag");
        user.setFirstname("саша");
        user.setSurname("иванов");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertFalse(usersDBSource.regUser(user));
    }

    /**
     * Unit-тест метода regUser: проверяет его правильную работу
     */
    @Test
    public void testRegUser() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        User user = new User();
        user.setTag("userTag");
        user.setFirstname("саша");
        user.setSurname("иванов");

        when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        assertTrue(usersDBSource.regUser(user));
    }

    /**
     * Unit-тест метода getAllUser: проверяет его правильную работу
     */
    @Test
    public void testGetAllUsers() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        Map<String, String> usersResponse = new HashMap<String, String>();

        usersResponse.put("id", "10");
        usersResponse.put("firstname", "саша");
        usersResponse.put("surname", "иванов");
        usersResponse.put("tag", "userTag");

        response.add(usersResponse);

        when(dbConnection.executeQuery(any(String.class))).thenReturn(response);

        usersDBSource.getAllUsers();
    }
}
