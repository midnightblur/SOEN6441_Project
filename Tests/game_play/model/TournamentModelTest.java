/*
 * Risk Game Team 2
 * TournamentResultsModelTest.java
 * Version 3.0
 * Nov 29, 2017
 */
package game_play.model;

import javafx.util.Pair;
import org.junit.BeforeClass;
import org.junit.Test;
import shared_resources.game_entities.Player;
import shared_resources.strategy.BenevolentBot;
import tests_resources.FixedGamePlayModel;

import java.util.Vector;

import static junit.framework.TestCase.assertEquals;
import static shared_resources.utilities.Config.GAME_STATES.STARTUP;
import static shared_resources.utilities.Config.GAME_STATES.VICTORY;

/**
 * Testing the tournament mode
 * A fixed game is used to play multiple times
 * The number of players is set to the maximum allowed by the map of the game
 * The players are all set to be of type Benevolent
 * We expect that there is no winner
 * We expect that we play as much as the maximum turns is set
 *
 * @author Team 2
 * @version 3.0
 */
public class TournamentModelTest {
    private static int maxTurns;
    private static Vector<Pair<String, Integer>> results;
    
    /**
     * Prepare the tournament context
     */
    @BeforeClass
    public static void setup() {
        Vector<GamePlayModel> gameSet = new Vector<>();
        int gameCount = 3;
        results = new Vector<>();
        maxTurns = 20;
        
        /* Build a new game and set the players and their strategies */
        GamePlayModel gamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
        gamePlayModel.setMaxTurns(maxTurns);
        
        /* Make all players to be of type Benevolent */
        for (Player player : gamePlayModel.getPlayers()) {
            player.setPlayerType(new BenevolentBot());
        }
        
        /* Add the same game to the set of games as many times as the gameCount is set */
        for (int i = 0; i < gameCount; i++) {
            gameSet.add(gamePlayModel);
        }
        
        /* Play the games in the game set */
        for (GamePlayModel gameToPlay : gameSet) {      // for each game in the set of games
            for (int i = 0; i < maxTurns; i++) {        // play as long as we reached maximum turns or get VICTORY
                gameToPlay.setGameState(STARTUP);
                gameToPlay.initializeNewGameForTournament();
                gameToPlay.startTheGame();
                while (gameToPlay.getTurnCounter() < maxTurns && gameToPlay.getGameState() != VICTORY) {
                    gameToPlay.letBotsPlay();
                }
                // collect the winner of the game and the played turns
                results.add(new Pair<>(gameToPlay.getWinner(), gameToPlay.getTurnCounter()));
            }
        }
    }
    
    /**
     * Testing for game results
     * We expect to always have a draw since players are all benevolent
     */
    @Test
    public void testDraw() {
        for (Pair<String, Integer> result : results) {
            System.out.println("We expect a \"Draw\" result. We obtained a \"" + result.getKey() + "\" result.");
            assertEquals("Draw", result.getKey());
        }
    }
    
    /**
     * Testing for turn counts
     * We expect to always exhaust the turn counts
     */
    @Test
    public void testTurns() {
        for (Pair<String, Integer> result : results) {
            System.out.println("We expect " + maxTurns + " turns. We played for " + result.getValue() + " turns.");
            assertEquals(maxTurns, (int) result.getValue());
        }
    }
    
}