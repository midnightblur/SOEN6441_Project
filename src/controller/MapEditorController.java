package controller;

import model.MapTableModel;
import view.MapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.GameMapHandler.loadGameMap;


/**
 * Controller class holding methods used in Map Editor module of the game
 */

public class MapEditorController implements ActionListener {
    
    private MapEditor theView;
    private MapTableModel theModel;
    
    public MapEditorController() {
        
        //create the View object
        theView = new MapEditor();
        
        //create the Model object
        try {
            theModel = new MapTableModel(loadGameMap(theView.getPath()));
            theView.setModel(theModel.getModel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Subscribe the view as observer to model changes
        theModel.addObserver(theView);
        
        // register an instance of the event handler class as a listener
        theView.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // update the model when button is clicked
            theModel.updateMapTableModel(loadGameMap(theView.getPath()));
            
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            theView.displayErrorMessage("Invalid path...");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
