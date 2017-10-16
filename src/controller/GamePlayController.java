package controller;

import model.RiskGame;
import model.ui_models.MapTableModel;
import view.screens.GamePlayFrame;

import static utilities.Config.GAME_STATES.STARTUP_PHASE;

/**
 * Controller to read and set map filepath to the model, and dispatchToController
 * the view to displayJFrame the map.
 */
public class GamePlayController {
    private GamePlayFrame gamePlayFrame;
    private MapTableModel mapTableModel;
    private RiskGame riskGame;
    private MainMenuController callerController;
    
    /* Constructors */
    
    /**
     * Constructor for the mainGameController
     * responsible of launching the game phases
     *
     * @param mainMenuController the caller to be used to go back
     */
    public GamePlayController(MainMenuController mainMenuController) {
        callerController = mainMenuController;
        gamePlayFrame = new GamePlayFrame();
        riskGame = RiskGame.getInstance();
        mapTableModel = riskGame.getMapTableModel();
        
        // put the game in initial state
        riskGame.setGameState(STARTUP_PHASE);
        
        // set the Control Panel to proper frame depending on the game state
        setControlPanel();
        
        /* update the table model from a loaded map */
        try {
            mapTableModel.updateMapTableModel(riskGame.getGameMap());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            gamePlayFrame.displayMessage(e.toString());
        }
        
        /* set the model for the main table */
        gamePlayFrame.getGameMapTable().setModel(mapTableModel.getModel());
        
        /* Register Observer to Observable */
        mapTableModel.addObserver(gamePlayFrame.getGameMapTable());
        
        /* Register to be ActionListeners */
        // TODO: put the back in a menu
        //gamePlayFrame.getControlPanel().addBackButtonListener(e -> backToMainMenu());
    }
    
    
    /* Private methods */
    
    /**
     * Setting the control panel area depending on the state of the game
     * and instantiating respective controller
     */
    private void setControlPanel() {
        switch (RiskGame.getInstance().getGameState()) {
            case STARTUP_PHASE:
                new StartupController(gamePlayFrame);
                break;
            case REINFORCEMENT_PHASE:
                new ReinforcementController(gamePlayFrame);
                break;
            case FORTIFICATION_PHASE:
                new FortificationController(gamePlayFrame);
                break;
            case TRADE_IN_PHASE:
                new TradeCardsController(gamePlayFrame);
                break;
            case ATTACK_PHASE:
                // TODO: make attack phase view and controller
                break;
        }
    }
    
    
}
