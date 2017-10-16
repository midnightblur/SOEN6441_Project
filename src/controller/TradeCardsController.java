package controller;

import model.RiskGame;
import model.game_entities.Card;
import model.game_entities.Player;
import view.screens.GamePlayFrame;
import view.ui_components.TradeCardsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static utilities.Config.GAME_STATES.TRADE_IN_PHASE;
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
     * Constructor for the Trade Cards Controller
     * responsible for exchanging cards for armies
     *
     * @param gamePlayFrame the main frame
     */
    public TradeCardsController(GamePlayFrame gamePlayFrame) {
        this.gamePlayFrame = gamePlayFrame;
        tradeCardsPanel = new TradeCardsPanel();
        
        gamePlayFrame.getContentPane().setRightComponent(tradeCardsPanel);
        setDivider(gamePlayFrame.getContentPane());
        riskGame = RiskGame.getInstance();
        currentPlayer = riskGame.getCurrPlayer();
        
        riskGame.setGameState(TRADE_IN_PHASE);
        
        /* set control panel */
        populateTradeCardsPanel();
        
        /* Register Observer to Observable */
        riskGame.addObserver(tradeCardsPanel);
        currentPlayer.addObserver(tradeCardsPanel);
        
        /* Register to be ActionListeners */
        tradeCardsPanel.addTradeCardsButtonListener(e -> tradeSelectedCards());
        tradeCardsPanel.addBackToReinforcementListener(e -> new PhaseReinforcementController(this.gamePlayFrame));
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
        listCards();
        
        /* set the army value label */
        tradeCardsPanel.setArmyValueLabel(riskGame.getArmyValue());
    }
    
    /**
     * Collect the selected cards from UI and trade them by calling the tradeInCards() from the model
     */
    private void tradeSelectedCards() {
        Vector<String> selectedCards = new Vector<>();
        for (Component component : tradeCardsPanel.getCardList().getComponents()) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected()) {
                selectedCards.add(checkBox.getText());
            }
        }
        String message = riskGame.tradeInCards(selectedCards);
        listCards(); // this will remove used cards
        gamePlayFrame.displayMessage(message);
    }
    
    public void listCards() {
        tradeCardsPanel.getCardList().removeAll();
        for (Card card : currentPlayer.getPlayersHand()) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setText(card.getCardType());
            tradeCardsPanel.getCardList().add(checkBox);
        }
        tradeCardsPanel.getCardList().revalidate();
        tradeCardsPanel.getCardList().repaint();
    }
}
