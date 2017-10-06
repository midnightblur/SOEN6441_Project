
import controller.*;
import model.*;
import view.PlayUI;


/**
 * Main driver class.
 */
public class Driver {
    /**
     * The game starts by creating an instance of the Game:
     * <list>
     * <li> Initially the game is by default set to ENTRY_PHASE
     * </list>
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // make a game (the game is a singleton)
        // TODO: merge the Game and RiskGame classes (probably make RiskGame as singleton) and accept GameStates
        Game game = Game.getInstance();

        /* these are only tests that put the game in a specific state, in reality the game will start from ENTRY_PHASE */
        //newGame.setGameState(GameStates.MAP_EDITOR);
        new MapEditorController();
        game.setGameState(GameStates.STARTUP_PHASE);
        
        //new StartupPhase(new PlayUI(), new RiskGame());
    }
}
