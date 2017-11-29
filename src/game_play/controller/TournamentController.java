/* 
 * Risk Game Team 2
 * TournamentController.java
 * Version 3.0
 * Nov 22, 2017
 */
package game_play.controller;

import game_play.model.DropDownModel;
import game_play.model.GamePlayModel;
import game_play.model.TournamentModel;
import game_play.view.screens.LoggingFrame;
import game_play.view.screens.ResultsFrame;
import game_play.view.screens.StrategyDialog;
import game_play.view.screens.TournamentFrame;
import shared_resources.helper.GameMapHelper;
import shared_resources.helper.UIHelper;
import shared_resources.strategy.CheaterBot;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.util.Vector;

import static shared_resources.helper.GameMapHelper.getMapsInFolder;


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
    // region Attribute declaration
    private TournamentModel tournamentModel;
    private static LoggingFrame log = LoggingFrame.getInstance();
    private TournamentFrame tournamentFrame;
    private MainMenuController callerController;
    private StrategyDialog strategyDialog;
    private ResultsFrame resultsFrame;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new map selector controller.
     *
     * @param callerController the caller controller
     */
    public TournamentController(MainMenuController callerController) {
        this.callerController = callerController;
        tournamentModel = new TournamentModel();
        tournamentFrame = new TournamentFrame();
        
        tournamentFrame.addPlayTournamentButtonListener(e -> startTournament());
        tournamentFrame.addBackButtonListener(e -> backToMainMenu());
        
        /* update map list and populate dropdown */
        tournamentFrame.getMapList().setModel(validMapsFromFolder());
        
        strategyDialog = new StrategyDialog(tournamentFrame);
        strategyDialog.addSubmitButtonListener(e -> setStrategy(tournamentModel.getTempGamePlayModel()));
    }
    
    /**
     * Validate the selected map, launch the game if it is valid, show a message if it is not.
     */
    private void startTournament() {
        JList<String> mapsList = tournamentFrame.getMapList();
        JTextField gamesCountField = tournamentFrame.getGameCount();
        JTextField maxTurnField = tournamentFrame.getMaxTurns();
        JTextField playersCountField = tournamentFrame.getPlayers();
        
        String message = tournamentModel.setupTournament(mapsList, gamesCountField, maxTurnField, playersCountField);
        
        if (message.contains("successfully")) {
            /* Pop-up the strategy dialog that will set the strategy for each game */
            tournamentFrame.dispose();
            showStrategyOptions(tournamentModel.getTempGamePlayModel());  // use tempTournamentSet to determine the strategies
        } else {
            UIHelper.displayMessage(tournamentFrame, message);
        }
    }
    
    
    // endregion
    
    // region Starting the tournament
    
    /**
     * Close MapSelectorFrame, invoke MainMenuFrame.
     */
    private void backToMainMenu() {
        LoggingFrame.getInstance().getLogArea().setText("");
        tournamentFrame.dispose();
        if (resultsFrame != null) {
            resultsFrame.dispose();
        }
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
    private void setStrategy(GamePlayModel gamePlayModel) {
        StrategyDialog.BehaviourOptions[] opts = strategyDialog.getPlayersOptions();
        strategyDialog.dispose();
        gamePlayModel.setPlayersType(opts);
        try {
            tournamentModel.startTournament();
        } catch (Exception e) {
            UIHelper.displayMessage(tournamentFrame, e.getMessage());
        }
        
        /* Pop-up a Result Frame */
        resultsFrame = new ResultsFrame(tournamentModel.getTournamentResultsModel());
        resultsFrame.addOKButtonListener(e -> backToMainMenu());
    }
    // endregion
    
    // region for Tournament helpers
    
    /**
     * Setting the player's strategy
     *
     * @param gamePlayModel the game play model
     */
    private void showStrategyOptions(GamePlayModel gamePlayModel) {
        strategyDialog.populateOptions(gamePlayModel.getPlayers(), true);
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
