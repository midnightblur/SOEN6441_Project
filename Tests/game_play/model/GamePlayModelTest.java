/*
 * Risk Game Team 2
 * GamePlayModelTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import org.junit.Before;
import org.junit.BeforeClass;
import shared_resources.utilities.FixedGamePlayModel;
import org.junit.Test;
import shared_resources.game_entities.Continent;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;


import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * This test class is used to test the startup initialization of the GamePlayModel class.
 */
public class GamePlayModelTest {
//    /*
//    Things to test:
//    - startup initialization (players, deck, territories, initial armies, army assignment, army placement)
//    - reinforcement phase (card trade in, add reinforcements, place armies, draw card)
//    - attack phase (declare attack, get neighboring territories not owned by player, eliminate player, move armies to conquered territory, get roll values)
//    - fortification phase (move armies)
//    - next player turn
//    - change game state phase
//    - get next player
//     */
//
    GamePlayModel fixedGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
    
    /**
     *
     */
    @Test
    public void initPlayerTestCase() {
        System.out.println("Testing initialization of players, their territories, " +
                "and the number of initial armies given:");
    
        System.out.println("Total of " + fixedGamePlayModel.getPlayers().size() + " players initialized.");
        for (Player player: fixedGamePlayModel.getPlayers()) {
            System.out.println("Player " + player.getPlayerID());
        }
        
        assertEquals(2, fixedGamePlayModel.getPlayers().size());
    }

    /**
     * Game play game_entities test case.
     */
    @Test
    public void gamePlayModelTestCase() {
        // testing initialization of players' territories and armies
        System.out.println("Testing initialization of players, their territories, " +
                "and the number of initial armies given:");
        for (Player player : fixedGamePlayModel.getPlayers()) {
            System.out.println("player " + player.getPlayerID() + " owns "
                    + player.getTerritories().size() + " territories, and are given "
                    + (player.getUnallocatedArmies() + player.getTerritories().size()) + " initial armies:");
            for (Map.Entry<String, Territory> entry : fixedGamePlayModel.getGameMap().getTerritoriesOfPlayer(player).entrySet()) {
                System.out.println("\t" + entry.getKey());
            }
            assertEquals(7, player.getTerritories().size());
            assertEquals(19, player.getUnallocatedArmies() + player.getTerritories().size());
        }
        assertEquals(6, fixedGamePlayModel.getPlayers().size());
        System.out.println();

        // testing initialization of the deck
        System.out.println("Deck size at the beginning of the game: " + fixedGamePlayModel.getDeck().size() + 5*6);
        assertEquals(42, fixedGamePlayModel.getDeck().size() + 5*6);
        System.out.println();

        // testing calculation of reinforcement armies
        System.out.println("Players' reinforcement armies:");
        int continentValue = 0;
        for (Player player : fixedGamePlayModel.getPlayers()) {
            fixedGamePlayModel.setCurrentPlayer(player);
            int initialUnallocatedArmies = fixedGamePlayModel.getCurrentPlayer().getUnallocatedArmies();
            fixedGamePlayModel.addReinforcementForCurrPlayer();
            System.out.println("Reinforcement armies gained by Player " + player.getPlayerID() + ": "
                    + (fixedGamePlayModel.getCurrentPlayer().getUnallocatedArmies() - initialUnallocatedArmies));
            for (Map.Entry<String, Continent> entry : fixedGamePlayModel.getGameMap().getContinents().entrySet()) {
                if (fixedGamePlayModel.getCurrentPlayer().getPlayerName().compareTo(entry.getValue().getContinentOwner(fixedGamePlayModel.getGameMap())) == 0) {
                    continentValue = entry.getValue().getControlValue();
                }
            }
            assertEquals(3 + continentValue, fixedGamePlayModel.getCurrentPlayer()
                    .getUnallocatedArmies() - initialUnallocatedArmies);
        }
    }
}