//JUnit 4 test suite

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        model.ContinentTest.class,
        model.DiceTest.class,
        model.GameMapHelperTest.class,
        model.GameMapTest.class,
        model.GameStatesTest.class,
        model.MapsLoaderTest.class,
        model.MapEditorModelTest.class,
        model.PlayerTest.class,
        model.RiskGameTest.class,
        model.TerritoryTest.class,
})

public class AllTestSuite {
}  