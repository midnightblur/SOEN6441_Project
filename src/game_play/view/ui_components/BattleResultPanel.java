/*
 * Risk Game Team 2
 * BattleResultPanel.java
 * Version 1.0
 * Nov 6, 2017
 */
package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Battle;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Battle Result Panel used to display the information for battle outcome
 *
 * @author Team 2
 * @version 2.0
 */
public class BattleResultPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String ATTACKER_ROLL_RESULT = "Attacker roll results: ";
    private static final String DEFENDER_ROLL_RESULT = "Defender roll results: ";
    private static final String ATTACKER_LOSE_ARMIES = "Attacker lose %d armies";
    private static final String DEFENDER_LOSE_ARMIES = "Defender lose %d armies";
    private static final String ATT_TERRITORY_ARMIES = "%s (attacking) now has %d armies left";
    private static final String DEF_TERRITORY_ARMIES = "%s (defending) now has %d armies left";
    private static final String ANOTHER_ATTACK_BUTTON = "Do another Attack";
    private static final String DONE_BUTTON = "Done (to Fortification)";
    
    private JLabel attackerRolls;
    private JLabel defenderRolls;
    private JLabel attackerLoseArmies;
    private JLabel defenderLoseArmies;
    private JLabel attackingArmies;
    private JLabel defendingArmies;
    private JButton anotherAttackBtn;
    private JButton doneBtn;
    // endregion
    
    // region Constructors
    
    /**
     * The panel used to display the battle results
     */
    public BattleResultPanel() {
        setLayout(new GridLayout(0, 1));
        attackerRolls = new JLabel();
        defenderRolls = new JLabel();
        attackerLoseArmies = new JLabel();
        defenderLoseArmies = new JLabel();
        attackingArmies = new JLabel();
        defendingArmies = new JLabel();
        anotherAttackBtn = new JButton(ANOTHER_ATTACK_BUTTON);
        doneBtn = new JButton(DONE_BUTTON);
        
        add(attackerRolls);
        add(defenderRolls);
        add(new JLabel());
        add(new JLabel());
        add(attackerLoseArmies);
        add(attackingArmies);
        add(new JLabel());
        add(new JLabel());
        add(defenderLoseArmies);
        add(defendingArmies);
        add(new JLabel());
        add(new JLabel());
        add(anotherAttackBtn);
        add(doneBtn);
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Add listener for another attack button
     *
     * @param listenerForAnotherAttackBtn the listener for another attack button
     */
    public void addAnotherAttackButtonListener(ActionListener listenerForAnotherAttackBtn) {
        anotherAttackBtn.addActionListener(listenerForAnotherAttackBtn);
    }
    
    /**
     * Add listener for done button
     *
     * @param listenerForDoneButton the listener for done button
     */
    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneBtn.addActionListener(listenerForDoneButton);
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
                    gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.ATTACK_BATTLE &&
                    gamePlayModel.getCurrentPlayer().isHuman()) {
    
                // Don't let player to prepare another attack if there is no valid attacking territory
                String[] validAttackTerritories = gamePlayModel.getValidAttackingTerritories(gamePlayModel.getCurrentPlayer());
                if (validAttackTerritories.length == 0) {
                    anotherAttackBtn.setEnabled(false);
                } else {
                    anotherAttackBtn.setEnabled(true);
                }
                
                Battle currentBattle = gamePlayModel.getCurrentBattle();
                
                /* Show the rolling dice result of attacker */
                StringBuilder rollResultStrBuilder = new StringBuilder(ATTACKER_ROLL_RESULT);
                for (Integer rollResult : currentBattle.getAttackerDice().getRollsResult()) {
                    rollResultStrBuilder.append(rollResult);
                    if (currentBattle.getAttackerDice().getRollsResult().indexOf(rollResult) != currentBattle.getAttackerDice().getRollsResult().size() - 1) {
                        rollResultStrBuilder.append(" - ");
                    }
                }
                attackerRolls.setText(rollResultStrBuilder.toString());
                attackerLoseArmies.setText(String.format(ATTACKER_LOSE_ARMIES, currentBattle.getAttackerLossCount()));
                attackingArmies.setText(String.format(ATT_TERRITORY_ARMIES, currentBattle.getAttackingTerritory().getName(),
                        currentBattle.getAttackingTerritory().getArmies()));
    
                /* Show the rolling dice result of defender */
                rollResultStrBuilder = new StringBuilder(DEFENDER_ROLL_RESULT);
                for (Integer rollResult : currentBattle.getDefenderDice().getRollsResult()) {
                    rollResultStrBuilder.append(rollResult);
                    if (currentBattle.getDefenderDice().getRollsResult().indexOf(rollResult) != currentBattle.getDefenderDice().getRollsResult().size() - 1) {
                        rollResultStrBuilder.append(" - ");
                    }
                }
                defenderRolls.setText(rollResultStrBuilder.toString());
                defenderLoseArmies.setText(String.format(DEFENDER_LOSE_ARMIES, currentBattle.getDefenderLossCount()));
                defendingArmies.setText(String.format(DEF_TERRITORY_ARMIES, currentBattle.getDefendingTerritory().getName(),
                        currentBattle.getDefendingTerritory().getArmies()));
            }
        }
    }
    // endregion
}
