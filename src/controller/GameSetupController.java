package controller;

import model.ui_models.GamePlayModel;
import view.screens.GamePlayFrame;
import view.ui_components.GameSetupPanel;

import static model.ui_models.GamePlayModel.getInstance;
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
        gameSetupPanel.addPlayButtonListener(e -> openPlayGameScreen());

        /* set control panel */
        populateStartupPanel();
    }

    /* Private methods */
    
    /**
     * Populate the startup control panel with updated model data
     */
    private void populateStartupPanel() {
        /* set the maximum players box */
        gameSetupPanel.setMaxPlayersLabel(getInstance().getGameMap().getMaxPlayers());
    }
    
    private void openPlayGameScreen() {
        /* initialize the game */
        gamePlayModel.getInstance().initializeNewGame(getInstance().getGameMap(), Integer.parseInt(gameSetupPanel.getPlayerCount().getText()));
        new PhaseStartupController(gamePlayFrame);
    }
}
