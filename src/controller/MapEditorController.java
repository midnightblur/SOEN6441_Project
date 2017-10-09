package controller;

import model.DropDownModel;
import model.MapEditorModel;
import model.helpers.GameMapHandler;
import utilities.Config;
import view.screens.MapEditorFrame;

import java.awt.event.WindowEvent;

public class MapEditorController {
    private MapEditorFrame mapEditorFrame;
    private MapEditorModel mapEditorModel;
    private MainGameController callerController;
    
    /* Constructors */
    public MapEditorController(MainGameController mainGameController) {
        this.callerController = mainGameController;
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
    
        /* Display list of maps to load to edit */
        DropDownModel mapDropdownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        this.mapEditorFrame.getEditMapControlPanel().getChooseMapDropdown().setModel(mapDropdownModel);

        /* Display list of options to add countries or continents */
        DropDownModel addDropdownModel = new DropDownModel(GameMapHandler.getEntities());
        this.mapEditorFrame.getEditMapControlPanel().getAddDropdown().setModel(addDropdownModel);
     
        /* Display list of maps to load to edit */
        DropDownModel editDropdownModel = new DropDownModel(GameMapHandler.getContinentsCountries());
        this.mapEditorFrame.getEditMapControlPanel().getEditDropdown().setModel(editDropdownModel);
        
        /* Register Observer to Observable */
        this.mapEditorModel.getMapTableModel().addObserver(this.mapEditorFrame.getEditMapTable());
        
        /* Register to be ActionListeners */
        this.mapEditorFrame.getEditMapControlPanel().addLoadMapButtonListener(e -> loadMap());
        this.mapEditorFrame.getEditMapControlPanel().addBackButtonListener(e -> backToMainMenu());
        this.mapEditorFrame.getEditMapControlPanel().addAddDropdownListener(e -> addContinentCountry());
        this.mapEditorFrame.getEditMapControlPanel().addEditDropdownListener(e -> editContinentCountry());
    }
    
    /* Private methods */
    
    /**
     * Display the map content to the table
     */
    private void loadMap() {
        try {
            String mapName = mapEditorFrame.getEditMapControlPanel().getChooseMapDropdown().getSelectedItem().toString();
            mapEditorModel.updateGameMap(mapName);
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
     * Show the add panel to add a country or a continent
     */
    private void addContinentCountry() {
        // TODO: implement method   
    }
    
    /**
     * show the edit panel to edit or delete a country or a continent
     */
    private void editContinentCountry() {
        // TODO: implement method   
    }
    
}