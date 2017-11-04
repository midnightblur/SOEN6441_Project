/* 
 * Risk Game Team 2
 * TradeCardsPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Card;
import shared_resources.utilities.Config.GAME_STATES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * TradeCardsPanel is responsible for representing the ui components for trading cards in Reinforcement phase of the game.
 */
public class TradeCardsPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String CARDS_LIST_LABEL = "List of cards owned: ";
    private static final String ARMY_VALUE_LABEL = "Current army value: ";
    private static final String TRADE_CARDS_BUTTON = "Trade cards";
    private static final String GAINED_ARMIES_LABEL = "# of armies gained: ";
    private static final String BACK_TO_REINFORCEMENT_BUTTON = "Back to Reinforcement";
    private JLabel playerID;
    private JLabel gainedArmiesLabel;
    private JLabel armyValueLabel;
    private JPanel cardList;
    private JButton tradeCards;
    private JButton backToReinforcementButton;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new trade cards panel.
     */
    public TradeCardsPanel() {
        /* Instantiate elements */
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(GAME_STATES.PLAYER_REINFORCEMENT.name());
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        tradeCards = new JButton(TRADE_CARDS_BUTTON);
        tradeCards.setForeground(Color.BLUE);
        backToReinforcementButton = new JButton(BACK_TO_REINFORCEMENT_BUTTON);
        armyValueLabel = new JLabel();
        JLabel cardsListLabel = new JLabel(CARDS_LIST_LABEL);
        gainedArmiesLabel = new JLabel();
        gainedArmiesLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        cardList = new JPanel(new FlowLayout());
        
        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new BoxLayout(controlWrapper, BoxLayout.PAGE_AXIS));
        cardList.setLayout(new GridLayout(5, 4));
        JPanel topGrid = new JPanel(new GridLayout(4, 1));
        JPanel bottomGrid = new JPanel(new GridLayout(7, 1));
        
        /* Add the elements to the panel */
        topGrid.add(gameState);
        topGrid.add(playerID);
        addVerticalSpacing(topGrid);
        topGrid.add(cardsListLabel);
        controlWrapper.add(topGrid);
        
        controlWrapper.add(cardList);
        
        bottomGrid.add(armyValueLabel);
        bottomGrid.add(gainedArmiesLabel);
        gainedArmiesLabel.setVisible(false);
        addVerticalSpacing(bottomGrid);
        bottomGrid.add(tradeCards);
        addVerticalSpacing(bottomGrid);
        bottomGrid.add(backToReinforcementButton);
        controlWrapper.add(bottomGrid);
        
        add(controlWrapper);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Sets the label for the gained armies while trading cards
     *
     * @param gainedArmies the quantity of armies gained
     */
    public void setGainedArmiesLabel(int gainedArmies) {
        gainedArmiesLabel.setVisible(true);
        gainedArmiesLabel.setText(GAINED_ARMIES_LABEL + gainedArmies);
    }
    
    /**
     * Gets the card list.
     *
     * @return the card list
     */
    public JPanel getCardList() {
        return cardList;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Adds the trade cards button listener.
     *
     * @param listenerForTradeCardsButton the listener for trade cards button
     */
    public void addTradeCardsButtonListener(ActionListener listenerForTradeCardsButton) {
        tradeCards.addActionListener(listenerForTradeCardsButton);
    }
    
    /**
     * Adds the back to reinforcement listener.
     *
     * @param listenerForBackToReinforcement the listener for back to reinforcement
     */
    public void addBackToReinforcementListener(ActionListener listenerForBackToReinforcement) {
        backToReinforcementButton.addActionListener(listenerForBackToReinforcement);
    }
    
    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == GAME_STATES.PLAY &&
                    gamePlayModel.getCurrentPlayer().getGameState() == GAME_STATES.PLAYER_REINFORCEMENT) {
                armyValueLabel.setText(ARMY_VALUE_LABEL + Integer.toString(gamePlayModel.getArmyValue()));
                gainedArmiesLabel.setVisible(false);
                playerID.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerID.setText(gamePlayModel.getCurrentPlayer().getPlayerName());
                
                /* Display list of cards owned by current player */
                getCardList().removeAll();
                for (Card card : gamePlayModel.getCurrentPlayer().getPlayersHand()) {
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setText(card.getCardType().name());
                    getCardList().add(checkBox);
                }
                getCardList().revalidate();
                getCardList().repaint();
            }
        }
    }
    // endregion
}