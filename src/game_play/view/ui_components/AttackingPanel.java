package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class AttackingPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String ATTACK_FROM_LABEL = "Attack from:";
    private static final String ATTACK_TO_LABEL = "Attack to:";
    private static final String NUMBER_OF_ATTACK_DICE = "Number of Dice for attack:";
    private static final String ATTACK_BUTTON = "ATTACK";
    private static final String DONE_BUTTON = "Done";
    
    private JPanel cardsPanel;
    private JLabel playerName;
    private JComboBox<String> attackingTerritories;
    private JComboBox<String> defendingTerritories;
    private JComboBox<Integer> attackerNoOfDice;
    private JButton attackButton;
    private JButton doneButton;
    // endregion
    
    // region Constructors
    public AttackingPanel() {
        populateUI();
    }
    // endregion
    
    // region Private methods
    private void populateUI() {
        cardsPanel = new JPanel(new CardLayout());
    
        populateAttackPreparation();
        
        add(cardsPanel);
    }
    
    private void populateAttackPreparation() {
        JPanel attackPreparationPanel = new JPanel();
        attackPreparationPanel.setLayout(new BoxLayout(attackPreparationPanel, BoxLayout.Y_AXIS));
        
        /* Populate header including Phase label & Player label */
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(Config.GAME_STATES.PLAYER_FORTIFICATION.name());
        attackPreparationPanel.add(gameState);
        playerName = new JLabel();
        playerName.setFont(new Font("Sans Serif", Font.BOLD, 20));
        attackPreparationPanel.add(playerName);
        
        /* Populate control components */
        JLabel attackFrom = new JLabel(ATTACK_FROM_LABEL);
        attackPreparationPanel.add(attackFrom);
        attackingTerritories = new JComboBox<>();
        attackPreparationPanel.add(attackingTerritories);
        
        JLabel attackTo = new JLabel(ATTACK_TO_LABEL);
        attackPreparationPanel.add(attackTo);
        defendingTerritories = new JComboBox<>();
        attackPreparationPanel.add(defendingTerritories);
        
        JLabel numberOfDiceLabel = new JLabel(NUMBER_OF_ATTACK_DICE);
        attackPreparationPanel.add(numberOfDiceLabel);
        attackerNoOfDice = new JComboBox<>();
        attackPreparationPanel.add(attackerNoOfDice);
    
        attackButton = new JButton(ATTACK_BUTTON);
        attackPreparationPanel.add(attackButton);
        doneButton = new JButton(DONE_BUTTON);
        attackPreparationPanel.add(doneButton);
        
        cardsPanel.add(attackPreparationPanel, attackPreparationPanel.getName());
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the card layout panel
     *
     * @return the card layout panel
     */
    public JPanel getCardsPanel() {
        return cardsPanel;
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.PLAY &&
                    gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.PLAYER_ATTACK) {
                // TODO: update the content of this panel regarding the current player's state
            }
        }
    }
    
    /**
     * Adds the attack button listener
     *
     * @param listenerForAttackButton listener for the attack button
     */
    public void addAttackButtonListener(ActionListener listenerForAttackButton) {
        attackButton.addActionListener(listenerForAttackButton);
    }
    
    /**
     * Adds the done button listener
     *
     * @param listenerForDoneButton listener for the done button
     */
    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneButton.addActionListener(listenerForDoneButton);
    }
    // endregion
}
