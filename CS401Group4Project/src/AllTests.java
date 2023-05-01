import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
    UserTesting.class, MessageTest.class, MessageThreadTest.class
})
public class AllTests {
}
