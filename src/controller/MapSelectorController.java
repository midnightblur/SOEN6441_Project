package controller;

import model.game_entities.GameMap;
import model.ui_models.DropDownModel;
import utilities.Config;
import view.screens.MapSelectorFrame;

import java.awt.event.WindowEvent;
import java.util.Vector;

import static model.helpers.GameMapHelper.getMapsInFolder;
import static model.helpers.GameMapHelper.loadGameMap;

public class MapSelectorController {
    // region Attributes declaration
    private MapSelectorFrame mapSelectorFrame;
    private MainMenuController callerController;
    // endregion
    
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
    
    // region Private methods
    private DropDownModel updateListOfMaps() {
        Vector<String> mapList = new Vector<>(getMapsInFolder(Config.MAPS_FOLDER));
        return new DropDownModel(mapList);
    }
    
    private void loadMapIntoGame() {
        /* load the selected map and make a new game*/
        try {
            GameMap gameMap = loadGameMap(mapSelectorFrame.getMapDropdown().getSelectedItem().toString());
            
            mapSelectorFrame.setVisible(false);
            mapSelectorFrame.setEnabled(false);
            new GamePlayController(gameMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void backToMainMenu() {
        callerController.invokeFrame();
        mapSelectorFrame.dispatchEvent(new WindowEvent(mapSelectorFrame, WindowEvent.WINDOW_CLOSING));
    }
    // endregion
}
