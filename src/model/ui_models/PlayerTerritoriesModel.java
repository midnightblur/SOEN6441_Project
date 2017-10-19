/* 
 * Risk Game Team 2
 * PlayerTerritoriesModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package model.ui_models;

import model.game_entities.GameMap;
import model.game_entities.Player;
import model.game_entities.Territory;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * This class holds the model representing a player's specific territories.
 *
 * This model is based on a DefaultTableModel, and
 * serves as the underlining model for a JTable on Reinforcement phase
 */
public class PlayerTerritoriesModel {
    
    // region Attributes declaration
    /** The model. */
    private DefaultTableModel model;
    
    /** The rows. */
    private Object[][] rows;
    
    /** The columns. */
    private Vector<String> columns;
    
    // endregion
    
    // region Constructors
    
    /**
     * Makes a new empty Player Territory model
     * 
     * The model would later be updated once players change.
     */
    public PlayerTerritoriesModel() {
        model = new DefaultTableModel();
        columns = new Vector<>();
    }
    
    /**
     * Makes a new Player Territory model for the player passed as argument.
     *
     * @param player The player for which the territory model is made
     */
    public PlayerTerritoriesModel(Player player) {
        model = new DefaultTableModel();
        columns = new Vector<>();
        model = updateMapTableModel(player);
    }
    
    // endregion
    
    // region Getters & setters
    
    /**
     * Accessor for the table model.
     *
     * @return a default table model holding territories information when player was indicated,
     * or an empty model if no player was indicated
     */
    public DefaultTableModel getModel() {
        return model;
    }
    
    // endregion
    
    // region Public methods
    
    /**
     * Updating the table model and notifying the subscribers.
     *
     * @param player the player object for which we collect the owned territories
     * @return a table model to be used to generate the view
     */
    public DefaultTableModel updateMapTableModel(Player player) {
        /* clears the model data and reinitialize it with new values */
        model.setRowCount(0);
        columns.clear();
        columns.add("Territory");
        columns.add("Armies to place");
        GameMap gameMap = GamePlayModel.getInstance().getGameMap();
        rows = new Object[gameMap.getTerritoriesOfPlayer(player).size()][columns.size()];
        
        int i = 0;
        for (Territory territory : gameMap.getTerritoriesOfPlayer(player).values()) {
            rows[i][0] = territory.getName();
            rows[i][1] = new Integer(0);
            i++;
        }
        this.model.setColumnIdentifiers(columns);
        for (Object[] row : rows) {
            this.model.addRow(row);
        }
        return model;
    }
    
    // endregion
    
}