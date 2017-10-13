package controller;

import model.RiskGame;
import model.game_entities.Player;
import view.screens.GamePlayFrame;
import view.ui_components.FortificationControlPanel;

public class FortificationController {
    private FortificationControlPanel fortificationControlPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private Player currentPlayer;
    
    public FortificationController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        fortificationControlPanel = new FortificationControlPanel();
        gamePlayFrame.getContentPane().setRightComponent(fortificationControlPanel);
        riskGame = RiskGame.getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        
        /* set control panel */
        populateFortificationPanel();
        
        /* Register Observer to Observable */
        currentPlayer.addObserver(fortificationControlPanel);
        
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
