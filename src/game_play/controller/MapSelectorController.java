/* 
 * Risk Game Team 2
 * MapSelectorController.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.controller;

import shared_resources.game_entities.GameMap;
import game_play.model.DropDownModel;
import shared_resources.utilities.Config;
import shared_resources.helper.UIHelper;
import game_play.view.screens.MapSelectorFrame;

import java.util.Vector;

import static shared_resources.helper.GameMapHelper.getMapsInFolder;
import static shared_resources.helper.GameMapHelper.loadGameMap;

/**
 * The Class MapSelectorController.
 *
 * @author Team 2
 * @version 1.0
 */
public class  MapSelectorController {
    
    // region Attributes declaration
    /** The map selector frame. */
    private MapSelectorFrame mapSelectorFrame;
    
    /** The caller controller. */
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
     * Update list of maps.
     *
     * @return the drop down game_entities
     */
    private DropDownModel updateListOfMaps() {
        Vector<String> mapList = new Vector<>(getMapsInFolder(Config.MAPS_FOLDER));
        return new DropDownModel(mapList);
    }
    
    /**
     * Load map into game.
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
     * Back to main menu.
     */
    private void backToMainMenu() {
        UIHelper.closeFrame(mapSelectorFrame);
        UIHelper.invokeFrame(callerController.getMainMenuFrame());
    }
    // endregion
}
