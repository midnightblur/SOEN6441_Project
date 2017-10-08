package model;

import util.BidiArrayComparator;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Observable;

/**
 * Model to hold the map data in order to display it within a JTable
 */
public class MapTableModel extends Observable {
    private DefaultTableModel model = new DefaultTableModel();
    private String[] columns = { "Continent", "Territory", "Neighbors", "Owner", "Armies" };
    private String[][] rows;
    
    /* Constructors */
    public MapTableModel() {}
    
    public MapTableModel(GameMap map) {
        this.updateMapTableModel(map);
    }
    
    /**
     * Updating the table model and notifying the subscribers
     * This method is also used by the constructor
     *
     * @param map
     *
     * @return a table model to be used to generate the view
     */
    public DefaultTableModel updateMapTableModel(GameMap map) {
        model.setRowCount(0);   // clears the model data
        rows = new String[map.getTerritoriesCount() + map.getContinentsCount()][columns.length];
        int i = 0;
        /* add continents */
        for (Continent c : map.getContinents()) {
            rows[i][0] = c.getName();
            rows[i][3] = (RiskGame.getInstance().getGameState().getValue() > 3) ? c.getContinentOwner() : "";
            rows[i][4] = (RiskGame.getInstance().getGameState().getValue() > 3) ? Integer.toString(c.getContinentArmies()) : "0";
            i++;
        }
        /* add countries and their information */
        for (Territory t : map.getTerritories().values()) {
            rows[i][0] = t.getContinent().getName();
            rows[i][1] = t.getName();
            rows[i][2] = t.getNeighbors().toString().replace("[", "").replace("]", "");
            if (t.getOwner() != null) {
                rows[i][3] = "Player " + Integer.toString(t.getOwner().getPlayerID());
            }
            rows[i][4] = Integer.toString(t.getArmies());
            i++;
        }
        
        this.model.setColumnIdentifiers(columns);
        Arrays.sort(rows, new BidiArrayComparator(0));        // perform sort on Continents column
        groupRows();                                                  // 'group' the rows
        
        /* specify that model state changed and notify observers */
        setChanged();
        notifyObservers();
        
        return model;
    }
    
    /* Getters & setters */
    public DefaultTableModel getModel() {
        return model;
    }
    
    /* Public methods */
    
    /**
     * If rows have same continent as previous row, clear the continent name
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