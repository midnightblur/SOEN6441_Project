package controller;

import model.game_entities.Player;
import model.ui_models.GamePlayModel;
import model.ui_models.StartupModel;
import view.screens.GamePlayFrame;
import view.ui_components.StartupPanel;

import static model.ui_models.GamePlayModel.getInstance;
import static utilities.Config.GAME_STATES.STARTUP_PHASE;
import static view.helpers.UIHelper.setDivider;

/**
 * The StartupController class
 */
public class PhaseStartupController {
    private StartupPanel startupPanel;
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    private Player currentPlayer;
    private StartupModel startupModel;


    /* Constructors */
    
    /**
     * Constructor for the Startup Controller
     * responsible for placing an army to a player territory
     *
     * @param gamePlayFrame the main frame
     */
    public PhaseStartupController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        startupPanel = new StartupPanel();
        startupModel = new StartupModel();
        
        gamePlayFrame.getContentPane().setRightComponent(startupPanel);
        setDivider(gamePlayFrame.getContentPane());
        gamePlayModel = getInstance();
        gamePlayModel.setGameState(STARTUP_PHASE);
        currentPlayer = gamePlayModel.getCurrPlayer();
        // update the game map TODO: is it necessary?
        gamePlayModel.getMapTableModel().updateMapTableModel(gamePlayModel.getGameMap());

        /* Register Observer to Observable */
        gamePlayModel.addObserver(startupPanel);
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
        startupPanel.setGameState(gamePlayModel.getGameState());

        /* set the player ID label */
        startupPanel.setPlayerID(currentPlayer.getPlayerID());

        /* set the unallocated armies */
        startupPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());

        /* set the source dropdown */
        startupPanel.getTerritoryDropdown().setModel(startupModel.getTerritoriesList());
        for (Player player : gamePlayModel.getPlayers()) {

        }
        if (isLastTurn()) {

        }
        if (gamePlayModel.getNextPlayer().getUnallocatedArmies() == 0) {
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
            gamePlayModel.placeArmy(territory);
//            riskGame.getMapTableModel().updateMapTableModel(riskGame.getGameMap());
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
        for (int i = 0; i < gamePlayModel.getPlayers().size(); i++) {
            if (gamePlayModel.getNextPlayer().getUnallocatedArmies() > 0) {
                gamePlayModel.setCurrPlayerToNextPlayer();
                new PhaseStartupController((this.gamePlayFrame));
                return;
            } else {
                gamePlayModel.setCurrPlayerToNextPlayer();
            }
        }
        gamePlayModel.setCurrPlayer(gamePlayModel.getPlayers().firstElement());
        gamePlayModel.reinforcementPhase();
        new PhaseReinforcementController(this.gamePlayFrame);
    }

    /**
     * Returns true if it is the last turn in the Startup Phase, false otherwise.
     *
     * @return Boolean true if it is the last turn in Startup Phase, false otherwise
     */
    public boolean isLastTurn() {
        int counter = 0;
        for (Player player : gamePlayModel.getPlayers()) {
            if (player.getUnallocatedArmies() == 0) {
                counter++;
            }
        }
        if (counter == gamePlayModel.getPlayers().size()) {
            return true;
        } else {
            return false;
        }
    }
}
