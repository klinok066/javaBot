package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Test;
import org.matmech.db.bll.GroupsDBSource;
import org.matmech.db.models.Groups;
import org.matmech.db.repository.DBConnection;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestGroupsDBSource extends TestCase {
    private final DBConnection dbConnectionMock = Mockito.mock(DBConnection.class);
    private final GroupsDBSource groupsDBSource = new GroupsDBSource();

    /**
     * Unit-тест метода createGroup: проверяет его неправильную работу
     */
    @Test
    public void testNotCreateGroup() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> responseItem = new HashMap<String, String>();
            responseItem.put("title", "глаголы");
            response.add(responseItem);

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Groups groups = new Groups();

            groups.setTitle("глаголы");
            groups.setDictonaryId(123);

            assertFalse(groupsDBSource.createGroup(groups, dbConnectionMock));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Unit-тест метода createGroup: проверяет его правильную работу
     */
    @Test
    public void testCreateGroup() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Groups groups = new Groups();

            groups.setTitle("глаголы");
            groups.setDictonaryId(123);

            assertTrue(groupsDBSource.createGroup(groups, dbConnectionMock));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Unit-тест метода getGruopId: проверяет его правильную работу
     */
    @Test
    public void testGetGroupId() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("id", "3");
            item.put("title", "глаголы");
            item.put("dictonary_id", "123");
            response.add(item);

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Groups groups = new Groups();

            groups.setTitle("глаголы");
            groups.setDictonaryId(123);

            assertEquals(3, groupsDBSource.getGroupId(groups, dbConnectionMock));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Unit-тест метода getGruopId: проверяет его неправильную работу
     */
    @Test
    public void testNotGetGroupId() {
        try {
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Groups groups = new Groups();

            groups.setTitle("глаголы");
            groups.setDictonaryId(123);

            assertEquals(-1, groupsDBSource.getGroupId(groups, dbConnectionMock));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Unit-тест метода getGroupTitle: проверяет его правильную работу
     */
    @Test
    public void testGetGroupTitle(){
        try{
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("id", "3");
            item.put("title", "глаголы");
            item.put("dictonary_id", "123");
            response.add(item);

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Groups groups = new Groups();

            groups.setId(3);
            groups.setDictonaryId(123);

            assertEquals("глаголы", groupsDBSource.getGroupTitle(groups,dbConnectionMock));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Unit-тест метода getGroupTitle: проверяет его неправильную работу
     */
    @Test
    public void testNotGetGroupTitle(){
        try{
            ArrayList<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Groups groups = new Groups();

            groups.setId(3);
            groups.setDictonaryId(123);

            assertNull(groupsDBSource.getGroupTitle(groups, dbConnectionMock));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
