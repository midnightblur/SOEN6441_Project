import controller.MainGameController;

/**
 * Main driver class.
 */
public class Driver {
    /**
     * The game starts by creating an instance of the RiskGame:
     * <ul>
     * <li> Initially the game is by default set to ENTRY_PHASE
     * </ul>
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // make a game (the game is a singleton)
//        RiskGame game = RiskGame.getInstance();
//
//        /* these are only tests that put the game in a specific state, in reality the game will start from ENTRY_PHASE */
//        game.setGameState(Config.GAME_STATES.STARTUP_PHASE);
//        new GamePlayController(new PlayUI(), RiskGame.getInstance());
//
//        game.setGameState(Config.GAME_STATES.MAP_EDITOR);
//        //game.setGameState(Config.GAME_STATES.ATTACK_PHASE);
//        new MapEditorController();
    
        MainGameController mainGameController = new MainGameController();
    }
}
