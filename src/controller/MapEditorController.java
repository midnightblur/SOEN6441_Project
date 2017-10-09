package controller;

import model.DropDownModel;
import model.GameMapHandler;
import model.MapTableModel;
import model.RiskGame;
import util.Config;
import view.MapEditor;

import java.io.IOException;

import static model.GameMapHandler.loadGameMap;


/**
 * Controller class holding methods used in Map Editor module of the game
 */

public class MapEditorController {
    
    private MapEditor theView;
    private MapTableModel theMapTableModel;
    private DropDownModel mapDropDownModel;
    
    public MapEditorController() {
        
        // create the View object
        theView = new MapEditor();
        
        // create the Model object
        try {
            //theMapTableModel = new MapTableModel(loadGameMap(Config.DEFAULT_MAP));
            theMapTableModel = new MapTableModel(RiskGame.getInstance().getGameMap());
        } catch (Exception e) {
            theView.displayErrorMessage(e.toString());
        }
        
        /* set the table model for the view */
        theView.getMyTable().setModel(theMapTableModel.getModel());
        theView.resizeColumns(theView.getMyTable());
        
        // Subscribe the view as observer to model changes
        theMapTableModel.addObserver(theView);
        
        /* set the model for the dropdown */
        mapDropDownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        theView.mapsDropdown.setModel(mapDropDownModel);
        theView.mapsDropdown.setSelectedItem(RiskGame.getInstance().getGameMap().getMapName());
        
        // Subscribe the mapDropdown as observer to dropdown model changes
        mapDropDownModel.addListDataListener(theView.mapsDropdown);
        
        /* make the controller listen to changes in the view */
        
        // update the model when button to load map is clicked
        theView.getLoadMap().addActionListener(e -> {
            try {
                theMapTableModel.updateMapTableModel(loadGameMap(theView.getMap()));
            } catch (Exception e1) {
                theView.displayErrorMessage(e1.toString());
            }
        });
        
        // write the map to file once menu to save the map is selected
        theView.getSaveMap().addActionListener(e -> {
            try {
                GameMapHandler.writeToFile(RiskGame.getInstance().getGameMap());
            } catch (IOException e1) {
                theView.displayErrorMessage(e1.toString());
            }
        });
        
    }
    
}
