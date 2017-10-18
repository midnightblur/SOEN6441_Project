package model;

import model.game_entities.Continent;
import model.game_entities.Player;
import model.game_entities.Territory;
import model.helpers.GameMapHelper;
import model.ui_models.GamePlayModel;
import org.junit.Before;
import org.junit.Test;
import utilities.Config;

import java.util.Map;
import java.util.Vector;

import static org.junit.Assert.*;

public class GamePlayModelTest {
    GamePlayModel gamePlayModel;
    int numOfPlayers = Config.DEFAULT_NUM_OF_PLAYERS;
    String mapFilePath = Config.DEFAULT_MAP;
    
    @Before
    public void beforeTests() {
        gamePlayModel = GamePlayModel.getInstance();
        try {
            gamePlayModel.setGameMap(GameMapHelper.loadGameMap(mapFilePath));
        } catch (Exception e) {}
    }
    
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
            assertEquals(8, player.getUnallocatedArmies() + player.getTerritories().size());
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