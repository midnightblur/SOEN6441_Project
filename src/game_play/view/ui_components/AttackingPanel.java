package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

public class AttackingPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String ATTACK_FROM_LABEL = "Attack from:";
    private static final String ATTACK_TO_LABEL = "Attack to:";
    private static final String NUMBER_OF_ATTACK_DICE = "Number of Dice for attack:";
    private static final String ATTACK_BUTTON = "ATTACK";
    private static final String DONE_BUTTON = "Done";
    
    private JPanel cardsPanel;
    private JLabel playerName;
    private JComboBox<String> attackingTerritoriesDropdown;
    private JComboBox<String> defendingTerritoriesDropdown;
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
        /* Steup layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        cardsPanel = new JPanel(new CardLayout());
    
        /* Setup all child panel */
        populateAttackPreparation();
        
        add(cardsPanel);
    }
    
    private void populateAttackPreparation() {
        JPanel attackPreparationPanel = new JPanel();
        attackPreparationPanel.setLayout(new GridLayout(0, 1));
        
        /* Populate header including Phase label & Player label */
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(Config.GAME_STATES.PLAYER_FORTIFICATION.name());
        gameState.setAlignmentX(CENTER_ALIGNMENT);
        attackPreparationPanel.add(gameState);
        playerName = new JLabel();
        playerName.setFont(new Font("Sans Serif", Font.BOLD, 20));
        playerName.setAlignmentX(CENTER_ALIGNMENT);
        attackPreparationPanel.add(playerName);
        addVerticalSpacing(attackPreparationPanel);
        
        /* Populate control components */
        JLabel attackFrom = new JLabel(ATTACK_FROM_LABEL);
        attackPreparationPanel.add(attackFrom);
        attackFrom.setAlignmentX(CENTER_ALIGNMENT);
        attackingTerritoriesDropdown = new JComboBox<>();
        attackPreparationPanel.add(attackingTerritoriesDropdown);
        attackingTerritoriesDropdown.setAlignmentX(CENTER_ALIGNMENT);
        addVerticalSpacing(attackPreparationPanel);
        
        JLabel attackTo = new JLabel(ATTACK_TO_LABEL);
        attackPreparationPanel.add(attackTo);
        attackTo.setAlignmentX(CENTER_ALIGNMENT);
        defendingTerritoriesDropdown = new JComboBox<>();
        attackPreparationPanel.add(defendingTerritoriesDropdown);
        defendingTerritoriesDropdown.setAlignmentX(CENTER_ALIGNMENT);
        addVerticalSpacing(attackPreparationPanel);
        
        JLabel numberOfDiceLabel = new JLabel(NUMBER_OF_ATTACK_DICE);
        attackPreparationPanel.add(numberOfDiceLabel);
        numberOfDiceLabel.setAlignmentX(CENTER_ALIGNMENT);
        attackerNoOfDice = new JComboBox<>();
        attackPreparationPanel.add(attackerNoOfDice);
        attackerNoOfDice.setAlignmentX(CENTER_ALIGNMENT);
        attackButton = new JButton(ATTACK_BUTTON);
        attackPreparationPanel.add(attackButton);
        attackButton.setAlignmentX(CENTER_ALIGNMENT);
        addVerticalSpacing(attackPreparationPanel);
        
        doneButton = new JButton(DONE_BUTTON);
        attackPreparationPanel.add(doneButton);
        doneButton.setAlignmentX(CENTER_ALIGNMENT);
        
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
    
    /**
     * Gets the attacking territories dropdown
     *
     * @return the attacking territories dropdown
     */
    public JComboBox<String> getAttackingTerritoriesDropdown() {
        return attackingTerritoriesDropdown;
    }
    
    /**
     * Gets the defending territories dropdown
     *
     * @return the defending territories dropdown
     */
    public JComboBox<String> getDefendingTerritoriesDropdown() {
        return defendingTerritoriesDropdown;
    }
    
    /**
     * Gets the attacker number of dice dropdown
     *
     * @return the attacker number of dice dropdown
     */
    public JComboBox<Integer> getAttackerNoOfDice() {
        return attackerNoOfDice;
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
                playerName.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerName.setText(gamePlayModel.getCurrentPlayer().getPlayerName());
                attackingTerritoriesDropdown.setModel(new DefaultComboBoxModel<>(
                        gamePlayModel.getValidAttackingTerritories(gamePlayModel.getCurrentPlayer())));
                attackingTerritoriesDropdown.setSelectedIndex(0);
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
    
    /**
     * Adds the attacking territory dropdown listener.
     *
     * @param listenerForAttackingTerritoryDropdown the listener for source territory dropdown
     */
    public void addAttackingTerritoryDropdownListener(ActionListener listenerForAttackingTerritoryDropdown) {
        attackingTerritoriesDropdown.addActionListener(listenerForAttackingTerritoryDropdown);
    }
    // endregion
}
