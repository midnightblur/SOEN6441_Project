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
    public void reinforcementTestCase() {
        GamePlayModel tempGamePlayModel = fixedGamePlayModel;
        Player player1 = tempGamePlayModel.getPlayers().get(0);
        Vector<String> selectedCards = new Vector<>();
        Map<Territory, Integer> armiesToPlace = new HashMap<>();
        armiesToPlace.put(tempGamePlayModel.getGameMap().getATerritory("1c"), 3);
        
        // make all territories in Cubes continent to belong to Player 1
        for (Map.Entry<String, Territory> entry : tempGamePlayModel.getGameMap().getTerritories().entrySet()) {
            if (entry.getValue().getContinent() == "Cubes") {
                entry.getValue().setOwner(player1);
            }
        }
        
        // give Player 1 three INFANTRY cards and remove them from the deck
        for (int i = 0 ; i < 4; i++) {
            int cardCounter = 0;
            for (Card card : tempGamePlayModel.getDeck()) {
                if (cardCounter >= 3) {
                    break;
                }
                if (card.getCardType().equals(INFANTRY)) {
                    player1.addCardToPlayersHand(card);
                    tempGamePlayModel.getDeck().remove(card);
                    selectedCards.add("INFANTRY");
                    cardCounter++;
                }
            }
        }
        
        player1.tradeInCards(tempGamePlayModel, selectedCards);
        
        assertEquals("Cards successfully traded in!", player1.tradeInCards(tempGamePlayModel, selectedCards));
        
        player1.distributeArmies(tempGamePlayModel, armiesToPlace);
        
    }
    
    /**
     *
     */
    @Test
    public void attackTestCase() {
    
    }
    
    /**
     *
     */
    @Test
    public void fortificationTestCase() {
    
    }

//    Things to test:
//    - reinforcement phase (card trade in, add reinforcements, place armies, draw card)
//    - attack phase (eliminate player and get all of their cards, move armies to conquered territory)
//    - fortification phase (move armies)


    
}