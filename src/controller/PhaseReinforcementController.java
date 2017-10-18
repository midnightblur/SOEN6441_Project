package controller;

import model.game_entities.Player;
import model.ui_models.GamePlayModel;
import model.ui_models.PlayerTerritoriesModel;
import view.screens.GamePlayFrame;
import view.ui_components.ReinforcementPanel;

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
//        reinforcementPanel.addTradeCardsButtonListener(e -> tradeCards());
//        reinforcementPanel.addPlaceArmiesButtonListener(e -> distributeArmies());
//        reinforcementPanel.addGoToFortificationButtonListener(e -> goBackToFortificationPhase());
    }
    
    /* Private methods */
    
    /**
     * Populate the reinforcement control panel with updated model data
     * The panel is subscribed to multiple model data sources
     */
    private void populateReinforcementPanel() {
//        /* set the phase label */
//        reinforcementPanel.setGameState(gamePlayModel.getGameState());
//
//        /* set the player ID label */
//        reinforcementPanel.setPlayerID(currentPlayer.getPlayerID());
//
//        /* set the unallocated armies */
//        reinforcementPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
//
//        /* set the model for the player table */
//        playerTerritoriesModel = new PlayerTerritoriesModel(currentPlayer);
//        reinforcementPanel.getPlayerTerritoryTable().setModel(playerTerritoriesModel.getModel());
//        reinforcementPanel.setTotalArmiesToPlace(currentPlayer.getUnallocatedArmies());
//        if (currentPlayer.getUnallocatedArmies() == 0) {
//            reinforcementPanel.getGoToFortificationButton().setEnabled(true);
//        } else {
//            reinforcementPanel.getGoToFortificationButton().setEnabled(false);
//        }
    }
    
    
}
