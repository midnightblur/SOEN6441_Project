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
 * Trade Cards Panel representing the controls for Trade Cards phase of the game
 */
public class TradeCardsPanel extends JPanel implements Observer {
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
    
    /* Constructors */
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
    
    /* Getters & Setters */
    public JPanel getCardList() {
        return cardList;
    }
    
    public void setPlayerID(int playerID) {
        this.playerID.setText("Player " + playerID);
    }
    
    public void setArmyValueLabel(int armyValue) {
        this.armyValueLabel.setText(ARMY_VALUE_LABEL + Integer.toString(armyValue));
    }
    
    /* MVC & Observer pattern methods */
    public void addTradeCardsButtonListener(ActionListener listenerForTradeCardsButton) {
        tradeCards.addActionListener(listenerForTradeCardsButton);
    }
    
    public void addBackToReinforcementListener(ActionListener listenerForBackToReinforcement) {
        backToReinforcementButton.addActionListener(listenerForBackToReinforcement);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == GAME_STATES.REINFORCEMENT) {
                setArmyValueLabel(gamePlayModel.getArmyValue());
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
}