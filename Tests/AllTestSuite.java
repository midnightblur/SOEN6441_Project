/* 
 * Risk Game Team 2
 * AllTestSuite.java
 * Version 1.0
 * Oct 18, 2017
 */

import game_play.model.GamePlayModelTest;
import map_editor.model.MapEditorModelTest;
import map_editor.model.MapValidationTest;
import map_editor.model.MapsLoaderTest;
import map_editor.model.ReadingInvalidMapFileTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import shared_resources.game_entities.ContinentTest;
import shared_resources.game_entities.DiceTest;
import shared_resources.game_entities.PlayerTest;
import shared_resources.game_entities.TerritoryTest;

/**
 * The Class AllTestSuite.
 */
@RunWith(Suite.class)
@SuiteClasses({
        MapValidationTest.class,
        ContinentTest.class,
        DiceTest.class,
        ReadingInvalidMapFileTest.class,
        MapsLoaderTest.class,
        MapEditorModelTest.class,
        PlayerTest.class,
        GamePlayModelTest.class,
        TerritoryTest.class,
})

public class AllTestSuite {
}  