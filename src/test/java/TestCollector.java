import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import TestCommands.TestRequestHandler;
import testServiceClasses.TestDictonaryDBSource;
import testServiceClasses.TestGroupsDBSource;
import testServiceClasses.TestUsersDBSource;
import testServiceClasses.TestWordsDBSource;

@Suite.SuiteClasses({
        TestRequestHandler.class,
        TestGroupsDBSource.class,
        TestUsersDBSource.class,
        TestDictonaryDBSource.class,
        TestWordsDBSource.class
})
@RunWith(Suite.class)
public class TestCollector {}