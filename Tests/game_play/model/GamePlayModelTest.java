/*
 * Risk Game Team 2
 * GamePlayModelTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import shared_resources.game_entities.Card;
import shared_resources.game_entities.Continent;
import shared_resources.utilities.FixedGamePlayModel;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;
import java.util.Map;
import static shared_resources.utilities.Config.INITIAL_ARMY_RATIO;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This test class is used to test the initialization of the game of a correct startup phase,
 * test the calculation of correct number of reinforcement armies, and test various functions of
 * attack phase, such as attacker/defender validation, moving armies after conquering, and end game
 * state of the GamePlayModel class.
 */
public class GamePlayModelTest {
    private GamePlayModel fixedGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
    
    /**
     * Test case for correct number of initialized players.
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
     * Test case for correct number of cards in the deck.
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
     * Test case for correct distribution of the territories to the players.
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
     * Test case for correct distribution of the initial armies to the players.
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
     * Test case for correct assignment of one army for each territory.
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
    
    /**
     * Test case for correct number of reinforcement armies for Player 1.
     */
    @Test
    public void addReinforcementForCurrPlayerTestCase() {
        GamePlayModel tempGamePlayModel = fixedGamePlayModel;
        
        Player player1 = tempGamePlayModel.getPlayers().get(0);
        player1.setUnallocatedArmies(0);  // deplete unallocated armies of player 1 from initialization
        tempGamePlayModel.setCurrentPlayer(player1);
        tempGamePlayModel.addReinforcementForCurrPlayer();
    
        System.out.println("Testing number of reinforcement armies for Player 1:");
        System.out.println("Player 1 has " + player1.getTerritories().size() +
                " territory(ies) and controls " + player1.getContinents(tempGamePlayModel
                .getGameMap()).size() + " continent(s).");
        if (player1.getContinents(tempGamePlayModel.getGameMap()).size() != 0) {
            for (int i = 0 ; i < player1.getContinents(tempGamePlayModel.getGameMap()).size(); i++) {
                System.out.println("Player 1's total continent control value = " + player1.getContinents(tempGamePlayModel.getGameMap()).get(i).getControlValue());
            }
        }
        System.out.println("\tReinforcement armies given to Player 1 = " + player1.getUnallocatedArmies());
        System.out.println();
        
        assertEquals(6, player1.getUnallocatedArmies());
    }
    
    /**
     *
     */
    @Test
    public void getValidAttackingTerritoriesTestCase() {
        GamePlayModel tempGamePlayModel = fixedGamePlayModel;
        
        Player player1 = tempGamePlayModel.getPlayers().get(0);
    
        System.out.println("Testing the list of territories that can be used to make valid attack moves for Player 1:");
        System.out.println("If Player 1 has the following territories and armies...");
        for (int i = 0; i < player1.getTerritories().size(); i++) {
            player1.getTerritories().get(i).setArmies(7 - i);
            System.out.println("\tTerritory " + player1.getTerritories().get(i).getName() +
                    " with " + player1.getTerritories().get(i).getArmies() + " armies and " +
                    "neighboring countries of" + player1.getTerritories().get(i).getNeighbors());
        }
        
        System.out.print("\tThen, the list of valid territories to attack from for Player 1 = ");
        String[] strArrOfValidAttackTerritories = tempGamePlayModel.getValidAttackingTerritories(player1);
        for (int i = 0; i < strArrOfValidAttackTerritories.length; i++) {
            if (i != 0) {
                System.out.print(", ");
            }
            System.out.print(strArrOfValidAttackTerritories[i]);
        }
        System.out.println("\n");
        
        assertEquals("1c", strArrOfValidAttackTerritories[0]);
    }
    
    /**
     * Test case for correct number of maximum dice that can be used to attack for Player 1 with Territory 1c.
     */
    @Test
    public void getMaxAttackingRollTestCase() {
        GamePlayModel tempGamePlayModel = fixedGamePlayModel;
    
        Player player1 = tempGamePlayModel.getPlayers().get(0);
        Territory tempTerritory = player1.getTerritories().get(0);
    
        System.out.println("Testing maximum number of dice a Player 1 can use to attack from Territory " +
                tempTerritory.getName() + ":");
        
        tempTerritory.setArmies(5);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to attack = " + tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        assertEquals(3, tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        
        tempTerritory.setArmies(4);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to attack = " + tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        assertEquals(3, tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        
        tempTerritory.setArmies(3);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to attack = " + tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        assertEquals(2, tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        
        tempTerritory.setArmies(2);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to attack = " + tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        assertEquals(1, tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        
        tempTerritory.setArmies(1);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " army, max number of dice that can be used to attack = " + tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        assertEquals(0, tempGamePlayModel.getMaxAttackingRoll(tempTerritory.getName()));
        
        System.out.println();
    }
    
    /**
     * Test case for correct number of maximum dice that can be used to defend Territory 1c for Player 1.
     */
    @Test
    public void getMaxDefendingRollTestCase() {
        GamePlayModel tempGamePlayModel = fixedGamePlayModel;
    
        Player player1 = tempGamePlayModel.getPlayers().get(0);
        Territory tempTerritory = player1.getTerritories().get(0);
    
        System.out.println("Testing maximum number of dice a Player 1 can use to defend Territory " +
                tempTerritory.getName() + ":");
    
        tempTerritory.setArmies(5);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to defend = " + tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
        assertEquals(2, tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
    
        tempTerritory.setArmies(4);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to defend = " + tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
        assertEquals(2, tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
    
        tempTerritory.setArmies(3);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to defend = " + tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
        assertEquals(2, tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
    
        tempTerritory.setArmies(2);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " armies, max number of dice that can be used to defend = " + tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
        assertEquals(2, tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
    
        tempTerritory.setArmies(1);
        System.out.println("\tIf Territory " + tempTerritory.getName() + " has " + tempTerritory.getArmies() +
                " army, max number of dice that can be used to defend = " + tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
        assertEquals(1, tempGamePlayModel.getMaxDefendingRoll(tempTerritory.getName()));
    
        System.out.println();
    }
    
    /**
     *
     */
    @Test
    public void gameVictoryTestCase() {
    
    }
}