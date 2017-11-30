/*
 * Risk Game Team 2
 * TournamentModel.java
 * Version 3.0
 * Nov 29, 2017
 */
package game_play.model;

import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static shared_resources.helper.GameMapHelper.loadGameMap;
import static shared_resources.utilities.Config.GAME_STATES.STARTUP;
import static shared_resources.utilities.Config.GAME_STATES.VICTORY;

/**
 * Tournament model class is responsible for handling the logic for tournament mode
 *
 * @author Team 2
 * @version 3.0
 */
public class TournamentModel {
    private TournamentResultsModel tournamentResultsModel;
    private GamePlayModel tempGamePlayModel;
    private Vector<String> strMapSet;
    private Vector<GamePlayModel> tournamentSet;
    private int enteredGames;
    private int enteredPlayers;
    private int enteredMaxTurns;
    
    /**
     * Instantiate new tournament model
     */
    public TournamentModel() {
        tempGamePlayModel = new GamePlayModel();
        strMapSet = new Vector<>();
        tournamentSet = new Vector<>();
    }
    
    /**
     * Gets the temporary game play model
     *
     * @return the temporary game play model
     */
    public GamePlayModel getTempGamePlayModel() {
        return tempGamePlayModel;
    }
    
    /**
     * Gets the tournament result model
     *
     * @return the tournament result model
     */
    public TournamentResultsModel getTournamentResultsModel() {
        return tournamentResultsModel;
    }
    
    /**
     * Setup and Validate input for tournament
     *
     * @param mapsList          the maps list
     * @param gamesCountField   the games count field
     * @param maxTurnField      the max turn field
     * @param playersCountField the players count field
     *
     * @return the message
     */
    public String setupTournament(JList<String> mapsList, JTextField gamesCountField,
                                  JTextField maxTurnField, JTextField playersCountField) {
        int maxPlayers; // will be set to the minimum allowed players based on the maps in the set of games
        /* Validate UI entries */
        int enteredMaps = validateEntry(mapsList, 1, 5);
        if (enteredMaps == -1) {
            return "Your entry for " + mapsList.getName() + " must between 1 and 5";
        }
        
        enteredGames = validateEntry(gamesCountField, 1, 5);
        if (enteredGames == -1) {
            return "Your entry for " + gamesCountField.getName() + " must between 1 and 5";
        }
        
        enteredMaxTurns = validateEntry(maxTurnField, 10, 50);
        if (enteredMaxTurns == -1) {
            return "Your entry for " + maxTurnField.getName() + " must between 10 and 50";
        }
        
        /* Load the maps first (we will determine the allowed number of players based on maps) */
        for (String selectedMap : mapsList.getSelectedValuesList()) {
            try {
                strMapSet.add(selectedMap);                         // add the string game map to the strMapSet
                GameMap gameMap = loadGameMap(selectedMap);         // load the selected map
                GamePlayModel gamePlayModel = new GamePlayModel();  // make a new game model that copies the temp game model
                gamePlayModel.setGameMap(gameMap);                  // set the game map
                tournamentSet.add(gamePlayModel);                   // add the game model to the tournament set
            } catch (Exception e) {
                strMapSet.clear();
                tournamentSet.clear();
                return e.getMessage();
            }
        }

        /* determine max players across all games' maps */
        maxPlayers = tournamentSet.firstElement().getGameMap().getMaxPlayers();
        for (GamePlayModel gamePlayModel : tournamentSet) {
            maxPlayers = Math.min(maxPlayers, gamePlayModel.getGameMap().getMaxPlayers());
        }
        
        /* Now validate the number of players against the maxPlayers across the games' maps*/
        enteredPlayers = validateEntry(playersCountField, 2, maxPlayers);
        if (enteredPlayers == -1) {
            strMapSet.clear();
            tournamentSet.clear();
            return "Your entry for " + playersCountField.getName() + " must between 2 and " + maxPlayers;
        }
        
        /* initialize each game */
        // use a tempTournamentSet to set up the game to determine the player strategies
        try {
            tempGamePlayModel.setGameMap(loadGameMap(strMapSet.firstElement()));
        } catch (Exception e) {
            strMapSet.clear();
            tournamentSet.clear();
            return e.getMessage();
        }
        
        tempGamePlayModel.setGameState(STARTUP);
        Player.resetStaticNextID();
        tempGamePlayModel.initPlayers(enteredPlayers);
        
        return "Tournament started successfully";
    }
    
    /**
     * Validate UI entries against a min and max values
     * Method will throw messages to user if exceptions
     *
     * @param component the component that holds the user entry
     * @param min       the minimum bound
     * @param max       the maximum bound
     *
     * @return the valid entry or -1 otherwise
     */
    private int validateEntry(Component component, int min, int max) {
        int entry = -1;
        try {
            /* parse the field entries */
            if (component instanceof JList) {
                entry = ((JList) component).getSelectedValuesList().size();
            } else if (component instanceof JTextField) {
                entry = Integer.parseInt(((JTextField) component).getText());
            }
            
            /* validate against the min-max */
            if (min <= entry && entry <= max) {
                return entry;
            } else {
                return -1;
            }
            // catch any non integer entry where applicable
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid entry for " + component.getName() + " . Please revise.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
        }
        return entry;
    }
    
    /**
     * Start the tournament
     *
     * @throws Exception invalid map exception
     */
    public void startTournament() throws Exception {
        /* For each game model in the set, start the game */
        GamePlayModel gameToPlay;
        String[][] resultLines = new String[tournamentSet.size()][enteredGames + 1];
        int r = 0;  // the result line number
        for (int n = 0; n < tournamentSet.size(); n++) {
            // collect the map name
            resultLines[r][0] = tournamentSet.get(n).getGameMap().getMapName();
            for (int i = 0; i < enteredGames; i++) {
                /* Play a copy of the game so we can replay from start if needed */
                gameToPlay = new GamePlayModel();
                gameToPlay.setGameMap(loadGameMap(strMapSet.get(n)));
                gameToPlay.setGameState(STARTUP);
                Player.resetStaticNextID();
                gameToPlay.initPlayers(enteredPlayers);
                for (int j = 0; j < enteredPlayers; j++) {  // set the player strategies
                    gameToPlay.getPlayers().get(j).setPlayerType(tempGamePlayModel.getPlayers().get(j).getPlayerType());
                }
                gameToPlay.initializeNewGameForTournament();
                gameToPlay.setMaxTurns(enteredMaxTurns);
                gameToPlay.startTheGame();
                while (gameToPlay.getTurnCounter() < enteredMaxTurns && gameToPlay.getGameState() != VICTORY) {
                    gameToPlay.letBotsPlay();
                }
                // collect the winner of the game
                resultLines[r][i + 1] = gameToPlay.getWinner() + " " + (gameToPlay.getTurnCounter()) + " turns";
            }
            r++;
        }
        /* Instantiate a result model */
        tournamentResultsModel = new TournamentResultsModel(enteredGames);
        tournamentResultsModel.setRows(resultLines);
    }
}
