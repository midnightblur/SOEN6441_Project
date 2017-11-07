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
import java.util.Observable;
import java.util.Observer;

/**
 * Battle Result Panel used to display the information for battle outcome
 */
public class BattleResultPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String ATTACKER_ROLL_RESULT = "Attacker roll results: ";
    private static final String DEFENDER_ROLL_RESULT = "Defender roll results: ";
    private static final String ATTACKER_LOSE_ARMIES = "Attacker lose %s armies:";
    private static final String DEFENDER_LOSE_ARMIES = "Defender lose %s armies:";
    private static final String ATT_TERRITORY_ARMIES = "%s (attacking) now has %s armies left";
    private static final String DEF_TERRITORY_ARMIES = "%s (defending) now has %s armies left";
    private static final String CONTINUE_ATTACK_BUTTON = "Continue attack this territory";
    private static final String ANOTHER_ATTACK_BUTTON = "Attack another territory";
    private static final String DONE_BUTTON = "Done (to Fortification)";
    
    private JLabel attackerRolls;
    private JLabel defenderRolls;
    private JLabel attackerLoseArmies;
    private JLabel defenderLoseArmies;
    private JLabel attackingArmies;
    private JLabel defendingArmies;
    private JButton continueAttackBtn;
    private JButton attackAnotherOneBtn;
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
        continueAttackBtn = new JButton(CONTINUE_ATTACK_BUTTON);
        attackAnotherOneBtn = new JButton(ANOTHER_ATTACK_BUTTON);
        doneBtn = new JButton(DONE_BUTTON);
        
        add(attackerRolls);
        add(defenderRolls);
        add(attackerLoseArmies);
        add(attackingArmies);
        add(defenderLoseArmies);
        add(defendingArmies);
        
        add(continueAttackBtn);
        add(attackAnotherOneBtn);
        add(doneBtn);
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
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
                    gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.PLAYER_ATTACK_BATTLE) {
                Battle currentBattle = gamePlayModel.getCurrentBattle();
                
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
