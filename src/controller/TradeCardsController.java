package controller;

import model.RiskGame;
import model.game_entities.Player;
import view.screens.GamePlayFrame;
import view.ui_components.TradeCardsPanel;

public class TradeCardsController {
    private TradeCardsPanel tradeCardsPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private Player currentPlayer;
    
    
    public TradeCardsController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        tradeCardsPanel = new TradeCardsPanel();
        gamePlayFrame.getContentPane().setRightComponent(tradeCardsPanel);
        riskGame = RiskGame.getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        
        /* set control panel */
        populateTradeCardsPanel();
        
        /* Register Observer to Observable */
        currentPlayer.addObserver(tradeCardsPanel);
        
        /* Register to be ActionListeners */
        
    }
    
    /* Private methods */
    
    /**
     * Populate the trade cards control panel with updated model data
     */
    private void populateTradeCardsPanel() {
        /* set view elements */
        
    }
    
}
