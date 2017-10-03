package controller;

import view.PlayUI;

/**
 * Game State Manager helps drive the flow of the game from stage to stage
 */
public class GameStateManager {
    
    /**
     * The game states
     */
    public enum GameState {
        ENTRY_MENU, MAP_EDITOR, STARTUP_PHASE, REINFORCEMENT_PHASE, ATTACK_PHASE, FORTIFICATION_PHASE
    }
    
    /**
     * Objects for each state game controller
     */
    public static GameState gameState = GameState.ENTRY_MENU;   // initial game state
    public static EntryMenu entryMenu;
    public static MapEditor mapEditor;
    public static StartupPhase startupPhase;
    public static ReinforcementPhase reinforcementPhase;
    public static AttackPhase attackPhase;
    public static FortificationPhase fortificationPhase;
    
    /**
     * Updating the game by switching to respective controller
     */
    public static void update() {
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
