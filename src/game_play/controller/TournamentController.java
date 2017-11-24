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
    
    // region Attributes declaration
    private TournamentFrame tournamentFrame;
    private MainMenuController callerController;
    private Vector<GamePlayModel> tournamentSet;
    // endregion
    
    // region Constructors
    
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
    
    // region Methods to handle events from UI
    
    /**
     * Validate the selected map, launch the game if it is valid, show a message if it is not.
     */
    private void startTournament() {
        int maxPlayers; // will be set to the minimum allowed players based on the maps in the set of games
    /* Load the maps first to determine the allowed number of players*/
        try {
            for (String selectedMap : tournamentFrame.getMapList().getSelectedValuesList()) {
                GameMap gameMap = loadGameMap(selectedMap);         // load the selected map
                GamePlayModel gamePlayModel = new GamePlayModel();  // make a new game model
                gamePlayModel.setGameMap(gameMap);                  // set the map
                tournamentSet.add(gamePlayModel);                   // add the game model to the tournament set
            }
            
        } catch (Exception e) {
            UIHelper.displayMessage(tournamentFrame, e.getMessage());
        }
        
        /* determine max players across all games' maps */
        maxPlayers = tournamentSet.firstElement().getGameMap().getMaxPlayers();
        for (GamePlayModel gamePlayModel : tournamentSet) {
            maxPlayers = Math.min(maxPlayers, gamePlayModel.getGameMap().getMaxPlayers());
        }
        
        /* Now we read the number of games, players*/
        try {
            int enteredPlayers = Integer.parseInt(tournamentFrame.getGameCount().getText());
            if ((enteredPlayers >= 2) && (enteredPlayers <= maxPlayers)) {
                
                /* Pop-up the strategy dialog */
                StrategyDialog strategyDialog = new StrategyDialog(this, tournamentFrame, tournamentSet.firstElement().getPlayers());
        
               /* initialize each game and set the strategy */
                for (GamePlayModel gamePlayModel : tournamentSet) {
                    gamePlayModel.initializeNewGame(maxPlayers);
                    setStrategy(strategyDialog);
                }
                
                
            } else {
                UIHelper.displayMessage(tournamentFrame, "You must enter an amount of players between 1 and " + maxPlayers);
            }
        } catch (ClassCastException | NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid entry. Please re-enter a number.",
                    "Entry Error!", JOptionPane.ERROR_MESSAGE);
        }
    
    /* For each game model in the set, start the game */
        for (GamePlayModel gamePlayModel : tournamentSet) {
            new GamePlayController(callerController, gamePlayModel.getGameMap());
            
            
            //TODO....
        }
        
        
    }
    
    
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
    // endregion
}
