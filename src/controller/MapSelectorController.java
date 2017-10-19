/* 
 * Risk Game Team 2
 * MapSelectorController.java
 * Version 1.0
 * Oct 18, 2017
 */
package controller;

import model.game_entities.GameMap;
import model.ui_models.DropDownModel;
import utilities.Config;
import view.helpers.UIHelper;
import view.screens.MapSelectorFrame;

import java.util.Vector;

import static model.helpers.GameMapHelper.getMapsInFolder;
import static model.helpers.GameMapHelper.loadGameMap;

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
     * @return the drop down model
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
