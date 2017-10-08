package controller;

import model.DropDownModel;
import model.GameMapHandler;
import model.MapTableModel;
import model.RiskGame;
import util.Config;
import view.MapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.GameMapHandler.loadGameMap;


/**
 * Controller class holding methods used in Map Editor module of the game
 */

public class MapEditorController implements ActionListener {
    
    private MapEditor theView;
    private MapTableModel theMapTableModel;
    private DropDownModel mapDropDownModel;
    
    public MapEditorController() {
        
        //create the View object
        theView = new MapEditor();
        
        //create the Model object
        try {
            //theMapTableModel = new MapTableModel(loadGameMap(Config.DEFAULT_MAP));
            theMapTableModel = new MapTableModel(RiskGame.getInstance().getGameMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // set the table model for the view
        theView.setTableModel(theMapTableModel.getModel());
        
        // Subscribe the view as observer to model changes
        theMapTableModel.addObserver(theView);
        
        // register this instance of controller as listener to the view
        theView.addActionListener(this);
        
        /* Get the available maps and populate the dropdown */
        mapDropDownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        theView.setDropdownModel(mapDropDownModel);
        mapDropDownModel.addListDataListener(theView.mapsDropdown);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // update the model when button is clicked
            theMapTableModel.updateMapTableModel(loadGameMap(theView.getMap()));
            
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            theView.displayErrorMessage("Invalid path...");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
