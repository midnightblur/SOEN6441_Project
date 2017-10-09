package model;

import model.game_entities.Continent;
import model.game_entities.GameMap;
import model.game_entities.Territory;
import utilities.BidiArrayComparator;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Observable;
import java.util.Vector;

/**
 * Model to hold the map data in order to displayJFrame it within a JTable
 */
public class MapTableModel extends Observable {
    private DefaultTableModel model = new DefaultTableModel();
    private String[][] rows;
    private Vector<String> columns = new Vector<>();
    
    /* Constructors */
    public MapTableModel() {
        model = new DefaultTableModel();
    }
    
    /**
     * Updating the table model and notifying the subscribers
     * This method is also used by the constructor
     *
     * @param gameMap the gameMap object that provides the data
     *
     * @return a table model to be used to generate the view
     */
    public DefaultTableModel updateMapTableModel(GameMap gameMap) {
        /* clears teh model data and reinitialize it with new values */
        model.setRowCount(0);
        columns.clear();
        columns.add("Continent");
        columns.add("Territory");
        columns.add("Neighbors");
        if (RiskGame.getInstance().getGameState().getValue() > 3) {
            columns.add("Owner");
            columns.add("Armies");
        }
    
        rows = new String[gameMap.getTerritoriesCount() + gameMap.getContinentsCount()][columns.size()];
        int i = 0;
        /* add continents */
        for (Continent c : gameMap.getContinents()) {
            rows[i][0] = c.getName();
            if (RiskGame.getInstance().getGameState().getValue() > 3) {
                rows[i][3] = c.getContinentOwner();
                rows[i][4] = Integer.toString(c.getContinentArmies());
            }
            i++;
        }
    
        for (Territory t : gameMap.getTerritories().values()) {
            rows[i][0] = t.getContinent().getName();
            rows[i][1] = t.getName();
            rows[i][2] = t.getNeighbors().toString().replace("[", "").replace("]", "");
            
            /* add countries and their information */
            if (RiskGame.getInstance().getGameState().getValue() > 3) {
                rows[i][3] = "Player " + Integer.toString(t.getOwner().getPlayerID());
                rows[i][4] = Integer.toString(t.getArmies());
            }
            
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