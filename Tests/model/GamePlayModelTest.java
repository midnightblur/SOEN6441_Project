package model;

import model.game_entities.GameMap;
import model.game_entities.Player;
import model.game_entities.Territory;
import model.helpers.GameMapHelper;
import model.ui_models.GamePlayModel;
import org.junit.Before;
import org.junit.Test;
import utilities.Config;

import java.util.Map;

import static org.junit.Assert.*;

public class GamePlayModelTest {
    GamePlayModel gamePlayModel1;
    GamePlayModel gamePlayModel2;
    int numOfPlayers = Config.DEFAULT_NUM_OF_PLAYERS;
    String mapFilePath = Config.DEFAULT_MAP;
    
    @Before
    public void beforeTests() {
        gamePlayModel1 = GamePlayModel.getInstance();
        try {
            gamePlayModel1.setGameMap(GameMapHelper.loadGameMap(mapFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        gamePlayModel2 = GamePlayModel.getInstance();
        try {
            gamePlayModel2.setGameMap(GameMapHelper.loadGameMap(mapFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void singletonGamePlayModelObjectTest() throws Exception {
        // checks if the two game objects are not null
        System.out.println("Testing to see if two getInstance() of RiskGame are the same.");
        assertEquals(gamePlayModel1, gamePlayModel2);
        System.out.println();
    }
    
    @Test
    public void initializePlayersTest() throws Exception {
        // testing count players
        System.out.println("Testing initialization of players, their territories, " +
                "and the number of initial armies given:");
        gamePlayModel1.initializeNewGame(numOfPlayers);
        for (Player player : gamePlayModel1.getPlayers()) {
            System.out.println("\tplayer " + player.getPlayerID() + " owns "
                    + player.getTerritories().size() + " territories, and are given "
                    + (player.getUnallocatedArmies() + player.getTerritories().size()) + " initial armies:");
            for (Map.Entry<String, Territory> entry : gamePlayModel1.getGameMap().getTerritoriesOfPlayer(player).entrySet()) {
                System.out.println("\t\t" + entry.getKey());
            }
        }
        
        assertEquals(6, gamePlayModel1.getPlayers().size());
        System.out.println();
    }
    
    @Test
    public void initializeDeckTest() throws Exception {
    }
    
    @Test
    public void drawCardTest() throws Exception {
    }
    
    @Test
    public void cardTradeInTest() throws Exception {
    }
    
    @Test
    public void reinforcementArmiesTest() throws Exception {
    }
    
    @Test
    public void fortificationTest() throws Exception {
    }
}
    
////        RiskGame riskGame = RiskGame.getInstance();
////        int numOfCards = riskGame.getGameMap().getTerritoriesCount() +
////                (riskGame.getGameMap().getTerritoriesCount() % Card.getTypesCount()) * Card.getTypesCount();
////        riskGame.initDeck();
////
////        // testing deck initialization
////        System.out.println("Testing to see if count of the deck of cards is correct:");
////        System.out.println("deck size: " + riskGame.getDeck().size());
////        for (int i = 0; i < numOfCards; i++) {
////            System.out.println("card " + (i + 1) + ": " + riskGame.getDeck().get(i).getCardType());
////        }
////        assertEquals(numOfCards, riskGame.getDeck().size());
////        System.out.println();
//    }
//
//    /*
//    // testing players armies
//        for (Player player : players) {
//            System.out.println("player " + player.getPlayerID() + "'s unallocated armies before allocation: "
//                    + player.getUnallocatedArmies());
//        }
//    // testing territory dist
//        for (Player player : players) {
//        System.out.println("player " + player.getPlayerID() + "'s territories: (total: "
//                + gameMap.getTerritoriesOfPlayer(player).size() + ")");
//        for (Map.Entry<String, Territory> entry : gameMap.getTerritoriesOfPlayer(player).entrySet()) {
//            System.out.println("\t" + entry.getIndex().getName());
//        }
//    }
//    // testing army placement
//        for (Player player : players) {
//        System.out.println("player " + player.getPlayerID() + ":");
//        for (Map.Entry<String, Territory> entry : gameMap.getTerritoriesOfPlayer(player).entrySet()) {
//            System.out.println("\t" + entry.getIndex().getName()
//                    + " (num of armies: " + entry.getIndex().getArmies() + ")." );
//        }
//    }
//    // print out to test round-robin fashion of players' turn
//    System.out.println("player " + players.elementAt(playerIndex).getPlayerID() + "'s turn to allocate army.");
//    */
//}