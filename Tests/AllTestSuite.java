/* 
 * Risk Game Team 2
 * AllTestSuite.java
 * Version 1.0
 * Oct 18, 2017
 */

import model.GamePlayModelTest;
import model.ReadingInvalidMapFileTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class AllTestSuite.
 */
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