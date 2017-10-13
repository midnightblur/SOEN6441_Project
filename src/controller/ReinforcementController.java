package controller;

import model.RiskGame;
import model.game_entities.Player;
import model.game_entities.Territory;
import model.ui_models.PlayerTerritoriesModel;
import view.screens.GamePlayFrame;
import view.ui_components.ReinforcementControlPanel;

import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.Map;

public class ReinforcementController {
    private ReinforcementControlPanel reinforcementControlPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private PlayerTerritoriesModel playerTerritoriesModel;
    private Player currentPlayer;
    
    /* Constructors */
    public ReinforcementController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        reinforcementControlPanel = new ReinforcementControlPanel();
        gamePlayFrame.getContentPane().setRightComponent(reinforcementControlPanel);
        riskGame = RiskGame.getInstance();
        currentPlayer = riskGame.getCurrPlayer();
    
        /* set control panel */
        populateReinforcementPanel();
        
        /* Register Observer to Observable */
        playerTerritoriesModel.addObserver(reinforcementControlPanel);
        currentPlayer.addObserver(reinforcementControlPanel);
        
        /* Register to be ActionListeners */
        reinforcementControlPanel.addTradeCardsButtonListener(e -> riskGame.tradeInCards());
        reinforcementControlPanel.addPlaceArmiesButtonListener(e -> distributeArmies());
        reinforcementControlPanel.addDoneButtonListener(e -> riskGame.fortificationPhase());
    }
    
    /* Private methods */
    
    /**
     * Populate the reinforcement control panel with updated model data
     * The panel is subscribed to multiple model data sources
     */
    private void populateReinforcementPanel() {
        /* set the phase label */
        reinforcementControlPanel.setGameState(riskGame.getGameState());
        
        /* set the player ID label */
        reinforcementControlPanel.setPlayerID(currentPlayer.getPlayerID());
        
        /* set the unallocated armies */
        reinforcementControlPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
        
        /* set the model for the player table */
        playerTerritoriesModel = new PlayerTerritoriesModel(currentPlayer);
        reinforcementControlPanel.getPlayerTerritoryTable().setModel(playerTerritoriesModel.getModel());
        reinforcementControlPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
    }
    
    /**
     * Looping through view table, get the quantity of armies for each territory
     * then place them using the placeArmies in the model
     */
    private void distributeArmies() {
        TableModel armiesData = reinforcementControlPanel.getPlayerTerritoryTable().getModel();
        String territoryName;
        int armies;
        int runningSum = 0;
        Map<Territory, Integer> armiesToPlace = new HashMap<>();
        for (int r = 0; r < armiesData.getRowCount(); r++) {
            armies = Integer.parseInt(armiesData.getValueAt(r, 1).toString());
            if (armies > 0) {   // only add entries that have more than 0 armies to be placed
                runningSum += armies;
                territoryName = armiesData.getValueAt(r, 0).toString();
                armiesToPlace.put(riskGame.getGameMap().getATerritory(territoryName), armies);
            }
        }
        if (runningSum <= currentPlayer.getUnallocatedArmies()) {
            riskGame.placeArmies(armiesToPlace);
            // refresh the main table model TODO: find a more optimal way
            riskGame.getMapTableModel().updateMapTableModel(riskGame.getGameMap());
            populateReinforcementPanel();
            gamePlayFrame.displayErrorMessage("The armies were placed successfully");
            // reset the armies to zero
            for (int r = 0; r < armiesData.getRowCount(); r++) {
                armiesData.setValueAt(0, r, 1);
            }
        } else {
            gamePlayFrame.displayErrorMessage("The total armies to allocate must be lesser or equal to the indicated total armies to place");
        }
    }
    
    
}
