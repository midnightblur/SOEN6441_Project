package controller;

import model.RiskGame;
import model.game_entities.Player;
import model.ui_models.StartupModel;
import view.screens.GamePlayFrame;
import view.ui_components.StartupPanel;

import static model.RiskGame.getInstance;
import static utilities.Config.GAME_STATES.STARTUP_PHASE;
import static view.helpers.UIHelper.setDivider;

/**
 * The StartupController class
 */
public class StartupController {
    private StartupPanel startupPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private Player currentPlayer;
    private StartupModel startupModel;


    /* Constructors */
    
    /**
     * Constructor for the Startup Controller
     * responsible for placing an army to a player territory
     *
     * @param gamePlayFrame the main frame
     */
    public StartupController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        startupPanel = new StartupPanel();
        startupModel = new StartupModel();
        
        gamePlayFrame.getContentPane().setRightComponent(startupPanel);
        setDivider(gamePlayFrame.getContentPane());
        riskGame = getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        
        riskGame.setGameState(STARTUP_PHASE);

        /* Register Observer to Observable */
        riskGame.addObserver(startupPanel);
        currentPlayer.addObserver(startupPanel);
        startupModel.addObserver(startupPanel);

        /* Register to be ActionListeners */
        startupPanel.addDoneButtonListener(e -> nextPlayer());
        startupPanel.addPlaceArmyButtonListener(e -> placeArmy());

        /* set control panel */
        populateStartupPanel();
    }

    /* Private methods */
    
    /**
     * Populate the startup control panel with updated model data
     */
    private void populateStartupPanel() {
        /* set the phase label */
        startupPanel.setGameState(riskGame.getGameState());

        /* set the player ID label */
        startupPanel.setPlayerID(currentPlayer.getPlayerID());

        /* set the unallocated armies */
        startupPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());

        /* set the source dropdown */
        startupPanel.getTerritoryDropdown().setModel(startupModel.getTerritoriesList());
        startupPanel.getDoneButton().setEnabled(false);
        startupPanel.getPlaceArmiesButton().setEnabled(true);
    }
    
    /**
     * Place an army to the selected territory.
     */
    private void placeArmy() {
        String territory = startupPanel.getTerritoryDropdown().getSelectedItem().toString();
        if (territory != null || !territory.equals("")) {
            startupPanel.getPlaceArmiesButton().setEnabled(false);
            riskGame.placeArmy(territory);
            riskGame.getMapTableModel().updateMapTableModel(riskGame.getGameMap());
            startupPanel.getDoneButton().setEnabled(true);
        } else {
            gamePlayFrame.displayMessage("Please validate your selection.");
        }
    }
    
    /**
     * Advance the game to next player
     */
    public void nextPlayer() {
        riskGame.setCurrPlayerToNextPlayer();
        if (riskGame.getPlayers().lastElement().getUnallocatedArmies() > 0) {
            new StartupController(this.gamePlayFrame);
        } else {
            riskGame.reinforcementPhase();
            new ReinforcementController(this.gamePlayFrame);
        }
    }
    
}
