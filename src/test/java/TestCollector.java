import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import TestCommands.TestRequestHandler;
import testServiceClasses.TestGroupsDBSource;
import testServiceClasses.TestUsersDBSource;

@Suite.SuiteClasses({ TestRequestHandler.class, TestGroupsDBSource.class })
@RunWith(Suite.class)
public class TestCollector {}