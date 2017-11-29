/* 
 * Risk Game Team 2
 * TournamentController.java
 * Version 3.0
 * Nov 22, 2017
 */
package game_play.controller;

import game_play.model.DropDownModel;
import game_play.model.GamePlayModel;
import game_play.model.TournamentResultsModel;
import game_play.view.screens.*;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Player;
import shared_resources.helper.GameMapHelper;
import shared_resources.helper.UIHelper;
import shared_resources.strategy.CheaterBot;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static shared_resources.helper.GameMapHelper.getMapsInFolder;
import static shared_resources.helper.GameMapHelper.loadGameMap;
import static shared_resources.utilities.Config.GAME_STATES.STARTUP;
import static shared_resources.utilities.Config.GAME_STATES.VICTORY;


/**
 * The controller for tournament mode
 *
 * <ul>
 * <li> Load valid maps from folder
 * <li> Validate user entries against predefined bounds
 * <li> Make a vector of games to play
 * <li> Determine the supported number of players against all maps to be played
 * <li> Play each game from the vector of games and collect the result
 * <li> Pop-up results for tournament
 * </ul>
 *
 * @author Team 2
 * @version 3.0
 */
public class TournamentController {
    private static LoggingFrame log = LoggingFrame.getInstance();
    // region Attribute declaration
    private TournamentFrame tournamentFrame;
    private MainMenuController callerController;
    private GamePlayFrame gamePlayFrame;
    private Vector<GamePlayModel> tournamentSet;
    private StrategyDialog strategyDialog;
    private ResultsFrame resultsFrame;
    private TournamentResultsModel tournamentResultsModel;
    private GamePlayModel tempGamePlayModel;
    private Vector<String> strMapSet;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new map selector controller.
     *
     * @param callerController the caller controller
     */
    public TournamentController(MainMenuController callerController) {
        this.callerController = callerController;
        gamePlayFrame = new GamePlayFrame(callerController);
        gamePlayFrame.setVisible(false);
        tournamentFrame = new TournamentFrame();
        tournamentSet = new Vector<>();
        tournamentFrame.addPlayTournamentButtonListener(e -> startTournament());
        tournamentFrame.addBackButtonListener(e -> backToMainMenu());
        strMapSet = new Vector<>();
        
        /* update map list and populate dropdown */
        tournamentFrame.getMapList().setModel(validMapsFromFolder());
        
        strategyDialog = new StrategyDialog(tournamentFrame);
        strategyDialog.addSubmitButtonListener(e -> setStrategy());
    }
    
