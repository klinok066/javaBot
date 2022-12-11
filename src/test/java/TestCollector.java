import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import TestCommands.TestRequestHandler;
import testServiceClasses.TestDictionaryDBSource;
import testServiceClasses.TestGroupDBSource;
import testServiceClasses.TestUserDBSource;
import testServiceClasses.TestWordDBSource;

@Suite.SuiteClasses({
        TestRequestHandler.class,
        TestGroupDBSource.class,
        TestUserDBSource.class,
        TestDictionaryDBSource.class,
        TestWordDBSource.class
})
@RunWith(Suite.class)
public class TestCollector {}