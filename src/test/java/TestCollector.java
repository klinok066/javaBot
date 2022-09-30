import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import TestCommands.TestRequestHandler;

@Suite.SuiteClasses({ TestRequestHandler.class })
@RunWith(Suite.class)
public class TestCollector {}