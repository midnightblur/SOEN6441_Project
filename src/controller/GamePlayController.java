package controller;

import model.RiskGame;
import model.ui_models.MapTableModel;
import utilities.Config;
import view.screens.GamePlayFrame;

/**
 * Controller to read and set map filepath to the model, and dispatchToController
 * the view to displayJFrame the map.
 */
public class GamePlayController {
    private GamePlayFrame gamePlayFrame;
    private MapTableModel mapTableModel;
    private RiskGame riskGame;
    private MainGameController callerController;
    
    /* Constructors */
    
    /**
     * Constructor for the mainGameController responsible of launching the game phases
     *
     * @param mainGameController the caller to be used to go back
     */
    public GamePlayController(MainGameController mainGameController) {
        callerController = mainGameController;
        gamePlayFrame = new GamePlayFrame();
        riskGame = RiskGame.getInstance();
        mapTableModel = riskGame.getMapTableModel();
        
        // TODO: the following method calls should be moved to be part of StartupListener class which implements the ActionListener class
        /*
        initStartUp should only take 1 param like 'new StartupListener()'.
        Config.DEFAULT_MAP should be retrieved from the view's filepath value,
        default number of players should be retrieved from the view's number of players value
         */
        riskGame.startupPhase(Config.DEFAULT_MAP, Config.DEFAULT_NUM_OF_PLAYERS);
        
        // set the Control Panel to proper frame depending on the game state
        setControlPanel();
        
        /* update the table model from a loaded map */
        try {
            mapTableModel.updateMapTableModel(riskGame.getGameMap());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            gamePlayFrame.displayErrorMessage(e.toString());
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
