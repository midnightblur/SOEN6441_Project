/* 
 * Risk Game Team 2
 * MapSelectorController.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.controller;

import game_play.model.DropDownModel;
import game_play.view.screens.MapSelectorFrame;
import shared_resources.game_entities.GameMap;
import shared_resources.helper.UIHelper;
import shared_resources.utilities.Config;

import java.util.Vector;

import static shared_resources.helper.GameMapHelper.getMapsInFolder;
import static shared_resources.helper.GameMapHelper.loadGameMap;

/**
 * MapSelectorController helps functioning the MapSelectorFrame, allowing users to choose a map to play the game on.
 * Validate the map and launch the game or
 * Go back to main menu if user decides to go back.
 *
 * @author Team 2
 * @version 1.0
 */
public class MapSelectorController {
    
    // region Attributes declaration
    private MapSelectorFrame mapSelectorFrame;
    private MainMenuController callerController;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new map selector controller.
     *
     * @param callerController the caller controller
     */
    public MapSelectorController(MainMenuController callerController) {
        this.callerController = callerController;
        mapSelectorFrame = new MapSelectorFrame();
        mapSelectorFrame.addPlayGameButtonListener(e -> loadMapIntoGame());
        mapSelectorFrame.addBackButtonListener(e -> backToMainMenu());
        
        /* update map list and populate dropdown */
        mapSelectorFrame.getMapDropdown().setModel(updateListOfMaps());
    }
    // endregion
    
    // region Methods to handle events from UI
    
    /**
     * Validate the selected map, launch the game if it is valid, show a message if it is not.
     */
    private void loadMapIntoGame() {
        /* load the selected map and make a new game*/
        try {
            GameMap gameMap = loadGameMap(String.valueOf(mapSelectorFrame.getMapDropdown().getSelectedItem()));
            
            mapSelectorFrame.setVisible(false);
            mapSelectorFrame.setEnabled(false);
            new GamePlayController(callerController, gameMap);
        } catch (Exception e) {
            UIHelper.displayMessage(mapSelectorFrame, e.getMessage());
        }
    }
    
    /**
     * Close MapSelectorFrame, invoke MainMenuFrame
     */
    private void backToMainMenu() {
        mapSelectorFrame.dispose();
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
