package controller;

import model.game_entities.GameMap;
import model.ui_models.GamePlayModel;
import view.screens.GamePlayFrame;

import static utilities.Config.GAME_STATES.SETUP_PHASE;

/**
 * This class is used as a controller to read and set map filepath
 * to the model, and dispatchToController
 * the view to displayJFrame the map.
 *
 * @author
 * @version 1.0
 */
public class GamePlayController {
    // region Attributes declaration
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    // endregion
    
    // region Constructors
    
    /**
     * Constructor for the mainGameController
     * responsible for launching the game phases
     *
     * @param gameMap The already loaded map for the game
     */
    public GamePlayController(GameMap gameMap) {
        gamePlayFrame = new GamePlayFrame();
        gamePlayModel = GamePlayModel.getInstance();
    
        /* set the map to loaded map and update the model */
        gamePlayModel.setGameMap(gameMap);
        gamePlayModel.getMapTableModel().updateMapTableModel(gameMap);
        
        // put the game in initial state
        gamePlayModel.setGameState(SETUP_PHASE);
        
        /* set the model for the main table */
        gamePlayFrame.getGameMapTable().setModel(gamePlayModel.getMapTableModel().getModel());
        
        setControlPanel();
        
        /* Register Observer to Observable */
        gamePlayModel.getMapTableModel().addObserver(gamePlayFrame.getGameMapTable());
        
        /* Register to be ActionListeners */
        // TODO: put the back in a menu
        //gamePlayFrame.getControlPanel().addBackButtonListener(e -> backToMainMenu());
    }
    // endregion
    
    // region Private methods
    
    /**
     * Setting the control panel area depending on the state of the game
     * and instantiating respective controller
     */
    private void setControlPanel() {
        switch (GamePlayModel.getInstance().getGameState()) {
            case SETUP_PHASE:
                new GameSetupController(gamePlayFrame);
                break;
            case STARTUP_PHASE:
                new PhaseStartupController(gamePlayFrame);
                break;
            case REINFORCEMENT_PHASE:
                new PhaseReinforcementController(gamePlayFrame);
                break;
            case FORTIFICATION_PHASE:
                new PhaseFortificationController(gamePlayFrame);
                break;
            case TRADE_IN_PHASE:
                new TradeCardsController(gamePlayFrame);
                break;
            case ATTACK_PHASE:
                // TODO: make attack phase view and controller
                break;
        }
    }
    // endregion
}
