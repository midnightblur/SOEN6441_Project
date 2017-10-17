package controller;

import model.ui_models.GamePlayModel;
import view.screens.GamePlayFrame;

import static utilities.Config.GAME_STATES.STARTUP_PHASE;

/**
 * Controller to read and set map filepath to the model, and dispatchToController
 * the view to displayJFrame the map.
 */
public class GamePlayController {
    // region Attributes declaration
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    private MainMenuController callerController;
    // endregion
    
    // region Constructors
    /**
     * Constructor for the mainGameController
     * responsible of launching the game phases
     *
     * @param mainMenuController the caller to be used to go back
     */
    public GamePlayController(MainMenuController mainMenuController) {
        callerController = mainMenuController;
        gamePlayFrame = new GamePlayFrame();
        gamePlayModel = GamePlayModel.getInstance();
        
        // put the game in initial state
        gamePlayModel.setGameState(STARTUP_PHASE);
        
        /* set the model for the main table */
        gamePlayFrame.getGameGameMapTable().setModel(gamePlayModel.getMapTableModel().getModel());
        
        /* Register Observer to Observable */
        gamePlayModel.getMapTableModel().addObserver(gamePlayFrame.getGameGameMapTable());
        
        /* Register to be ActionListeners */
        // TODO: put the back in a menu
        //gamePlayFrame.getControlPanel().addBackButtonListener(e -> backToMainMenu());
    }
    // endregion
    
    // region Private methods
//    /**
//     * Setting the control panel area depending on the state of the game
//     * and instantiating respective controller
//     */
//    private void setControlPanel() {
//        switch (GamePlayModel.getInstance().getGameState()) {
//            case STARTUP_PHASE:
//                new PhaseStartupController(gamePlayFrame);
//                break;
//            case REINFORCEMENT_PHASE:
//                new PhaseReinforcementController(gamePlayFrame);
//                break;
//            case FORTIFICATION_PHASE:
//                new PhaseFortificationController(gamePlayFrame);
//                break;
//            case TRADE_IN_PHASE:
//                new TradeCardsController(gamePlayFrame);
//                break;
//            case ATTACK_PHASE:
//                // TODO: make attack phase view and controller
//                break;
//        }
//    }
    // endregion
}
