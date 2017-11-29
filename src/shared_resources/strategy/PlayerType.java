/*
 * Risk Game Team 2
 * PlayerType.java
 * Version 3.0
 * Nov 10, 2017
 */
package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

/**
 * The classes implementing a concrete strategy should implement this.
 * The players use this to adopt a concrete strategy.
 */
public interface PlayerType extends Serializable {
    /**
     * Reinforcement method for players
     *
     * @param gamePlayModel the game play model
     * @param selectedCards the selected cards
     * @param armiesToPlace the amount of armies to be placed
     *
     * @return the message to user if reinforcement was successful or not
     */
    String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace);
    
    /**
     * This method allows a player to make an attack move with an opponent player based on the
     * current battle state of the game. The method rolls the number of dice for the attacker
     * and the defender, and decides the outcome of the battle by comparing the highest roll
     * depending on the number of dice used by both players. Then also checks if the attacking
     * player in current battle has conquered any territories, or eliminated any players in
     * that attack turn.
     *
     * @param gamePlayModel the game play model
     */
    void attack(GamePlayModel gamePlayModel);
    
    /**
     * Implement the Fortification Phase of a particular player.
     *
     * The method gives a player an option to move any number of armies from one country to
     * another. The method only allows only one such move that is valid, which requires that
     * the two countries that the player picks must be owned by that player, be different
     * territories from one another, be adjacent to one another, and must have more armies
     * in the territory than the number of armies specified by the player (a territory must
     * have more than 1 army at minimum).
     *
     * @param gamePlayModel   The GamePlayModel containing the state of the game
     * @param sourceTerritory String value of the name of the source Territory
     * @param targetTerritory String value of the name of the target Territory
     * @param noOfArmies      Integer value of the number of armies to be moved
     *
     * @return String value of the messages that will be displayed to the user
     */
    String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies);
    
    /**
     * Moves armies to conquered territory
     *
     * @param gamePlayModel the game model
     */
    void moveArmiesToConqueredTerritory(GamePlayModel gamePlayModel);
}

