/*
 * Risk Game Team 2
 * AllTestSuite.java
 * Version 1.0
 * Oct 18, 2017
 */

//import game_play.model.GamePlayModelTest;

import game_play.model.GamePlayModelTest;
import game_play.model.TournamentModelTest;
import map_editor.model.MapEditorModelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import shared_resources.game_entities.ContinentTest;
import shared_resources.game_entities.DiceTest;
import shared_resources.game_entities.PlayerTest;
import shared_resources.game_entities.TerritoryTest;
import shared_resources.helper.GameMapHelperTest;
import shared_resources.utilities.SavedStateTest;

/**
 * The Class AllTestSuite used to run unit test classes
 *
 * @author Team 2
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({
        GamePlayModelTest.class,
        MapEditorModelTest.class,
        ContinentTest.class,
        DiceTest.class,
        PlayerTest.class,
        TerritoryTest.class,
        GameMapHelperTest.class,
        SavedStateTest.class,
        TournamentModelTest.class,
})

public class AllTestSuite {
}