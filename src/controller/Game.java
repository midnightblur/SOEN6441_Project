package controller;

import model.GameStates;

/**
 * Game State Manager helps drive the flow of the game from stage to stage
 * It is implemented as a singleton class
 */
public class Game {
    
    /**
     * Initial instance of the game
     */
    private static Game instance = null;
    
    /**
     * The initial state of the game
     */
    private GameStates gameState = GameStates.ENTRY_MENU;
    
    /* Getters and setters */
    public GameStates getGameState() {
        return gameState;
    }
    
    public void setGameState(GameStates gameState) {
        this.gameState = gameState;
    }
    
    /**
     * private constructor preventing any other class from instantiating.
     */
    private Game() {
    }
    
    /**
     * Static instance method to determine if an object of Game already exists
     *
     * @return instance of the singleton object
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
    
}
