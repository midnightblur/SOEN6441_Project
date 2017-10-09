package controller;

import model.DropDownModel;
import model.MapEditorModel;
import model.helpers.GameMapHandler;
import utilities.Config;
import view.helpers.SaveDialog;
import view.screens.MapEditorFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.File;

public class MapEditorController {
    private MapEditorFrame mapEditorFrame;
    private MapEditorModel mapEditorModel;
    private MainGameController callerController;
    private String mapName;
    
    /* Constructors */
    public MapEditorController(MainGameController mainGameController) {
        this.callerController = mainGameController;
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
    
        /* Display list of maps to load to edit */
        DropDownModel mapDropdownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        this.mapEditorFrame.getEditMapControlPanel().getChooseMapDropdown().setModel(mapDropdownModel);
        
        /* Register Observer to Observable */
        this.mapEditorModel.getMapTableModel().addObserver(this.mapEditorFrame.getEditMapTable());
        
        /* Register to be ActionListeners */
        this.mapEditorFrame.getEditMapControlPanel().addLoadMapButtonListener(e -> loadMap());
        this.mapEditorFrame.getEditMapControlPanel().addBackButtonListener(e -> backToMainMenu());
        this.mapEditorFrame.getEditMapControlPanel().addNewMapButtonListener(e -> initiateNewGameMap());
        this.mapEditorFrame.getEditMapControlPanel().getContinentEditPanel().addContinentsListDropdownListener(e -> prepareContinentEditArea());
        this.mapEditorFrame.getEditMapControlPanel().getContinentEditPanel().addSaveButtonListener(e -> saveContientInfo());
        this.mapEditorFrame.getEditMapControlPanel().addSaveMapButtonListener(e -> saveMap());
    }
    
    /* Private methods */
    /**
     * Update the GameMap object from the selected items from DropdownList
     */
    private void loadMap() {
        try {
            mapName = String.valueOf(mapEditorFrame.getEditMapControlPanel().getChooseMapDropdown().getSelectedItem());
            mapEditorModel.loadNewGameMap(mapName);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            mapEditorFrame.displayErrorMessage(e.getMessage());
        }
    }
    
    /**
     * Close the current MapEditor screen and navigate back to the MainMenu screen
     */
    private void backToMainMenu() {
        callerController.invokeFrame();
        mapEditorFrame.dispatchEvent(new WindowEvent(mapEditorFrame, WindowEvent.WINDOW_CLOSING));
    }
    
    /**
     * Create a new GameMap object for users to make their own map from scratch
     */
    private void initiateNewGameMap() {
        // TODO: implement method
    }
    
    /**
     *  Prepare the content for Continent Editing area
     */
    private void prepareContinentEditArea() {
    
    }
    
    /**
     *  Get information from Continent Editing area and save to GameMap object
     */
    private void saveContientInfo() {
    
    }
    
    private void saveMap() {
        SaveDialog fileChooser = new SaveDialog();
        int selection = fileChooser.showSaveDialog(fileChooser.getParent());
        if (selection == JFileChooser.APPROVE_OPTION) {
            File mapFileToSave = fileChooser.getSelectedFile();
            try {
                GameMapHandler.writeToFile(this.mapEditorModel.getGameMap(), mapFileToSave.getAbsolutePath());
                mapEditorFrame.displayErrorMessage("The map file was saved at \n" + mapFileToSave.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                mapEditorFrame.displayErrorMessage(e.toString());
            }
        }
    }
}