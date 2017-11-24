/* 
 * Risk Game Team 2
 * TournamentController.java
 * Version 3.0
 * Nov 22, 2017
 */
package game_play.controller;

import game_play.model.DropDownModel;
import game_play.view.screens.TournamentFrame;
import shared_resources.game_entities.GameMap;
import shared_resources.helper.UIHelper;
import shared_resources.utilities.Config;

import java.util.Vector;

import static shared_resources.helper.GameMapHelper.getMapsInFolder;
import static shared_resources.helper.GameMapHelper.loadGameMap;

/**
 * TODO
 *
 * @author Team 2
 * @version 3.0
 */
public class TournamentController {
    
    // region Attributes declaration
    private TournamentFrame tournamentFrame;
    private MainMenuController callerController;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new map selector controller.
     *
     * @param callerController the caller controller
     */
    public TournamentController(MainMenuController callerController) {
        this.callerController = callerController;
        tournamentFrame = new TournamentFrame();
        tournamentFrame.addPlayGameButtonListener(e -> loadMapIntoGame());
        tournamentFrame.addBackButtonListener(e -> backToMainMenu());
        
        /* update map list and populate dropdown */
        tournamentFrame.getMapList().setModel(updateListOfMaps());
    }
    // endregion
    
    // region Methods to handle events from UI
    
    /**
     * Validate the selected map, launch the game if it is valid, show a message if it is not.
     */
    private void loadMapIntoGame() {
        /* load the selected map and make a new game*/
        try {
            for (String selectedMap : tournamentFrame.getMapList().getSelectedValuesList()) {
                GameMap gameMap = loadGameMap(selectedMap);
                tournamentFrame.setVisible(false);
                tournamentFrame.setEnabled(false);
                new GamePlayController(callerController, gameMap);
                //TODO....
            }
            
        } catch (Exception e) {
            UIHelper.displayMessage(tournamentFrame, e.getMessage());
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
