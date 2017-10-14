package controller;

import model.RiskGame;
import model.game_entities.Player;
import model.ui_models.FortificationModel;
import view.screens.GamePlayFrame;
import view.ui_components.FortificationPanel;

import static model.RiskGame.getInstance;
import static utilities.Config.GAME_STATES.FORTIFICATION_PHASE;
import static view.helpers.UIHelper.setDivider;

/**
 * The FortificationController class
 */
public class FortificationController {
    private FortificationPanel fortificationPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private Player currentPlayer;
    private FortificationModel fortificationModel;
    
    
    /**
     * Constructor
     *
     * @param gamePlayFrame
     */
    public FortificationController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        fortificationPanel = new FortificationPanel();
        gamePlayFrame.getContentPane().setRightComponent(fortificationPanel);
        setDivider(gamePlayFrame.getContentPane());
        
        riskGame = getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        riskGame.setGameState(FORTIFICATION_PHASE);
        
        fortificationModel = new FortificationModel();
        
        

        /* Register Observer to Observable */
        fortificationModel.addObserver(fortificationPanel);
        currentPlayer.addObserver(fortificationPanel);
        riskGame.addObserver(fortificationPanel);
        
        /* Register to be ActionListeners */
        fortificationPanel.addDoneButtonListener(e -> new ReinforcementController(this.gamePlayFrame));
        fortificationPanel.addMoveArmiesButtonListener(e -> moveArmies());
        fortificationPanel.addSourceTerritoryDropdownListener(e -> fortificationModel.setTargetTerritoriesList(
                fortificationPanel.getSourceTerritoryDropdown().getSelectedItem().toString()));
    
        /* set control panel */
        populateFortificationPanel();
    }
    
    /* Private methods */
    
    private void moveArmies() {
        riskGame.fortificationPhase(
                fortificationPanel.getSourceTerritoryDropdown().getSelectedItem().toString(),
                fortificationPanel.getTargetTerritoryDropdown().getSelectedItem().toString(),
                Integer.parseInt(fortificationPanel.getArmiesToMoveField().getText()));
        riskGame.getMapTableModel().updateMapTableModel(riskGame.getGameMap());
    }
    
    /**
     * Populate the fortification control panel with updated model data
     */
    private void populateFortificationPanel() {
        /* set the phase label */
        fortificationPanel.setGameState(riskGame.getGameState());
        
        /* set the player ID label */
        fortificationPanel.setPlayerID(currentPlayer.getPlayerID());
        
        /* set the source dropdown */
        fortificationPanel.getSourceTerritoryDropdown().setModel(fortificationModel.getSourceTerritoriesList());
        
        /* set the target dropdown */
        fortificationPanel.getTargetTerritoryDropdown().setModel(fortificationModel.getTargetTerritoriesList());
        fortificationModel.setTargetTerritoriesList(fortificationPanel.getSourceTerritoryDropdown().getSelectedItem().toString());
    }
}
