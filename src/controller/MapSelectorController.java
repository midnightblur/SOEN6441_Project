/**  
 * @file  MapSelectorController.java 
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
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
 * @author 
 * @version 
 */
public class  MapSelectorController {
    
    /** The map selector frame. */
    // region Attributes declaration
    private MapSelectorFrame mapSelectorFrame;
    
    /** The caller controller. */
    private MainMenuController callerController;
    // endregion
    
    /**
     * Instantiates a new map selector controller.
     *
     * @param callerController the caller controller
     */
    // region Construtors
    public MapSelectorController(MainMenuController callerController) {
        this.callerController = callerController;
        mapSelectorFrame = new MapSelectorFrame();
        mapSelectorFrame.addPlayGameButtonListener(e -> loadMapIntoGame());
        mapSelectorFrame.addBackButtonListener(e -> backToMainMenu());
        
        /* update map list and populate dropdown */
        mapSelectorFrame.getMapDropdown().setModel(updateListOfMaps());
    }
    // endregion
    
    /**
     * Update list of maps.
     *
     * @return the drop down model
     */
    // region Private methods
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
            e.printStackTrace();
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
