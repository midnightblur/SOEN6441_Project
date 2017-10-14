package controller;

import model.RiskGame;
import model.game_entities.Card;
import model.game_entities.Player;
import view.screens.GamePlayFrame;
import view.ui_components.TradeCardsPanel;

import javax.swing.*;

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
        
        //cardsListModel = new DropDownModel(riskGame.getCurrPlayer().getPlayersHand());
        /* set control panel */
        populateTradeCardsPanel();
        
        /* Register Observer to Observable */
        currentPlayer.addObserver(tradeCardsPanel);
        
        /* Register to be ActionListeners */
        tradeCardsPanel.addSameThreeButtonListener(e -> riskGame.tradeInCards());
        tradeCardsPanel.addOneEachButtonListener(e -> riskGame.tradeInCards());
        tradeCardsPanel.addDoneButtonListener(e -> new ReinforcementController(this.gamePlayFrame));
        
    }
    
    /* Private methods */
    
    /**
     * Populate the trade cards control panel with updated model data
     */
    private void populateTradeCardsPanel() {
        /* set the phase label */
        tradeCardsPanel.setGameState(riskGame.getGameState());
        
        /* set the player ID label */
        tradeCardsPanel.setPlayerID(currentPlayer.getPlayerID());
        
        /* set the armies gained label */
        tradeCardsPanel.setArmiesGained(currentPlayer.getUnallocatedArmies());
        
        /* the cards list for this particular player */
        for (Card card : currentPlayer.getPlayersHand()) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setText(card.getCardType());
            tradeCardsPanel.getCardList().add(checkBox);
        }
    }
}
