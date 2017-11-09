/* 
 * Risk Game Team 2
 * PlayerTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import game_play.model.GamePlayModel;
import org.junit.Test;
import shared_resources.utilities.FixedGamePlayModel;

import static org.junit.Assert.assertEquals;
import static shared_resources.utilities.Config.PLAYER_COLOR;

/**
 * The Class PlayerTest tests the player colour.
 *
 * @author Team 2
 * @version 1.0
 */
public class PlayerTest {
    private GamePlayModel fixedGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
    
    /**
     * Tests the correct color of the players.
     */
    @Test
    public void getColor() {
        Player[] arrayOfPlayers = new Player[6];
        for (int i = 0; i < fixedGamePlayModel.getPlayers().size(); i++) {
            arrayOfPlayers[i] = fixedGamePlayModel.getPlayers().get(i);
        }
        
        System.out.println("Testing if players colours are the designated ones...");
        for (int i = 0; i < fixedGamePlayModel.getPlayers().size(); i++) {
            System.out.println("Expected color: " + PLAYER_COLOR[i] + "Actual color: " + arrayOfPlayers[i].getColor());
            assertEquals(PLAYER_COLOR[i], arrayOfPlayers[i].getColor());
        }
    }
    
    /**
     * Tests the correct number of armies that can be moved to the conquered country.
     */
    @Test
    public void conquerTestCase() {
        GamePlayModel tempGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
        Player player = tempGamePlayModel.getPlayers().get(0);
        Territory attackingTerritory = tempGamePlayModel.getGameMap().getATerritory("1c");
        Territory defendingTerritory = tempGamePlayModel.getGameMap().getATerritory("1t");
    
        System.out.println("Testing valid number of armies that can be moved to conquered territory:");
        
        int armiesToMove = 1;
        attackingTerritory.setArmies(3);
        defendingTerritory.setArmies(0);
        System.out.println("\tIf Player 1 has [" + (attackingTerritory.getArmies() - 1) + "] armies in " +
                "Attacking territory and wants to move [" + armiesToMove + "] army to the " +
                "Conquered territory...");
        tempGamePlayModel.declareAttack(attackingTerritory.getName(), defendingTerritory.getName(), 0, 0);
        player.conquer(tempGamePlayModel, armiesToMove);
        System.out.println("\t\tThen the Attacking territory will have [" + attackingTerritory
                .getArmies() + "] army left, and Conquered territory will have [" +
                defendingTerritory.getArmies() + "] army.");
        assertEquals(1, defendingTerritory.getArmies());
    
        armiesToMove = 2;
        attackingTerritory.setArmies(3);
        defendingTerritory.setArmies(0);
        System.out.println("\tIf Player 1 has [" + (attackingTerritory.getArmies() - 1) + "] armies in " +
                "Attacking territory and wants to move [" + armiesToMove + "] army to the " +
                "Conquered territory...");
        tempGamePlayModel.declareAttack(attackingTerritory.getName(), defendingTerritory.getName(), 0, 0);
        player.conquer(tempGamePlayModel, armiesToMove);
        System.out.println("\t\tThen the Attacking territory will have [" + attackingTerritory
                .getArmies() + "] army left, and Conquered territory will have [" +
                defendingTerritory.getArmies() + "] army (No valid move was made).");
        assertEquals(0, defendingTerritory.getArmies());
    
        armiesToMove = 3;
        attackingTerritory.setArmies(3);
        defendingTerritory.setArmies(0);
        System.out.println("\tIf Player 1 has [" + (attackingTerritory.getArmies() - 1) + "] armies in " +
                "Attacking territory and wants to move [" + armiesToMove + "] army to the " +
                "Conquered territory...");
        tempGamePlayModel.declareAttack(attackingTerritory.getName(), defendingTerritory.getName(), 0, 0);
        player.conquer(tempGamePlayModel, armiesToMove);
        System.out.println("\t\tThen the Attacking territory will have [" + attackingTerritory
                .getArmies() + "] army left, and Conquered territory will have [" +
                defendingTerritory.getArmies() + "] army (No valid move was made).");
        assertEquals(0, defendingTerritory.getArmies());
        
        System.out.println();
    }
    
    /**
     *
     */
    @Test
    public void fortificationTestCase() {
        GamePlayModel tempGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
        Player player = tempGamePlayModel.getPlayers().get(0);
        Territory sourceT = tempGamePlayModel.getGameMap().getATerritory("1c");
        Territory targetT = tempGamePlayModel.getGameMap().getATerritory("1t");
        sourceT.setArmies(0);
        targetT.setArmies(0);
        
        int noOfArmies = 1;
        player.fortification(tempGamePlayModel, sourceT.getName(), targetT.getName(), noOfArmies);
    }
    
    
    

//    Things to test:
//    - reinforcement phase (card trade in, add reinforcements, place armies, draw card)
//    - attack phase (eliminate player and get all of their cards, move armies to conquered territory)
//    - fortification phase (move armies)


    
}