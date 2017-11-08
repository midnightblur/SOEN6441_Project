/*
 * Risk Game Team 2
 * AllTestSuite.java
 * Version 1.0
 * Oct 18, 2017
 */

//import game_play.model.GamePlayModelTest;

import map_editor.model.MapEditorModelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import shared_resources.game_entities.*;
import shared_resources.helper.GameMapHelperTest;

/**
 * The Class AllTestSuite.
 */
@RunWith(Suite.class)
@SuiteClasses({
        GameMapHelperTest.class,
        ContinentTest.class,
        DiceTest.class,
        MapEditorModelTest.class,
        PlayerTest.class,
        GameMapTest.class,
//        GamePlayModelTest.class,
        TerritoryTest.class,
})

public class AllTestSuite {
}