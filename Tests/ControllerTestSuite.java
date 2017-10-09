//JUnit 4 test suite

import controller.StartupPhaseControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        controller.GameStateManagerTest.class,
        controller.GameTest.class,
        controller.MapEditorControllerTest.class,
        StartupPhaseControllerTest.class
})

public class ControllerTestSuite {
}  