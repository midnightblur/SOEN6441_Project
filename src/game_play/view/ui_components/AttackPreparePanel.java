/*
 * Risk Game Team 2
 * AttackingPreparePanel.java
 * Version 1.0
 * Nov 6, 2017
 */
package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * Attacking Prepare Panel class used to start an attack
 * Player to attack will decide source and target for the attack
 * Then it will roll the dice
 * Defender will have the chance to protect its territory using a modal dialog
 *
 * @author Team 2
 * @version 2.0
 */
public class AttackPreparePanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String ATTACK_FROM_LABEL = "Attack from:";
    private static final String ATTACK_TO_LABEL = "Attack to:";
    private static final String NUMBER_OF_ATTACK_DICE = "Number of Dice for attack:";
    private static final String ATTACK_BUTTON = "ATTACK";
    private static final String DONE_BUTTON = "Done (to Fortification)";
    
    private JComboBox<String> attackingTerritoriesDropdown;
    private JComboBox<String> defendingTerritoriesDropdown;
    private JComboBox<Integer> attackerNoOfDice;
    private JButton attackButton;
    private JButton doneButton;
    // endregion
    
    // region Constructors
    
    /**
     * The constructor for Attack Prepare Panel
     */
    public AttackPreparePanel() {
        setLayout(new GridLayout(0, 1));
        
        /* Populate control components */
        JLabel attackFrom = new JLabel(ATTACK_FROM_LABEL);
        add(attackFrom);
        attackFrom.setAlignmentX(CENTER_ALIGNMENT);
        attackingTerritoriesDropdown = new JComboBox<>();
        add(attackingTerritoriesDropdown);
        attackingTerritoriesDropdown.setAlignmentX(CENTER_ALIGNMENT);
        addVerticalSpacing(this);
        
        JLabel attackTo = new JLabel(ATTACK_TO_LABEL);
        add(attackTo);
        attackTo.setAlignmentX(CENTER_ALIGNMENT);
        defendingTerritoriesDropdown = new JComboBox<>();
        add(defendingTerritoriesDropdown);
        defendingTerritoriesDropdown.setAlignmentX(CENTER_ALIGNMENT);
        addVerticalSpacing(this);
        
        JLabel numberOfDiceLabel = new JLabel(NUMBER_OF_ATTACK_DICE);
        add(numberOfDiceLabel);
        numberOfDiceLabel.setAlignmentX(CENTER_ALIGNMENT);
        attackerNoOfDice = new JComboBox<>();
        add(attackerNoOfDice);
        attackerNoOfDice.setAlignmentX(CENTER_ALIGNMENT);
        attackButton = new JButton(ATTACK_BUTTON);
        add(attackButton);
        attackButton.setAlignmentX(CENTER_ALIGNMENT);
        addVerticalSpacing(this);
        
        doneButton = new JButton(DONE_BUTTON);
        add(doneButton);
        doneButton.setAlignmentX(CENTER_ALIGNMENT);
    }
    // endregion
    
    // region Getters & Setters
    
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
    
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.PLAY &&
                    gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.ATTACK_PREPARE) {
                DefaultComboBoxModel<String> attackingTerritoriesModel = new DefaultComboBoxModel<>(
                        gamePlayModel.getValidAttackingTerritories(gamePlayModel.getCurrentPlayer()));
                
                // Disable all selection controls if no valid territory to attack
                if (attackingTerritoriesModel.getSize() > 0) {
                    attackButton.setEnabled(true);
                    attackingTerritoriesDropdown.setEnabled(true);
                    defendingTerritoriesDropdown.setEnabled(true);
                    attackerNoOfDice.setEnabled(true);
                    attackingTerritoriesDropdown.setModel(attackingTerritoriesModel);
                    attackingTerritoriesDropdown.setSelectedIndex(0);
                } else {
                    attackButton.setEnabled(false);
                    attackingTerritoriesDropdown.setEnabled(false);
                    defendingTerritoriesDropdown.setEnabled(false);
                    attackerNoOfDice.setEnabled(false);
                }
            }
        }
    }
    // endregion
}
