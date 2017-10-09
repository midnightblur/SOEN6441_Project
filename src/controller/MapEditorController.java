package controller;

import model.DropDownModel;
import model.helpers.GameMapHandler;
import model.MapEditorModel;
import utilities.Config;
import view.screens.MapEditorFrame;

public class MapEditorController {
    private MapEditorFrame mapEditorFrame;
    private MapEditorModel mapEditorModel;
    
    /* Constructors */
    public MapEditorController() {
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
    
        /* Display list of maps to load to edit */
        DropDownModel dropDownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        this.mapEditorFrame.loadMapsList(dropDownModel);
        
        /* Register Observer to Observable */
        this.mapEditorModel.getMapTableModel().addObserver(this.mapEditorFrame.getEditMapTable());
        
        /* Register to be ActionListeners */
        this.mapEditorFrame.getEditMapControlPanel().addLoadMapButtonListener(e -> loadMap());
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
}