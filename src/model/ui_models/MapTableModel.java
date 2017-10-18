/* 
 * Risk Game Team 2
 * MapTableModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package model.ui_models;

import model.game_entities.Continent;
import model.game_entities.GameMap;
import model.game_entities.Territory;
import utilities.BidiArrayComparator;
import utilities.Config;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

/**
 * Model to hold the gameMap data in order to displayJFrame it within a JTable.
 */
public class MapTableModel {
    
    /** The model. */
    private DefaultTableModel model;
    
    /** The rows. */
    private String[][] rows;
    
    /** The columns. */
    private Vector<String> columns;
    
    /**
     * Instantiates a new map table model.
     */
    /* Constructors */
    public MapTableModel() {
        model = new DefaultTableModel();
        columns = new Vector<>();
    }
    
    /**
     * Updating the table model and notifying the subscribers
     * This method is also used by the constructor.
     *
     * @param gameMap the gameMap object that provides the data
     * @param gameStates the game states
     * @return a table model to be used to generate the view
     */
    public DefaultTableModel updateMapTableModel(GameMap gameMap, Config.GAME_STATES gameStates) {
        /* clears the model data and reinitialize it with new values */
        model.setRowCount(0);
        columns.clear();
        columns.add("Continent");
        columns.add("Territory");
        columns.add("Neighbors");
        if (gameStates.getValue() >= 3) {
            columns.add("Owner");
            columns.add("Armies");
        }
        
        rows = new String[gameMap.getTerritoriesCount() + gameMap.getContinentsCount()][columns.size()];
        int i = 0;
        /* add continents */
        for (Continent continent : gameMap.getContinents().values()) {
            rows[i][0] = continent.getName();
            if (gameStates.getValue() >= 3) {
                if (continent.getContinentOwner().compareTo("") == 0) {
                    rows[i][3] = "nobody owns it yet";
                } else {
                    rows[i][3] = continent.getContinentOwner();
                }
                rows[i][4] = Integer.toString(continent.getContinentArmies());
            }
            i++;
        }
        
        for (Territory territory : gameMap.getTerritories().values()) {
            rows[i][0] = territory.getContinent();
            rows[i][1] = territory.getName();
            rows[i][2] = territory.getNeighbors().toString().replace("[", "").replace("]", "");
            
            /* add countries and their information */
            if (gameStates.getValue() >= 3) {
                rows[i][3] = territory.getOwner().getPlayerName();
                rows[i][4] = Integer.toString(territory.getArmies());
            }
            
            i++;
        }
        this.model.setColumnIdentifiers(columns);
        Arrays.sort(rows, new BidiArrayComparator(0));        // perform sort on Continents column
        groupRows();                                                  // 'group' the rows
        return model;
    }
    
    /* Getters & setters */
    
    /**
     * Gets the model.
     *
     * @return the model
     */
    public DefaultTableModel getModel() {
        return model;
    }
    
    /* Public methods */
    
    /**
     * If rows have same continent as previous row, clear the continent name.
     */
    private void groupRows() {
        String prevGroup = "";
        for (String[] row : rows) {
            if (row[0].equals(prevGroup)) {
                row[0] = "  ";
            } else {
                // add a row and shift down the rest
                prevGroup = row[0];
            }
            this.model.addRow(row);
        }
    }
}