/*
 * Risk Game Team 2
 * SavedStateTest.java
 * Version 3.0
 * Nov 22, 2017
 */
package shared_resources.utilities;

import game_play.model.GamePlayModel;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import tests_resources.FixedGamePlayModel;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Testing the saving and loading of a game model
 *
 * @author Team 2
 * @version 1.0
 */
public class SavedStateTest {
    private static String fileName = "testObject.game";
    
    /**
     * Clean-up by removing the saved file
     */
    @AfterClass
    public static void cleanUp() {
        new File(fileName).delete();
    }
    
    /**
     * Make a fixed game model
     * Set the current player to the last one (there are 2 total players)
     * Set the army value to 999
     * Serialize and save the game to file
     */
    @Before
    public void setUp() {
        GamePlayModel mockObject = FixedGamePlayModel.getFixedGamePlayModel();
        mockObject.setCurrentPlayer(mockObject.getPlayers().lastElement());
        mockObject.setArmyValue(999);
        SavedState.saveGame(mockObject, fileName);
        System.out.println("A game was saved with an army value of \"999\" and current player as to \"Player 2\"");
    }
    
    /**
     * Load the game from file
     * Test if the current player is the last one ("Player 2")
     * Test if the army value is 999
     */
    @Test
    public void loadGame() {
        GamePlayModel loadedObject = SavedState.loadGame(fileName);
        System.out.println("We load the saved game from file and validate that the army value is \"999\" and current player is \"Player 2\"");
        assertEquals(loadedObject.getCurrentPlayer().getPlayerName(), "Player 2");
        assertEquals(loadedObject.getArmyValue(), 999);
    }
}