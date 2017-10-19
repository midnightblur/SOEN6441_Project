/* 
 * Risk Game Team 2
 * PlayerTerritoriesModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.model;

import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Player;
import shared_resources.game_entities.Territory;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * This class holds the game_entities representing a player's specific territories.
 *
 * This game_entities is based on a DefaultTableModel, and
 * serves as the underlining game_entities for a JTable on Reinforcement phase
 */
public class PlayerTerritoriesModel {
    
    // region Attributes declaration
    /** The game_entities. */
    private DefaultTableModel model;
    
    /** The columns. */
    private Vector<String> columns;
    
    // endregion
    
    // region Constructors
    
    /**
     * Makes a new empty Player Territory game_entities
     * 
     * The game_entities would later be updated once players change.
     */
    public PlayerTerritoriesModel() {
        model = new DefaultTableModel();
        columns = new Vector<>();
    }
    
    // endregion
    
    // region Getters & setters
    
    /**
     * Accessor for the table game_entities.
     *
     * @return a default table game_entities holding territories information when player was indicated,
     * or an empty game_entities if no player was indicated
     */
    public DefaultTableModel getModel() {
        return model;
    }
    
    // endregion
    
    // region Public methods
    
    /**
     * Updating the table game_entities and notifying the subscribers.
     *
     * @param player the player object for which we collect the owned territories
     */
    public void updateMapTableModel(Player player) {
        /* clears the game_entities data and reinitialize it with new values */
        model.setRowCount(0);
        columns.clear();
        columns.add("Territory");
        columns.add("Armies to place");
        GameMap gameMap = GamePlayModel.getInstance().getGameMap();
        /* The rows. */
        Object[][] rows = new Object[gameMap.getTerritoriesOfPlayer(player).size()][columns.size()];
        
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
    }
    
    // endregion
    
}