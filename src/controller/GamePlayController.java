package controller;

import model.game_entities.GameMap;
import model.ui_models.GamePlayModel;
import view.helpers.UIHelper;
import view.screens.GamePlayFrame;

import javax.swing.*;

import static model.ui_models.GamePlayModel.getInstance;
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
    private MapSelectorController callerController;
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
    public GamePlayController(MapSelectorController callerController, GameMap gameMap) {
        this.callerController = callerController;
        gamePlayModel = GamePlayModel.getInstance();
        gamePlayFrame = new GamePlayFrame();
    
        registerObserversToObservable();
    
        registerToBeListener();
        
        gamePlayModel.setGameMap(gameMap);
        gamePlayModel.setGameState(SETUP_PHASE);
        
        /* set the model for the main table */
        gamePlayFrame.getGameMapTable().setModel(gamePlayModel.getMapTableModel().getModel());
        
//        setControlPanel();
    }
    // endregion
    
    // region Private methods
    private void registerObserversToObservable() {
        gamePlayModel.addObserver(gamePlayFrame.getGameMapTable());
        gamePlayModel.addObserver(gamePlayFrame.getGameSetupPanel());
        gamePlayModel.addObserver(gamePlayFrame.getStartupPanel());
        gamePlayModel.addObserver(gamePlayFrame.getReinforcementPanel());
        gamePlayModel.addObserver(gamePlayFrame.getFortificationPanel());
    }
    
    private void registerToBeListener() {
        // TODO: put the back in a menu
        //gamePlayFrame.getControlPanel().addBackButtonListener(e -> backToMainMenu());
        gamePlayFrame.getGameSetupPanel().addPlayButtonListener(e -> startTheGame());
    }
    
    private void startTheGame() {
        /* initialize the game */
        int enteredPlayers;
        try {
            enteredPlayers = Integer.parseInt(gamePlayFrame.getGameSetupPanel().getPlayerCount().getText());
            if ((enteredPlayers > 0) && (enteredPlayers <= getInstance().getGameMap().getMaxPlayers())) {
                getInstance().initializeNewGame(getInstance().getGameMap(), enteredPlayers);
                new PhaseStartupController(gamePlayFrame);
            } else {
                UIHelper.displayMessage(gamePlayFrame,"You must enter an amount of players between 1 and " + getInstance().getGameMap().getMaxPlayers());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid entry. Please re-enter a number.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
//    /**
//     * Setting the control panel area depending on the state of the game
//     * and instantiating respective controller
//     */
//    private void setControlPanel() {
//        switch (GamePlayModel.getInstance().getGameState()) {
//            case SETUP_PHASE:
//                new GameSetupController(gamePlayFrame);
//                break;
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
