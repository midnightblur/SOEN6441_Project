package model;

import javax.swing.table.DefaultTableModel;
import java.util.Observable;

/**
 * Model to hold the map data in order to display it within a JTable
 */
public class MapTableModel extends Observable {
    private DefaultTableModel model = new DefaultTableModel();
    private String[] columns = { "Continent", "Territory", "Neighbors", "Owner", "Armies" };
    private String[][] rows;
    
    /* Constructors */
    public MapTableModel(GameMap map) {
        rows = new String[map.getTerritoriesCount()][columns.length];
        int i = 0;
        for (Territory t : map.getTerritories().values()) {
            rows[i][0] = t.getContinent().getName();
            rows[i][1] = t.getName();
            rows[i][2] = t.getNeighbors().toString();
            rows[i][3] = t.getOwner().getPlayerName();
            rows[i][4] = Integer.toString(t.getArmies());
            i++;
        }
        
        this.model.setColumnIdentifiers(columns);
        groupRows();
    }
    
    public DefaultTableModel updateMapTableModel(GameMap map) {
        model.setRowCount(0);   // clears the model data
        rows = new String[map.getTerritoriesCount()][columns.length];
        int i = 0;
        for (Territory t : map.getTerritories().values()) {
            rows[i][0] = t.getContinent().getName();
            rows[i][1] = t.getName();
            rows[i][2] = t.getNeighbors().toString();
            rows[i][3] = t.getOwner().getPlayerName();
            rows[i][4] = Integer.toString(t.getArmies());
            i++;
        }
        
        this.model.setColumnIdentifiers(columns);
        groupRows();
        
        // specify that model state changed
        setChanged();
        
        // notify observers
        notifyObservers();
        
        return model;
    }
    
    /* Getters & setters */
    public DefaultTableModel getModel() {
        return model;
    }
    
    /* Public methods */
    private void groupRows() {
        String prevGroup = "";
        for (String[] row : rows) {
            if (row[0].equals(prevGroup)) {
                row[0] = "   -> same as above";
            } else {
                prevGroup = row[0];
            }
            this.model.addRow(row);
        }
    }
    
    
}