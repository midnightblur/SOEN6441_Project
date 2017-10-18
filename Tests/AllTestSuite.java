//JUnit 4 test suite

import model.GamePlayModelTest;
import model.ReadingInvalidMapFileTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        model.ContinentTest.class,
        model.DiceTest.class,
        ReadingInvalidMapFileTest.class,
        model.GameMapTest.class,
        model.GameStatesTest.class,
        model.MapsLoaderTest.class,
        model.MapEditorModelTest.class,
        model.PlayerTest.class,
        GamePlayModelTest.class,
        model.TerritoryTest.class,
})

public class AllTestSuite {
}  