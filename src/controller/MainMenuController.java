package controller;

import model.game_entities.GameMap;
import model.ui_models.DropDownModel;
import utilities.Config;
import view.screens.MainMenuFrame;
import view.screens.MapSelectorFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Vector;

import static model.helpers.GameMapHelper.getMapsInFolder;
import static model.helpers.GameMapHelper.loadGameMap;

/**
 * The Main Class MainGameController  is
 * responsible for launching different game screens
 *
 * @author
 * @version 1.0
 */
public class MainMenuController extends JFrame {
    private MainMenuFrame mainMenuFrame;
    private DropDownModel mapDropdownModel;
    private MapSelectorFrame mapSelectorFrame;

    /**
     * This method is used to launch main screen of application
     */
    public MainMenuController() {
        mainMenuFrame = new MainMenuFrame();
        registerToBeListener();
    }


    public void invokeFrame() {
        mainMenuFrame.setVisible(true);
        mainMenuFrame.setEnabled(true);
    }
    
    private void registerToBeListener() {
        mainMenuFrame.addMapEditorButtonListener(e -> openMapEditorScreen());
        mainMenuFrame.addPlayGameButtonListener(e -> openPlayGameScreen());
        mainMenuFrame.addQuitButtonListener(e -> exitGame());
    }
    
    private void openMapEditorScreen() {
        mainMenuFrame.setVisible(false);
        mainMenuFrame.setEnabled(false);
        
        new MapEditorController(this);
    }
    
    private void openPlayGameScreen() {
        mainMenuFrame.setVisible(false);
        mainMenuFrame.setEnabled(false);
        
        mapSelectorFrame = new MapSelectorFrame();
                mapSelectorFrame.addPlayGameButtonListener(e -> loadMapIntoGame());
                        /* update map list and populate dropdown */
                                updateListOfMaps();
                mapSelectorFrame.getMapDropdown().setModel(mapDropdownModel);
                    }
        
            public void updateListOfMaps() {
                Vector<String> mapList = new Vector<>();
                mapList.addAll(getMapsInFolder(Config.MAPS_FOLDER));
                mapDropdownModel = new DropDownModel(mapList);
        
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
                                }}
    
    private void exitGame() {
        mainMenuFrame.dispatchEvent(new WindowEvent(mainMenuFrame, WindowEvent.WINDOW_CLOSING));
    }
}
