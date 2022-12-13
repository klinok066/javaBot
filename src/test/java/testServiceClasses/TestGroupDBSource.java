package testServiceClasses;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.matmech.db.bll.GroupsDBSource;
import org.matmech.db.models.Group;
import org.matmech.db.repository.DBConnection;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestGroupDBSource {
    private final DBConnection dbConnectionMock = Mockito.mock(DBConnection.class);
    private final GroupsDBSource groupsDBSource = new GroupsDBSource(dbConnectionMock);

    /**
     * Unit-тест метода createGroup: проверяет его неправильную работу
     */
    @Test
    public void testNotCreateGroup() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();
        Map<String, String> responseItem = new HashMap<String, String>();
        responseItem.put("title", "глаголы");
        response.add(responseItem);

        when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        Group group = new Group();

        group.setTitle("глаголы");
        group.setDictonaryId(123);

        Assert.assertFalse(groupsDBSource.createGroup(group));
    }

    /**
     * Unit-тест метода createGroup: проверяет его правильную работу
     */
    @Test
    public void testCreateGroup() {
        try {
            List<Map<String, String>> response = new ArrayList<Map<String, String>>();

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Group group = new Group();

            group.setTitle("глаголы");
            group.setDictonaryId(123);

            Assert.assertTrue(groupsDBSource.createGroup(group));
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
            List<Map<String, String>> response = new ArrayList<Map<String, String>>();
            Map<String, String> item = new HashMap<String, String>();
            item.put("id", "3");
            item.put("title", "глаголы");
            item.put("dictonary_id", "123");
            response.add(item);

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Group group = new Group();

            group.setTitle("глаголы");
            group.setDictonaryId(123);

            Assert.assertEquals(3, groupsDBSource.getGroupId(group));
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
            List<Map<String, String>> response = new ArrayList<Map<String, String>>();

            when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

            Group group = new Group();

            group.setTitle("глаголы");
            group.setDictonaryId(123);

            Assert.assertEquals(-1, groupsDBSource.getGroupId(group));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Unit-тест метода getGroupTitle: проверяет его правильную работу
     */
    @Test
    public void testGetGroupTitle() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();
        Map<String, String> item = new HashMap<String, String>();
        item.put("id", "3");
        item.put("title", "глаголы");
        item.put("dictonary_id", "123");
        response.add(item);

        when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        Group group = new Group();

        group.setId(3);
        group.setDictonaryId(123);

        Assert.assertEquals("глаголы", groupsDBSource.getGroupTitle(group));
    }

    /**
     * Unit-тест метода getGroupTitle: проверяет его неправильную работу
     */
    @Test
    public void testNotGetGroupTitle() throws SQLException {
        List<Map<String, String>> response = new ArrayList<Map<String, String>>();

        when(dbConnectionMock.executeQueryWithParams(any(String.class), any(ArrayList.class))).thenReturn(response);

        Group group = new Group();

        group.setId(3);
        group.setDictonaryId(123);

        Assert.assertNull(groupsDBSource.getGroupTitle(group));
    }
}
