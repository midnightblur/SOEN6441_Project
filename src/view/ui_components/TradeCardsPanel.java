package view.ui_components;

import model.RiskGame;
import model.game_entities.Player;
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
    private static final String DONE_BUTTON = "Done";
    
    private JLabel gameState;
    private JLabel playerID;
    private JLabel gainedArmiesLabel;
    private JLabel cardsListLabel;
    private JLabel armyValueLabel;
    private JPanel cardList;
    private JButton tradeCards;
    private JButton doneButton;
    
    /* Constructors */
    public TradeCardsPanel() {
        /* Instantiate elements */
        gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        tradeCards = new JButton(TRADE_CARDS_BUTTON);
        doneButton = new JButton(DONE_BUTTON);
        armyValueLabel = new JLabel();
        cardsListLabel = new JLabel(CARDS_LIST_LABEL);
        gainedArmiesLabel = new JLabel();
        gainedArmiesLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        cardList = new JPanel(new FlowLayout());
        
        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        
        /* Add the elements to the panel */
        controlPanel.add(gameState);
        addVerticalSpacing(controlPanel);
        controlPanel.add(playerID);
        addVerticalSpacing(controlPanel);
        controlPanel.add(cardsListLabel);
        controlPanel.add(cardList);
        addVerticalSpacing(controlPanel);
        controlPanel.add(armyValueLabel);
        addVerticalSpacing(controlPanel);
        controlPanel.add(tradeCards);
        addVerticalSpacing(controlPanel);
        controlPanel.add(doneButton);
        addVerticalSpacing(controlPanel);
        
        add(controlPanel);
    }
    
    /* Getters & Setters */
    public JPanel getCardList() {
        return cardList;
    }
    
    public void setGameState(GAME_STATES gameState) {
        this.gameState.setText("<html><p style=\"color:blue;\">" + gameState.toString() + "<br>Trading Cards</p></html>");
    }
    
    public void setPlayerID(int playerID) {
        this.playerID.setText("Player " + playerID);
    }
    
    public void setArmiesGained(int armiesGained) {
        this.gainedArmiesLabel.setText(GAINED_ARMIES_LABEL + Integer.toString(armiesGained));
    }
    
    public void setArmyValueLabel(int armyValue) {
        this.armyValueLabel.setText(ARMY_VALUE_LABEL + Integer.toString(armyValue));
    }
    
    /* MVC & Observer pattern methods */
    public void addSameThreeButtonListener(ActionListener listenerForSameThreeButton) {
        tradeCards.addActionListener(listenerForSameThreeButton);
    }
    
    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneButton.addActionListener(listenerForDoneButton);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Player) {
            setArmiesGained(((Player) o).getUnallocatedArmies());
        }
        if (o instanceof RiskGame) {
            setArmyValueLabel(((RiskGame) o).getArmyValue());
        }
    }
}