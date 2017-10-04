package controller;

import model.GameStates;
import view.PlayUI;

/**
 * Game State Manager helps drive the flow of the game from stage to stage
 * It is implemented as a singleton class
 */
public class GameStateManager {
    
    /**
     * Initial instance of the game
     */
    private static GameStateManager instance = null;
    
    /**
     * The initial state of the game
     */
    private GameStates gameState = GameStates.ENTRY_MENU;
    
    /* Objects for each state game controller */
    private EntryMenu entryMenu;
    private MapEditor mapEditor;
    private StartupPhase startupPhase;
    private ReinforcementPhase reinforcementPhase;
    private AttackPhase attackPhase;
    private FortificationPhase fortificationPhase;
    
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
    private GameStateManager() {
    }
    
    /**
     * Static instance method to determine if an object of GameStateManager already exists
     *
     * @return instance of the singleton object
     */
    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }
    
    /**
     * Updating the game by switching to respective controller
     */
    public void update() {
        switch (gameState) {
            case ENTRY_MENU:
                if (entryMenu == null) {
                    entryMenu = new EntryMenu();
                }
                break;
            case MAP_EDITOR:
                if (mapEditor == null) {
                    mapEditor = new MapEditor();
                }
                break;
            case STARTUP_PHASE:
                if (startupPhase == null) {
                    startupPhase = new StartupPhase(new PlayUI());
                }
                break;
            case REINFORCEMENT_PHASE:
                if (reinforcementPhase == null) {
                    reinforcementPhase = new ReinforcementPhase();
                }
                break;
            case ATTACK_PHASE:
                if (attackPhase == null) {
                    attackPhase = new AttackPhase();
                }
                break;
            case FORTIFICATION_PHASE:
                if (fortificationPhase == null) {
                    fortificationPhase = new FortificationPhase();
                }
                break;
        }
    }
    
}
