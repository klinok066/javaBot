package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.db.bll.UsersDBSource;
import org.matmech.db.models.Users;
import org.matmech.db.repository.DBConnection;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestUsersDBSource extends TestCase {
    private final DBConnection dbConnection = Mockito.mock(DBConnection.class);
    private final UsersDBSource usersDBSource = new UsersDBSource();

    @Test
    public void testGetUserIdByTag() {
        try {
            ArrayList<HashMap<String, String>> response  = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> userResponse = new HashMap<String, String>();
            userResponse.put("id", "10");
            userResponse.put("tag", "userTag");
            response.add(userResponse);

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Users users = new Users();
            users.setTag("iffomko");

            assertEquals(10, usersDBSource.getUserIdByTag(users, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тестирование провалено!\n" + e.getMessage());
        }
    }

    @Test
    public void testNotGetUserIdByTag() {
        try {
            ArrayList<HashMap<String, String>> response  = new ArrayList<HashMap<String, String>>();

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Users users = new Users();
            users.setTag("iffomko");

            assertEquals(10, usersDBSource.getUserIdByTag(users, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тестирование провалено!\n" + e.getMessage());
        }
    }

    @Test
    public void testNotRegUser() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> userResponse = new HashMap<String, String>();
            userResponse.put("id", "10");
            userResponse.put("firstname", "саша");
            userResponse.put("surname", "иванов");
            userResponse.put("tag", "userTag");
            response.add(userResponse);

            Users users = new Users();
            users.setTag("userTag");
            users.setFirstname("саша");
            users.setSurname("иванов");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertFalse(usersDBSource.regUser(users, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тестирование првоалено!\n" + e.getMessage());
        }
    }

    @Test
    public void testRegUser() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            Users users = new Users();
            users.setTag("userTag");
            users.setFirstname("саша");
            users.setSurname("иванов");

            when(dbConnection.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            assertTrue(usersDBSource.regUser(users, dbConnection));
        } catch (SQLException e) {
            System.out.println("Тестирование провалено!\n" + e.getMessage());
        }
    }

    @Test
    public void testGetAllUsers() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            HashMap<String, String> usersResponse = new HashMap<String, String>();

            usersResponse.put("id", "10");
            usersResponse.put("саша", "саша");
            usersResponse.put("иванов", "иванов");
            usersResponse.put("tag", "userTag");

            response.add(usersResponse);

            when(dbConnection.executeQuery(any(String.class))).thenReturn(response);

            usersDBSource.getAllUsers(dbConnection);
        } catch (SQLException e) {
            System.out.println("Тестирование провалено!\n" + e.getMessage());
        }
    }
}
