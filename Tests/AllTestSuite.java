//JUnit 4 test suite

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        ControllerTestSuite.class,
        ModelTestSuite.class
})

public class AllTestSuite {
}  