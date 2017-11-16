/*
 * Risk Game Team 2
 * Strategy.java
 * Version 3.0
 * Nov 10, 2017
 */
package shared_resources.strategy;

import game_play.model.GamePlayModel;
import shared_resources.game_entities.Territory;

import java.util.Map;
import java.util.Vector;

/**
 * The classes implementing a concrete strategy should implement this.
 * The players use this to adopt a concrete strategy.
 */
public interface Strategy {
    String reinforcement(GamePlayModel gamePlayModel, Vector<String> selectedCards, Map<Territory, Integer> armiesToPlace);

    void attack(GamePlayModel gamePlayModel);

    String fortification(GamePlayModel gamePlayModel, String sourceTerritory, String targetTerritory, int noOfArmies);
}

