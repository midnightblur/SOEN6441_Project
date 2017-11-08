import game_play.model.GamePlayModel;
import shared_resources.helper.GameMapHelper;

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
        
        fixedGamePlayModel.fixedInitializeNewGame(numOfPlayers);
        
        return fixedGamePlayModel;
    }
}

