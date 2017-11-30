/*
 * Risk Game Team 2
 * TournamentResultsModelTest.java
 * Version 3.0
 * Nov 29, 2017
 */
package game_play.model;

import org.junit.BeforeClass;
import org.junit.Test;
import shared_resources.game_entities.Player;
import shared_resources.strategy.BenevolentBot;
import tests_resources.FixedGamePlayModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

import static junit.framework.TestCase.assertEquals;

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
    private static int gameCount;
    private static int maxTurns;
    private static Vector<String> mapSet;
    private static TournamentModel tournamentModel;
    
    private static int minBound;
    private static int maxBound;
    
    /**
     * Prepare the tournament context
     */
    @BeforeClass
    public static void setup() {
        tournamentModel = new TournamentModel();
        gameCount = 2;
        maxTurns = 10;
        minBound = 2;
        maxBound = 4;
        
        /* Build a new game and set the players and their strategies */
        GamePlayModel gamePlayModel = FixedGamePlayModel.getFixedGamePlayModel();
        gamePlayModel.setMaxTurns(maxTurns);
        
        /* Make all players to be of type Benevolent */
        for (Player player : gamePlayModel.getPlayers()) {
            player.setPlayerType(new BenevolentBot());
        }
        
        /* Add the same game to the set of games as many times as the gameCount is set. Same for the map. */
        mapSet = new Vector<>();
        for (int i = 0; i < gameCount; i++) {
            mapSet.add(gamePlayModel.getGameMap().getMapName());
            tournamentModel.getTournamentSet().add(gamePlayModel);
        }
        
        /* set attributes in the tournament model */
        tournamentModel.setEnteredGames(gameCount);
        tournamentModel.setEnteredMaxTurns(maxTurns);
        tournamentModel.setEnteredPlayers(gamePlayModel.getPlayers().size());
        tournamentModel.setStrMapSet(mapSet);
        tournamentModel.setTempGamePlayModel(gamePlayModel);
        
        /* Play the games in the game set */
        try {
            tournamentModel.startTournament();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Testing for game results
     * We expect to always have a draw after the maximum turns since players are all benevolent
     */
    @Test
    public void testResults() {
        DefaultTableModel results = tournamentModel.getTournamentResultsModel().getResultsModel();
        String resultText;
        for (int row = 0; row < results.getRowCount(); row++) {
            for (int column = 1; column < results.getColumnCount(); column++) { //first column is the map name so we skip it
                resultText = (String) results.getValueAt(row, column);
                System.out.println("Game #" + column + ": We expect a \"Draw " + maxTurns + " turns\" result. We obtained a \"" + resultText + "\" result.");
                assertEquals("Draw " + maxTurns + " turns", resultText);
            }
        }
    }
    
    /**
     * Testing for out of bounds entry in a text field
     */
    @Test
    public void testInvalidIntegerEntries() {
        JTextField textInput = new JTextField();
        textInput.setText("50");
        System.out.println("We expect an input between 2 and 4. We test 50 as invalid input.");
        assertEquals(-1, tournamentModel.validateEntry(textInput, minBound, maxBound));
    }
    
    /**
     * Testing for a larger than expected selection in a list
     */
    @Test
    public void testInvalidStringEntries() {
        JList listInput = new JList();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < 10; i++) {
            listModel.add(i, "list option");
        }
        listInput.setModel(listModel);
        int[] selection = { 0, 1, 2, 3, 4, 5 };
        listInput.setSelectedIndices(selection);
        System.out.println("We expect a selection of 2 to 4 list elements. We test a selection of 6 elements.");
        assertEquals(-1, tournamentModel.validateEntry(listInput, minBound, maxBound));
    }
    
}