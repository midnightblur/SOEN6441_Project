package controller;

import model.RiskGame;
import model.game_entities.Card;
import model.game_entities.Player;
import view.screens.GamePlayFrame;
import view.ui_components.TradeCardsPanel;

import javax.swing.*;

import static view.helpers.UIHelper.setDivider;

/**
 * The Trade Cards Controller responsible of capturing user interaction from the trade cards panel
 * and converting them to changes in the risk game model
 * Once user is done trading cards, it returns focus to the ReinforcementController
 */
public class TradeCardsController {
    private TradeCardsPanel tradeCardsPanel;
    private GamePlayFrame gamePlayFrame;
    private RiskGame riskGame;
    private Player currentPlayer;
    
    /* Constructors */
    
    /**
     * The constructor
     *
     * @param gamePlayFrame the main frame
     */
    public TradeCardsController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        setDivider(gamePlayFrame.getContentPane());
        tradeCardsPanel = new TradeCardsPanel();
        gamePlayFrame.getContentPane().setRightComponent(tradeCardsPanel);
        riskGame = RiskGame.getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        
        /* set control panel */
        populateTradeCardsPanel();
        
        /* Register Observer to Observable */
        currentPlayer.addObserver(tradeCardsPanel);
        riskGame.addObserver(tradeCardsPanel);
        
        /* Register to be ActionListeners */
        tradeCardsPanel.addSameThreeButtonListener(e -> riskGame.tradeInCards());
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
        
        /* set the army value label */
        tradeCardsPanel.setArmyValueLabel(riskGame.getArmyValue());
    }
    
}
