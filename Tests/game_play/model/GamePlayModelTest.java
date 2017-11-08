/*
 * Risk Game Team 2
 * GamePlayModelTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import org.junit.Before;
import org.junit.BeforeClass;
import shared_resources.game_entities.Card;
import shared_resources.utilities.FixedGamePlayModel;
import org.junit.Test;
import shared_resources.game_entities.Continent;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;


import java.util.Map;
import java.util.Vector;

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
     * Test case for initPlayer() in GamePlayModel class.
     */
    @Test
    public void initPlayerTestCase() {
        System.out.println("Testing initialization of the players:");
        System.out.println("Total of " + fixedGamePlayModel.getPlayers().size() + " players initialized.");
        for (Player player : fixedGamePlayModel.getPlayers()) {
            System.out.println("\tPlayer " + player.getPlayerID());
        }
        System.out.println();
        
        assertEquals(2, fixedGamePlayModel.getPlayers().size());
    }
    
    /**
     *
     */
    @Test
    public void initDeckTestCase() {
        System.out.println("Testing initialization of the deck:");
        System.out.println("Total of " + fixedGamePlayModel.getDeck().size() + " cards initialized in the deck.");
        int counter = 1;
        for (Card card : fixedGamePlayModel.getDeck()) {
            System.out.println("\t" + counter + ") Card " + card.getCardType());
            counter++;
        }
        System.out.println();
        
        assertEquals(15, fixedGamePlayModel.getDeck().size());
    }
    
    /**
     *
     */
    @Test
    public void fixedDistributeTerritoriesTestCase() {
        String p1territories = "";
        String p2territories = "";
       
        System.out.println("Testing fixed distribution of territories:");
        System.out.println("Total of " + fixedGamePlayModel.getGameMap().getTerritoriesCount() + " territories distributed.");
        for (Player player : fixedGamePlayModel.getPlayers()) {
            int counter = 1;
            System.out.println("\tPlayer " + player.getPlayerID() + " owns " + player.getTerritories().size() + " territories");
            for (int i = 0; i < player.getTerritories().size(); i++) {
                if (player.getPlayerID() == 1) {
                    p1territories += player.getTerritories().get(i).getName();
                } else {
                    p2territories += player.getTerritories().get(i).getName();
                }
                System.out.println("\t\t" + counter + ") " + player.getTerritories().get(i).getName());
                counter++;
            }
        }
        System.out.println();
    
        assertEquals(13, fixedGamePlayModel.getGameMap().getTerritoriesCount());
        assertEquals(7, fixedGamePlayModel.getPlayers().get(0).getTerritories().size());
        assertEquals("1c2c3c4c5c6c7c", p1territories);
        assertEquals(6, fixedGamePlayModel.getPlayers().get(1).getTerritories().size());
        assertEquals("1t2t3t4t5t6t", p2territories);
    }
    
    /**
     *
     */
    @Test
    public void giveInitialArmiesTestCase() {
    
    }
    
    /**
     *
     */
    @Test
    public void assignOneArmyPerTerritoryTestCase() {
    
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