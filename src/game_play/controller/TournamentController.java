/* 
 * Risk Game Team 2
 * TournamentController.java
 * Version 3.0
 * Nov 22, 2017
 */
package game_play.controller;

import game_play.model.DropDownModel;
import game_play.model.GamePlayModel;
import game_play.view.screens.StrategyDialog;
import game_play.view.screens.TournamentFrame;
import shared_resources.game_entities.GameMap;
import shared_resources.helper.UIHelper;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static shared_resources.helper.GameMapHelper.getMapsInFolder;
import static shared_resources.helper.GameMapHelper.loadGameMap;

/**
 * TODO
 *
 * @author Team 2
 * @version 3.0
 */
public class TournamentController extends GamePlayController {
    
    int enteredMaps;
    int enteredPlayers;
    int enteredGames;
    int enteredMaxTurns;
    private TournamentFrame tournamentFrame;
    private MainMenuController callerController;
    private Vector<GamePlayModel> tournamentSet;
    
    /**
     * Instantiates a new map selector controller.
     *
     * @param callerController the caller controller
     */
    public TournamentController(MainMenuController callerController) {
        super(callerController);
        this.callerController = callerController;
        tournamentFrame = new TournamentFrame();
        tournamentSet = new Vector<>();
        tournamentFrame.addPlayTournamentButtonListener(e -> startTournament());
        tournamentFrame.addBackButtonListener(e -> backToMainMenu());
        
        /* update map list and populate dropdown */
        tournamentFrame.getMapList().setModel(updateListOfMaps());
    }
    // endregion
    
    // region Constructors
    
    /**
     * Validate the selected map, launch the game if it is valid, show a message if it is not.
     */
    private void startTournament() {
        int maxPlayers; // will be set to the minimum allowed players based on the maps in the set of games
        enteredMaps = validateEntry(tournamentFrame.getMapList(), 1, 5);
        enteredGames = validateEntry(tournamentFrame.getGameCount(), 1, 5);
        enteredMaxTurns = validateEntry(tournamentFrame.getMaxTurns(), 10, 50);
        
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
                GameMap gameMap = loadGameMap(selectedMap);         // load the selected map
                GamePlayModel gamePlayModel = new GamePlayModel();  // make a new game model
                gamePlayModel.setGameMap(gameMap);                  // set the map
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
        
        /* Now we read the number of players */
        enteredPlayers = validateEntry(tournamentFrame.getPlayers(), 2, maxPlayers);
        if (maxPlayers > -1) {
            
            /* initialize each game */
            for (GamePlayModel gamePlayModel : tournamentSet) {
                gamePlayModel.initializeNewGame(maxPlayers);
            }
            
            /* Pop-up the strategy dialog and add the submit listener, who will set the strategy */
            showStrategyOptions();
        }
    
    /* For each game model in the set, start the game */
        for (int i = 0; i < enteredGames; i++) {
            //TODO....
            do {
            /* Play the game */
                enteredMaxTurns--;
            } while (enteredMaxTurns > 0);
        }
        
    }
    // endregion
    
    // region Methods to handle events from UI
    
    /**
     * Close MapSelectorFrame, invoke MainMenuFrame.
     */
    private void backToMainMenu() {
        tournamentFrame.dispose();
        UIHelper.invokeFrame(callerController.getMainMenuFrame());
    }
    
    /**
     * Show the list of map files to the JComboBox.
     *
     * @return the drop down game_entities
     */
    private DropDownModel updateListOfMaps() {
        Vector<String> mapList = new Vector<>(getMapsInFolder(Config.MAPS_FOLDER));
        return new DropDownModel(mapList);
    }
    
    private int validateEntry(Component component, int min, int max) {
        int entry = -1;
        if (component instanceof JTextField) {
            entry = Integer.parseInt(((JTextField) component).getText());
        } else if (component instanceof JList) {
            entry = ((JList) component).getSelectedValuesList().size();
        }
        try {
            if (min <= entry && entry <= max) {
                return entry;
            } else {
                UIHelper.displayMessage(tournamentFrame, "Your entry for " + component.getName() + " must between " + min + " and " + max);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid entry for " + component.getName() + " . Please revise.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
        }
        return entry;
    }
    
    // region Attributes declaration
    
    // endregion
    
    /**
     * Setting the player's strategy
     */
    private void showStrategyOptions() {
        StrategyDialog strategyDialog = new StrategyDialog(this, tournamentFrame, tournamentSet.firstElement().getPlayers());
        strategyDialog.addSubmitButtonListener(e -> setStrategy(strategyDialog));
    }
    
    /**
     * Sets the player strategy as selected in StrategyDialog
     *
     * @param strategyDialog the strategy options UI
     */
    @Override
    public void setStrategy(StrategyDialog strategyDialog) {
        StrategyDialog.BehaviourOptions[] opts = strategyDialog.getPlayersOptions();
        strategyDialog.dispose();
        for (GamePlayModel gamePlayModel : tournamentSet) {
            gamePlayModel.setPlayersType(opts);
        }
    }
    
    /**
     * Gets tournamentSet.
     *
     * @return Value of tournamentSet.
     */
    public Vector<GamePlayModel> getTournamentSet() {
        return tournamentSet;
    }
}
