package controller;

import model.RiskGame;
import model.game_entities.Player;
import view.screens.GamePlayFrame;
import view.ui_components.FortificationPanel;

public class FortificationController {
    private FortificationPanel fortificationPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private Player currentPlayer;
    
    public FortificationController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        fortificationPanel = new FortificationPanel();
        gamePlayFrame.getContentPane().setRightComponent(fortificationPanel);
        riskGame = RiskGame.getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        
        /* set control panel */
        populateFortificationPanel();
        
        /* Register Observer to Observable */
        currentPlayer.addObserver(fortificationPanel);
        
        /* Register to be ActionListeners */
        
    }
    
    /* Private methods */
    
    /**
     * Populate the fortification control panel with updated model data
     */
    private void populateFortificationPanel() {
        /* set view elements */
        
    }
    
}
