package model;

import org.junit.Test;
import util.Config;

import java.util.Map;

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
        GameMap gameMap = GameMapHandler.loadGameMap(Config.DEFAULT_MAP);
        RiskGame riskGame = RiskGame.getInstance();
        riskGame.initPlayers(Config.DEFAULT_NUM_OF_PLAYERS);
        
        // testing count players
        System.out.println("Testing to see if count of the list of players is correct:");
        for (int i = 0; i < riskGame.getPlayers().size(); i++) {
            System.out.println("\tplayer " + riskGame.getPlayers().get(i).getPlayerID());
        }
        assertEquals(6, riskGame.getPlayers().size());
    }
    
    /*
    // TESTING... TO BE PUT INTO JUNIT TESTING LATER ON
        System.out.println("number of territories: " + gameMap.getTerritoriesCount());
    
    // testing deck initialization
        System.out.println("deck size: " + deck.size());
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
    */
}