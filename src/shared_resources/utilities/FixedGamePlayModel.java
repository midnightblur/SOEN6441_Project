package shared_resources.utilities;

import game_play.model.GamePlayModel;
import shared_resources.helper.GameMapHelper;

/**
 * This class is responsible for providing an instance of a GamePlayModel object for testing
 * purposes with a fixed order of the territories distributed to the players during the
 * initialization phase of the game.
 */
public class FixedGamePlayModel {
    
    /** The game play game_entities. */
    final static GamePlayModel fixedGamePlayModel = new GamePlayModel();
    
    /** The num of players. */
    final static int numOfPlayers = 2;
    
    /** The map file path. */
    final static String mapFilePath = "3D.map";
    
    /**
     * This static method creates an instance of a GamePlayMode object that is initialized with
     * a method to distribute territories in a fixed approach and returns it for the sole purpose
     * of testing.
     *
     * @return GamePlayModel object with fixed distribution of territories
     */
    public static GamePlayModel getFixedGamePlayModel() {
        
        try {
            fixedGamePlayModel.setGameMap(GameMapHelper.loadGameMap(mapFilePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        fixedGamePlayModel.fixedInitializeNewGame(numOfPlayers);
        
        return fixedGamePlayModel;
    }
}

