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
import static shared_resources.utilities.Config.INITIAL_ARMY_RATIO;

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
    private GamePlayModel fixedGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
    
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
        int armiesToGive = (int) (fixedGamePlayModel.getGameMap().getTerritoriesCount() * INITIAL_ARMY_RATIO / fixedGamePlayModel.getPlayers().size());
        
        System.out.println("Testing initial armies given to the players:");
        System.out.println("Total of " + armiesToGive + " initial armies given to each player.");
        for (Player player : fixedGamePlayModel.getPlayers()) {
            System.out.println("\tPlayer " + player.getPlayerID() + "'s initial unallocated armies = " +
                    (player.getUnallocatedArmies() + player.getTerritories().size()));
        }
        System.out.println();
    
        assertEquals(17, fixedGamePlayModel.getPlayers().get(0).getUnallocatedArmies() +
                fixedGamePlayModel.getPlayers().get(0).getTerritories().size());
        assertEquals(17, fixedGamePlayModel.getPlayers().get(1).getUnallocatedArmies() +
                fixedGamePlayModel.getPlayers().get(1).getTerritories().size());
    }
    
    /**
     *
     */
    @Test
    public void assignOneArmyPerTerritoryTestCase() {
        int armiesToGive = (int) (fixedGamePlayModel.getGameMap().getTerritoriesCount() * INITIAL_ARMY_RATIO / fixedGamePlayModel.getPlayers().size());
    
        System.out.println("Testing assignment of one army per territory:");
        for (Player player : fixedGamePlayModel.getPlayers()) {
            int counter = 1;
            System.out.println("\tPlayer " + player.getPlayerID() + " has " + player.getUnallocatedArmies() + " unallocated armies remaining.");
            for (int i = 0; i < player.getTerritories().size(); i++) {
                System.out.println("\t\t" + counter + ") " + player.getTerritories().get(i).getName() +
                        " has " + player.getTerritories().get(i).getArmies() + " army.");
                counter++;
            }
        }
        System.out.println();
    
        for (Map.Entry<String, Territory> entry : fixedGamePlayModel.getGameMap().getTerritories().entrySet()) {
            assertEquals(1, entry.getValue().getArmies());
        }
    }
}