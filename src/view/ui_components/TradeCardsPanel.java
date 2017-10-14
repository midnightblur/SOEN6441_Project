package view.ui_components;

import utilities.Config.GAME_STATES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class TradeCardsPanel extends JPanel implements Observer {
    private static final String CARDS_LIST_LABEL = "List of cards owned: ";
    private static final String SAME_THREE_BUTTON = "3 of a kind";
    private static final String ONE_EACH_BUTTON = "One of each";
    private static final String GAINED_ARMIES_LABEL = "# of armies gained: ";
    private static final String DONE_BUTTON = "Done";
    
    private JLabel gameState;
    private JLabel playerID;
    private JLabel gainedArmiesLabel;
    private JPanel cardList;
    private JButton sameThreeButton;
    private JButton oneEachButton;
    private JButton doneButton;
    
    /* Constructors */
    public TradeCardsPanel() {
        gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        sameThreeButton = new JButton(SAME_THREE_BUTTON);
        oneEachButton = new JButton(ONE_EACH_BUTTON);
        doneButton = new JButton(DONE_BUTTON);
        gainedArmiesLabel = new JLabel();
        gainedArmiesLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        cardList = new JPanel();
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        
        /* Control panel */
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        
        controlPanel.add(gameState);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        controlPanel.add(playerID);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        controlPanel.add(cardList);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new FlowLayout());
        panel_1.add(sameThreeButton);
        panel_1.add(oneEachButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    
        controlPanel.add(panel_1);
        controlPanel.add(doneButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 50)));
    
        add(controlPanel);
    
    }
    
    /* Getters & Setters */
    
    public JPanel getCardList() {
        return cardList;
    }
    
    public void setGameState(GAME_STATES gameState) {
        this.gameState.setText(gameState.toString());
    }
    
    public void setPlayerID(int playerID) {
        this.playerID.setText("Player " + playerID);
    }
    
    public void setArmiesGained(int armiesGained) {
        this.gainedArmiesLabel.setText(GAINED_ARMIES_LABEL + Integer.toString(armiesGained));
    }
    
    /* MVC & Observer pattern methods */
    public void addSameThreeButtonListener(ActionListener listenerForSameThreeButton) {
        sameThreeButton.addActionListener(listenerForSameThreeButton);
    }
    
    public void addOneEachButtonListener(ActionListener listenerForOneEachButton) {
        oneEachButton.addActionListener(listenerForOneEachButton);
    }
    
    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneButton.addActionListener(listenerForDoneButton);
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
    
    }
}
