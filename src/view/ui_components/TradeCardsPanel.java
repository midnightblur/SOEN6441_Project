/* 
 * Risk Game Team 2
 * TradeCardsPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package view.ui_components;

import model.game_entities.Card;
import model.ui_models.GamePlayModel;
import utilities.Config.GAME_STATES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.addVerticalSpacing;

/**
 * Trade Cards Panel representing the controls for Trade Cards phase of the game.
 */
public class TradeCardsPanel extends JPanel implements Observer {
    
    /** The Constant CARDS_LIST_LABEL. */
    private static final String CARDS_LIST_LABEL = "List of cards owned: ";
    
    /** The Constant ARMY_VALUE_LABEL. */
    private static final String ARMY_VALUE_LABEL = "Current army value: ";
    
    /** The Constant TRADE_CARDS_BUTTON. */
    private static final String TRADE_CARDS_BUTTON = "Trade cards";
    
    /** The Constant GAINED_ARMIES_LABEL. */
    private static final String GAINED_ARMIES_LABEL = "# of armies gained: ";
    
    /** The Constant BACK_TO_REINFORCEMENT_BUTTON. */
    private static final String BACK_TO_REINFORCEMENT_BUTTON = "Back to Reinforcement";
    
    /** The player ID. */
    private JLabel playerID;
    
    /** The gained armies label. */
    private JLabel gainedArmiesLabel;
    
    /** The army value label. */
    private JLabel armyValueLabel;
    
    /** The card list. */
    private JPanel cardList;
    
    /** The trade cards. */
    private JButton tradeCards;
    
    /** The back to reinforcement button. */
    private JButton backToReinforcementButton;
    
    /**
     * Instantiates a new trade cards panel.
     */
    // region Constructors
    public TradeCardsPanel() {
        /* Instantiate elements */
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(GAME_STATES.REINFORCEMENT.name());
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
        setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new BoxLayout(controlWrapper, BoxLayout.PAGE_AXIS));
        cardList.setLayout(new GridLayout(4, 5));
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
     * Gets the card list.
     *
     * @return the card list
     */
    public JPanel getCardList() {
        return cardList;
    }
    
    public void setGainedArmiesLabel(int armyValue) {
        gainedArmiesLabel.setVisible(true);
        gainedArmiesLabel.setText(GAINED_ARMIES_LABEL + armyValue);
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
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == GAME_STATES.REINFORCEMENT) {
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