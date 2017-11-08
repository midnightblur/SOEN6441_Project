import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;

import java.util.Map;

import static shared_resources.utilities.Config.GAME_STATES.STARTUP;
import static shared_resources.utilities.Config.log;

public class FixedGamePlayModel {
    public static GamePlayModel getFixedGamePlayModel() {
        /** The game play game_entities. */
        final GamePlayModel fixedGamePlayModel = new GamePlayModel();
    
        /** The num of players. */
        final int numOfPlayers = 2;
    
        /** The map file path. */
        final String mapFilePath = "World.map";
        
        try {
            fixedGamePlayModel.setGameMap(GameMapHelper.loadGameMap(mapFilePath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
        
        return fixedGamePlayModel;
    }
}

