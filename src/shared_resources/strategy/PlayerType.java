/*
 * Risk Game Team 2
 * Strategy.java
 * Version 3.0
 * Nov 10, 2017
 */
package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Battle;
import shared_resources.game_entities.Territory;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import static shared_resources.utilities.Config.log;

/**
 * The classes implementing a concrete strategy should implement this.
 * The players use this to adopt a concrete strategy.
 */
public interface PlayerType extends Serializable {
    /**
     * Both players roll their dice
     *
     * @param gamePlayModel the game play model
     */
    default void attackForAllPlayers(GamePlayModel gamePlayModel) {
        Battle currentBattle = gamePlayModel.getCurrentBattle();
        log.append("        Battle between " + currentBattle.getAttacker().getPlayerName() +
                "'s " + currentBattle.getAttackingTerritory().getName() +
                    " and " + currentBattle.getDefender().getPlayerName() +
                "'s " + currentBattle.getDefendingTerritory().getName());
        
        /* Both players roll dice */
        currentBattle.attackerRollDice();
        log.append("            " + currentBattle.getAttacker().getPlayerName() + " roll dice: " +
                currentBattle.getAttackerDice().getRollsResult());
        currentBattle.defenderRollDice();
        log.append("            " + currentBattle.getDefender().getPlayerName() + " roll dice: " +
                currentBattle.getDefenderDice().getRollsResult());
    }
    
    String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace);

    void attack(GamePlayModel gamePlayModel);

    String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies);
}

