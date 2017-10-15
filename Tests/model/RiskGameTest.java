package model;

import org.junit.Test;
import utilities.Config;

import static org.junit.Assert.*;

public class RiskGameTest {
    @Test
    public void getInstance() throws Exception {
        RiskGame riskGameObj1 = RiskGame.getInstance();
        RiskGame riskGameObj2 = RiskGame.getInstance();
        
        // checks if the two game objects are not null
        System.out.println("Testing to see if two getInstance() of RiskGame are the same.");
        assertEquals(riskGameObj1, riskGameObj2);
        System.out.println();
    }

    @Test
    public void initializePlayers() throws Exception {
//        RiskGame riskGame = RiskGame.getInstance();
//        riskGame.initPlayers(Config.DEFAULT_NUM_OF_PLAYERS);
//
//        // testing count players
//        System.out.println("Testing to see if count of the list of players is correct:");
//        for (int i = 0; i < riskGame.getPlayers().size(); i++) {
//            System.out.println("\tplayer " + riskGame.getPlayers().get(i).getPlayerID());
//        }
//        assertEquals(6, riskGame.getPlayers().size());
//        System.out.println();
    }
    
    @Test
    public void initializeDeck() throws Exception {
//        RiskGame riskGame = RiskGame.getInstance();
//        int numOfCards = riskGame.getGameMap().getTerritoriesCount() +
//                (riskGame.getGameMap().getTerritoriesCount() % Card.getTypesCount()) * Card.getTypesCount();
//        riskGame.initDeck();
//
//        // testing deck initialization
//        System.out.println("Testing to see if count of the deck of cards is correct:");
//        System.out.println("deck size: " + riskGame.getDeck().size());
//        for (int i = 0; i < numOfCards; i++) {
//            System.out.println("card " + (i + 1) + ": " + riskGame.getDeck().get(i).getCardType());
//        }
//        assertEquals(numOfCards, riskGame.getDeck().size());
//        System.out.println();
    }
    
    /*
    // testing players armies
        for (Player player : players) {
            System.out.println("player " + player.getPlayerID() + "'s unallocated armies before allocation: "
                    + player.getUnallocatedArmies());
        }
    // testing territory dist
        for (Player player : players) {
        System.out.println("player " + player.getPlayerID() + "'s territories: (total: "
                + gameMap.getTerritoriesOfPlayer(player).size() + ")");
        for (Map.Entry<String, Territory> entry : gameMap.getTerritoriesOfPlayer(player).entrySet()) {
            System.out.println("\t" + entry.getValue().getName());
        }
    }
    // testing army placement
        for (Player player : players) {
        System.out.println("player " + player.getPlayerID() + ":");
        for (Map.Entry<String, Territory> entry : gameMap.getTerritoriesOfPlayer(player).entrySet()) {
            System.out.println("\t" + entry.getValue().getName()
                    + " (num of armies: " + entry.getValue().getArmies() + ")." );
        }
    }
    // print out to test round-robin fashion of players' turn
    System.out.println("player " + players.elementAt(playerIndex).getPlayerID() + "'s turn to allocate army.");
    */
}