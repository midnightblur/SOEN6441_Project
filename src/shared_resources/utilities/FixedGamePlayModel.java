/*
 * Risk Game Team 2
 * FixedGamePlayModel.java
 * Version 1.0
 * Nov 7, 2017
 */
package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.helper.GameMapHelper;

/**
 * This class is responsible for providing an instance of a GamePlayModel object for testing
 * purposes with a fixed order of the territories distributed to the players during the
 * initialization phase of the game.
 *
 * @author Team 2
 * @version 2.0
 */
public class FixedGamePlayModel {
    // region Attributes
    /** The num of players. */
    final static int numOfPlayers = 2;
    
    /** The map file path. */
    final static String mapFilePath = "3D.map";
    // endregion
    
    // region Public methods
    /**
     * This static method creates an instance of a GamePlayMode object that is initialized with
     * a method to distribute territories in a fixed approach and returns it for the sole purpose
     * of testing.
     *
     * @return GamePlayModel object with fixed distribution of territories
     */
    public static GamePlayModel getFixedGamePlayModel() {
        GamePlayModel fixedGamePlayModel = new GamePlayModel();
        
        try {
            fixedGamePlayModel.setGameMap(GameMapHelper.loadGameMap(mapFilePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        fixedGamePlayModel.fixedInitializeNewGame(numOfPlayers);
        
        return fixedGamePlayModel;
    }
    // endregion
}

