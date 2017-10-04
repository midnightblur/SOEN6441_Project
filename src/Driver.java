
import controller.*;
import model.*;


/**
 * Main driver class.
 */
public class Driver {
    
    /**
     * The game starts by creating an instance of the GameStateManager:
     * <list>
     * <li> Initially the game is by default set to ENTRY_PHASE
     * <li> As the user changes the game states, the GameStateManager observes the changes in model
     * and triggers the game update() method, effectively passing the control to respective controller
     * </list>
     *
     * @param args
     */
    public static void main(String[] args) {
        GameStateManager newGame = GameStateManager.getInstance();
        
        /* these are only tests that put the game in a specific state, in reality the game will start from ENTRY_PHASE */
        newGame.setGameState(GameStates.MAP_EDITOR);
        newGame.update();
        
        // views
        //PlayUI playUI = new PlayUI();
        
        // models
        
        // controllers
        
    }
}
