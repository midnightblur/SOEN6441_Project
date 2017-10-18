package controller;

import model.game_entities.Player;
import model.game_entities.Territory;
import model.ui_models.GamePlayModel;
import model.ui_models.PlayerTerritoriesModel;
import view.helpers.UIHelper;
import view.screens.GamePlayFrame;
import view.ui_components.ReinforcementPanel;

import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.Map;

import static model.ui_models.GamePlayModel.getInstance;
import static utilities.Config.GAME_STATES.REINFORCEMENT_PHASE;
import static view.helpers.UIHelper.setDivider;

/**
 * This is Reinforcement controller class which is responsible for starting
 * and controlling this phase.
 *
 * @author
 * @verison 1.0
 */
public class PhaseReinforcementController {
    private ReinforcementPanel reinforcementPanel;
    private GamePlayFrame gamePlayFrame;
    private GamePlayModel gamePlayModel;
    private PlayerTerritoriesModel playerTerritoriesModel;
    private Player currentPlayer;
    
    /* Constructors */
    
    /**
     * Constructor for the Reinforcement Controller is
     * responsible for moving armies of the same player to adjacent territories
     *
     * @param gamePlayFrame the main frame
     */
    public PhaseReinforcementController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        reinforcementPanel = new ReinforcementPanel();
        
        gamePlayFrame.getContentPane().setRightComponent(reinforcementPanel);
        setDivider(gamePlayFrame.getContentPane());
        gamePlayModel = getInstance();
        currentPlayer = gamePlayModel.getCurrentPlayer();
        
        gamePlayModel.setGameState(REINFORCEMENT_PHASE);
    
        /* set control panel */
        populateReinforcementPanel();
        
        /* Register Observer to Observable */
        gamePlayModel.addObserver(reinforcementPanel);
//        currentPlayer.addObserver(reinforcementPanel);
        playerTerritoriesModel.addObserver(reinforcementPanel);

        /* Register to be ActionListeners */
        reinforcementPanel.addTradeCardsButtonListener(e -> tradeCards());
        reinforcementPanel.addPlaceArmiesButtonListener(e -> distributeArmies());
        reinforcementPanel.addGoToFortificationButtonListener(e -> goBackToFortificationPhase());
    }
    
    /* Private methods */
    
    /**
     * Populate the reinforcement control panel with updated model data
     * The panel is subscribed to multiple model data sources
     */
    private void populateReinforcementPanel() {
        /* set the phase label */
        reinforcementPanel.setGameState(gamePlayModel.getGameState());
        
        /* set the player ID label */
        reinforcementPanel.setPlayerID(currentPlayer.getPlayerID());
        
        /* set the unallocated armies */
        reinforcementPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
        
        /* set the model for the player table */
        playerTerritoriesModel = new PlayerTerritoriesModel(currentPlayer);
        reinforcementPanel.getPlayerTerritoryTable().setModel(playerTerritoriesModel.getModel());
        reinforcementPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
        if (currentPlayer.getUnallocatedArmies() == 0) {
            reinforcementPanel.getGoToFortificationButton().setEnabled(true);
        } else {
            reinforcementPanel.getGoToFortificationButton().setEnabled(false);
        }
    }
    
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmiesReinforcement in the model
     */
    private void distributeArmies() {
        TableModel armiesData = reinforcementPanel.getPlayerTerritoryTable().getModel();
        String territoryName;
        int armies;
        int runningSum = 0;
        Map<Territory, Integer> armiesToPlace = new HashMap<>();
        for (int r = 0; r < armiesData.getRowCount(); r++) {
            armies = Integer.parseInt(armiesData.getValueAt(r, 1).toString());
            if (armies > 0) {   // only add entries that have more than 0 armies to be placed
                runningSum += armies;
                territoryName = armiesData.getValueAt(r, 0).toString();
                armiesToPlace.put(gamePlayModel.getGameMap().getATerritory(territoryName), armies);
            }
        }
        if (runningSum > 0 && runningSum <= currentPlayer.getUnallocatedArmies()) {
            gamePlayModel.placeArmiesReinforcement(armiesToPlace);
            //riskGame.getMapTableModel().updateMapTableModel(riskGame.getGameMap());
            populateReinforcementPanel();
            UIHelper.displayMessage(gamePlayFrame, "The armies were placed successfully");
            // reset the armies to zero
            for (int r = 0; r < armiesData.getRowCount(); r++) {
                armiesData.setValueAt(0, r, 1);
            }
        } else {
            UIHelper.displayMessage(gamePlayFrame, "The total armies to allocate must be lesser or equal to the indicated total armies to place");
        }
    }
    
    /**
     * Shows the controller responsible for trading cards
     */
    private void tradeCards() {
        new TradeCardsController(this.gamePlayFrame);
    }
    
    private void goBackToFortificationPhase() {
        // TODO: this needs fixing so it correctly returns to previous phase
        // TODO: (see true condition in the game and possibly have a setter for it under currentPlayer)
        // riskGame.fortificationPhase();
        
        new PhaseFortificationController(gamePlayFrame);
        
    }
}