    /**
     * Validate the selected map, launch the game if it is valid, show a message if it is not.
     */
    private void startTournament() {
        int maxPlayers; // will be set to the minimum allowed players based on the maps in the set of games
        /* Validate UI entries */
        int enteredMaps = validateEntry(tournamentFrame.getMapList(), 1, 5);
        int enteredGames = validateEntry(tournamentFrame.getGameCount(), 1, 5);
        int enteredMaxTurns = validateEntry(tournamentFrame.getMaxTurns(), 10, 50);
        
        /* If one of these entries is invalid return */
        if (enteredMaps == -1 || enteredGames == -1 || enteredMaxTurns == -1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please validate your entries.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        /* Load the maps first (we will determine the allowed number of players based on maps) */
        for (String selectedMap : tournamentFrame.getMapList().getSelectedValuesList()) {
            try {
                strMapSet.add(selectedMap);                         // add the string game map to the strMapSet
                GameMap gameMap = loadGameMap(selectedMap);         // load the selected map
                GamePlayModel gamePlayModel = new GamePlayModel();  // make a new game model that copies the temp game model
                gamePlayModel.setGameMap(gameMap);                  // set the game map
                tournamentSet.add(gamePlayModel);                   // add the game model to the tournament set
            } catch (Exception e) {
                UIHelper.displayMessage(tournamentFrame, e.getMessage());
                return;
            }
        }

        /* determine max players across all games' maps */
        maxPlayers = tournamentSet.firstElement().getGameMap().getMaxPlayers();
        for (GamePlayModel gamePlayModel : tournamentSet) {
            maxPlayers = Math.min(maxPlayers, gamePlayModel.getGameMap().getMaxPlayers());
        }
        
        /* Now validate the number of players against the maxPlayers across the games' maps*/
        int enteredPlayers = validateEntry(tournamentFrame.getPlayers(), 2, maxPlayers);
        if (enteredPlayers > -1) {
            /* initialize each game */
            // use a tempTournamentSet to set up the game to determine the player strategies
            tempGamePlayModel = new GamePlayModel();
            try {
                tempGamePlayModel.setGameMap(loadGameMap(strMapSet.firstElement()));
            } catch (Exception e) {
                UIHelper.displayMessage(tournamentFrame, e.getMessage());
                return;
            }
            tempGamePlayModel.setGameState(STARTUP);
            Player.resetStaticNextID();
            tempGamePlayModel.initPlayers(enteredPlayers);

            /* Pop-up the strategy dialog that will set the strategy for each game */
            tournamentFrame.dispose();
            showStrategyOptions(tempGamePlayModel.getPlayers());  // use tempTournamentSet to determine the strategies
        }
        
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
                try {
                    gameToPlay.setGameMap(loadGameMap(strMapSet.get(n)));
                } catch (Exception e) {
                    UIHelper.displayMessage(tournamentFrame, e.getMessage());
                    return;
                }
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
        
        /* Pop-up a Result Frame */
        resultsFrame = new ResultsFrame();
        tournamentResultsModel.addObserver(resultsFrame);
        resultsFrame.addOKButtonListener(e -> backToMainMenu());
        
        tournamentResultsModel.setRows(resultLines);
    }
    
    
    // endregion
    
    // region Starting the tournament
    
    /**
     * Close MapSelectorFrame, invoke MainMenuFrame.
     */
    private void backToMainMenu() {
        LoggingFrame.getInstance().getLogArea().setText("");
        tournamentFrame.dispose();
        if (resultsFrame != null) resultsFrame.dispose();
        UIHelper.invokeFrame(callerController.getMainMenuFrame());
    }
    // endregion
    
    // region Methods to handle events from UI
    
    /**
     * Show the list of map files
     *
     * @return the model for the map list
     */
    private DropDownModel validMapsFromFolder() {
        Vector<String> mapsInFolder = new Vector<>(getMapsInFolder(Config.MAPS_FOLDER));
        Vector<String> validMaps = new Vector<>();
        /* Check to see if the maps are invalid and remove them from the list */
        for (String mapFile : mapsInFolder) {
            try {
                GameMapHelper.validateMap(GameMapHelper.loadGameMap(mapFile));
                validMaps.add(mapFile);
            } catch (Exception e) {
                log.append("Map discarded: " + mapFile + " [" + e.getMessage() + "]");
            }
        }
        return new DropDownModel(validMaps);
    }
    
    /**
     * Sets the player strategy as selected in StrategyDialog
     */
    private void setStrategy() {
        StrategyDialog.BehaviourOptions[] opts = strategyDialog.getPlayersOptions();
        strategyDialog.dispose();
        tempGamePlayModel.setPlayersType(opts);
    }
    // endregion
    
    // region for Tournament helpers
    
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
                UIHelper.displayMessage(tournamentFrame, "Your entry for " + component.getName() + " must between " + min + " and " + max);
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
     * Setting the player's strategy
     *
     * @param players the players to populate the strategy dialog
     */
    private void showStrategyOptions(Vector<Player> players) {
        strategyDialog.populateOptions(players, true);
        strategyDialog.selectSpecificStrategy(CheaterBot.class.getSimpleName());
        strategyDialog.revalidate();
        strategyDialog.repaint();
        strategyDialog.setVisible(true);
    }
    
    /**
     * Quits the game
     */
    private void quit() {
        System.exit(0);
    }
    // endregion
}