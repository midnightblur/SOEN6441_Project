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
        if (riskGame.getNextPlayer().getUnallocatedArmies() == 0) {
            startupPanel.setDoneButton("Done (to Fortification Phase)");
        }
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
     * Advance the game to next player, or to the reinforcement phase if all the
     * players have exhausted all unallocated armies.
     */
    public void nextPlayer() {
        for (Player player : riskGame.getPlayers()) {
            if (player.getUnallocatedArmies() == 0) {
                continue;
            }
            else {
                riskGame.setCurrPlayerToNextPlayer();
                new StartupController((this.gamePlayFrame));
                return;
            }
        }
        riskGame.setCurrPlayer(riskGame.getPlayers().firstElement());
        riskGame.reinforcementPhase();
        new ReinforcementController(this.gamePlayFrame);
    }
}
