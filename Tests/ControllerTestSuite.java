//JUnit 4 test suite

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        controller.GameStateManagerTest.class,
        controller.GameTest.class,
        controller.MapEditorControllerTest.class,
        controller.StartupPhaseTest.class
})

public class ControllerTestSuite {
}  