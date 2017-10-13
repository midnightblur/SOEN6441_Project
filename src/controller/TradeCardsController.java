package controller;

import model.RiskGame;
import model.game_entities.Player;
import view.screens.GamePlayFrame;
import view.ui_components.TradeCardsControlPanel;

public class TradeCardsController {
    private TradeCardsControlPanel tradeCardsControlPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private Player currentPlayer;
    
    
    public TradeCardsController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        tradeCardsControlPanel = new TradeCardsControlPanel();
        gamePlayFrame.getContentPane().setRightComponent(tradeCardsControlPanel);
        riskGame = RiskGame.getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        
        /* set control panel */
        populateTradeCardsPanel();
        
        /* Register Observer to Observable */
        currentPlayer.addObserver(tradeCardsControlPanel);
        
        /* Register to be ActionListeners */
        
    }
    
    /* Private methods */
    
    /**
     * Populate the fortification control panel with updated model data
     */
    private void populateTradeCardsPanel() {
        /* set view elements */
        
    }
    
}
