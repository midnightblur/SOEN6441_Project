/* 
 * Risk Game Team 2
 * GamePlayModelTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package model;

import shared_resources.game_entities.Continent;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;
import game_play.model.GamePlayModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * The Class GamePlayModelTest.
 */
public class GamePlayModelTest {
    
    /** The game play game_entities. */
    GamePlayModel gamePlayModel;
    
    /** The num of players. */
    final int numOfPlayers = 6;
    
    /** The map file path. */
    final String mapFilePath = "World.map";
    
    /**
     * Before tests.
     */
    @Before
    public void beforeTests() {
        gamePlayModel = GamePlayModel.getInstance();
        try {
            gamePlayModel.setGameMap(GameMapHelper.loadGameMap(mapFilePath));
        } catch (Exception e) {}
    }
    
    /**
     * Game play game_entities test case.
     */
    @Test
    public void gamePlayModelTestCase() {
        gamePlayModel.initializeNewGame(numOfPlayers);
        // testing initialization of players' territories and armies
        System.out.println("Testing initialization of players, their territories, " +
                "and the number of initial armies given:");
        for (Player player : gamePlayModel.getPlayers()) {
            System.out.println("player " + player.getPlayerID() + " owns "
                    + player.getTerritories().size() + " territories, and are given "
                    + (player.getUnallocatedArmies() + player.getTerritories().size()) + " initial armies:");
            for (Map.Entry<String, Territory> entry : gamePlayModel.getGameMap().getTerritoriesOfPlayer(player).entrySet()) {
                System.out.println("\t" + entry.getKey());
            }
            assertEquals(7, player.getTerritories().size());
            assertEquals(19, player.getUnallocatedArmies() + player.getTerritories().size());
        }
        assertEquals(6, gamePlayModel.getPlayers().size());
        System.out.println();
   
        // testing initialization of the deck
        System.out.println("Deck size at the beginning of the game: " + gamePlayModel.getDeck().size() + 5*6);
        assertEquals(42, gamePlayModel.getDeck().size() + 5*6);
        System.out.println();
        
        // testing calculation of reinforcement armies
        System.out.println("Players' reinforcement armies:");
        int continentValue = 0;
        for (Player player : gamePlayModel.getPlayers()) {
            gamePlayModel.setCurrentPlayer(player);
            int initialUnallocatedArmies = gamePlayModel.getCurrentPlayer().getUnallocatedArmies();
            gamePlayModel.addReinforcementForCurrPlayer();
            System.out.println("Reinforcement armies gained by Player " + player.getPlayerID() + ": "
                    + (gamePlayModel.getCurrentPlayer().getUnallocatedArmies() - initialUnallocatedArmies));
            for (Map.Entry<String, Continent> entry : gamePlayModel.getGameMap().getContinents().entrySet()) {
                if (gamePlayModel.getCurrentPlayer().getPlayerName().compareTo(entry.getValue().getContinentOwner()) == 0) {
                    continentValue = entry.getValue().getControlValue();
                }
            }
            assertEquals(3 + continentValue, gamePlayModel.getCurrentPlayer()
                    .getUnallocatedArmies() - initialUnallocatedArmies);
        }
    }
}