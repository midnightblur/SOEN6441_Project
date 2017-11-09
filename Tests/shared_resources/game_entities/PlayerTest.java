/* 
 * Risk Game Team 2
 * PlayerTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import game_play.model.GamePlayModel;
import org.junit.Before;
import org.junit.Test;
import shared_resources.utilities.FixedGamePlayModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static shared_resources.game_entities.Card.CARD_TYPE.INFANTRY;
import static shared_resources.utilities.Config.PLAYER_COLOR;

/**
 * The Class PlayerTest tests the player colour.
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
     *
     */
    @Test
    public void tradeInCardsTestCase() {
    
    }
    
    /**
     *
     */
    @Test
    public void conquerTestCase() {
        GamePlayModel tempGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
        Player player1Attacker = tempGamePlayModel.getPlayers().get(0);
        Player player2Defender = tempGamePlayModel.getPlayers().get(1);
        
        
        
        
        
        int armiesToMove = ;
        
        tempGamePlayModel.declareAttack();
        
        player1.conquer();
        
        
        currentBattle = new Battle(attacker, attackingTerritory, numOfAtkDice, defender, defendingTerritory, numOfDefDice);
        
        
    }
    
    /**
     *
     */
    @Test
    public void fortificationTestCase() {
        GamePlayModel tempGamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
    }
    
    
    

//    Things to test:
//    - reinforcement phase (card trade in, add reinforcements, place armies, draw card)
//    - attack phase (eliminate player and get all of their cards, move armies to conquered territory)
//    - fortification phase (move armies)


    
}