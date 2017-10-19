/* 
 * Risk Game Team 2
 * AllTestSuite.java
 * Version 1.0
 * Oct 18, 2017
 */

import model.*;
import model.helpers.MapValidationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class AllTestSuite.
 */
@RunWith(Suite.class)
@SuiteClasses({
        MapValidationTest.class,
        ContinentTest.class,
        DiceTest.class,
        ReadingInvalidMapFileTest.class,
        GameMapTest.class,
        GameStatesTest.class,
        MapsLoaderTest.class,
        MapEditorModelTest.class,
        PlayerTest.class,
        GamePlayModelTest.class,
        TerritoryTest.class,
})

public class AllTestSuite {
}  