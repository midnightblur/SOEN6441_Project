package controller;

import model.GamePlayModel;
import utilities.Config;
import view.screens.GamePlayFrame;
import view.ui_components.GameSetupPanel;

import static model.GamePlayModel.getInstance;
import static view.helpers.UIHelper.setDivider;

/**
 * The StartupController class
 */
public class GameSetupController {
    private GameSetupPanel gameSetupPanel;
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    


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
        gamePlayModel = getInstance();
        
        /* Register Observer to Observable */
        gamePlayModel.addObserver(gameSetupPanel);
        
        /* Register to be ActionListeners */
        gameSetupPanel.addPlayButtonListener(e -> new PhaseStartupController(this.gamePlayFrame));

        /* set control panel */
        populateStartupPanel();
    }

    /* Private methods */
    
    /**
     * Populate the startup control panel with updated model data
     */
    private void populateStartupPanel() {
        /* set the maximum players box */
        gameSetupPanel.setMaxPlayersLabel(gamePlayModel.getGameMap().getMaxPlayers());
    }
    
    private void openPlayGameScreen() {
        /* initialize the game */
        GamePlayModel.getInstance().initializeNewGame(Config.DEFAULT_MAP, Config.DEFAULT_NUM_OF_PLAYERS);
        // new GamePlayController(this);
    }
}
