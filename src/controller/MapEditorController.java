package controller;

import model.GameMap;
import model.MapTableModel;
import view.MapEditor;

import javax.swing.table.DefaultTableModel;
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
            System.out.println("myMap.getTerritoriesCount() = " + loadGameMap(theView.getPath()).getTerritoriesCount());
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
            String path = theView.getPath();
            GameMap myMap = loadGameMap(path);
            System.out.println("UPDATED MAP HAS NOW " + myMap.getTerritoriesCount());
            
            // make a MapTableModel model object using the path
            theModel = new MapTableModel(myMap);
            
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            theView.displayErrorMessage("Invalid path...");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
        
        
 /*
    public MapEditorController() throws Exception {
        // get the path from the view
        String path = "Maps/World.map";
        // load the map
        GameMap myMap = loadGameMap(path);
        
        // display or refresh the view
        this.display();
        
        
        DefaultTableModel tableModel = new MapTableModel(myMap).getModel();
        JTable myTable = new JTable();
        // adding the view as listener to the model
        myTable.setModel(tableModel);
    
        this.setLayout(new GridLayout());
    
        myTable.setFont(new Font("Verdana", Font.PLAIN, 20));
        myTable.setAutoCreateRowSorter(true);
        myTable.setRowHeight(24);
        myTable.setBackground(Color.orange);
        myTable.setForeground(Color.blue);
        this.add(new JScrollPane(myTable));
    }
    
    
    */
    
}
