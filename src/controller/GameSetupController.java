package controller;

import model.RiskGame;
import utilities.Config;
import view.screens.GamePlayFrame;
import view.ui_components.GameSetupPanel;

import static model.RiskGame.getInstance;
import static view.helpers.UIHelper.setDivider;

/**
 * The StartupController class
 */
public class GameSetupController {
    private GameSetupPanel gameSetupPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    


    /* Constructors */
    
    /**
     * Constructor for the GameSetup Controller
     * responsible for setting up the number of players before starting the game
     *
     * @param gamePlayFrame the main frame
     */
    public GameSetupController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        gameSetupPanel = new GameSetupPanel();
        
        gamePlayFrame.getContentPane().setRightComponent(gameSetupPanel);
        setDivider(gamePlayFrame.getContentPane());
        riskGame = getInstance();
        
        /* Register Observer to Observable */
        riskGame.addObserver(gameSetupPanel);
        
        /* Register to be ActionListeners */
        gameSetupPanel.addPlayButtonListener(e -> new StartupController(this.gamePlayFrame));

        /* set control panel */
        populateStartupPanel();
    }

    /* Private methods */
    
    /**
     * Populate the startup control panel with updated model data
     */
    private void populateStartupPanel() {
        /* set the maximum players box */
        gameSetupPanel.setMaxPlayersLabel(riskGame.getGameMap().getMaxPlayers());
    }
    
    private void openPlayGameScreen() {
        /* initialize the game */
        RiskGame.getInstance().initializeNewGame(Config.DEFAULT_MAP, Config.DEFAULT_NUM_OF_PLAYERS);
        // new GamePlayController(this);
    }
}
