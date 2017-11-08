/* 
 * Risk Game Team 2
 * PlayerTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shared_resources.utilities.Config.PLAYER_COLOR;

/**
 * The Class PlayerTest tests the player colour.
 */
public class PlayerTest {
    private Player[] arrayOfPlayers;
    
    /**
     * Makes an array of 6 players
     */
    @Before
    public void setUp() {
        arrayOfPlayers = new Player[6];
        for (int i = 0; i < 6; i++) {
            arrayOfPlayers[i] = new Player();
        }
    }
    
    /**
     * Tests the correct color of the players.
     */
    @Test
    public void getColor() {
        System.out.println("Testing if players colours are the designated ones...");
        for (int i = 0; i < 6; i++) {
            System.out.println("Expected color: " + PLAYER_COLOR[i] + "Actual color: " + arrayOfPlayers[i].getColor());
            assertEquals(PLAYER_COLOR[i], arrayOfPlayers[i].getColor());
        }
    }
    
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
    
}