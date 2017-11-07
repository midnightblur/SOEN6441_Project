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
 * PlayerTerritoriesModel is responsible for providing data for the table of distributing armies to territories
 *
 * @author Team 2
 * @version 1.0
 */
public class PlayerTerritoriesModel {
    // region Attributes declaration
    private DefaultTableModel model;
    private Vector<String> columns;
    // endregion
    
    // region Constructors
    
    /**
     * Makes a new shared_resources.empty PlayerTerritoryModel
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
     * Accessor for data game_play.
     *
     * @return a default table game_entities holding territories information when player was indicated,
     * or an shared_resources.empty game_entities if no player was indicated
     */
    public DefaultTableModel getModel() {
        return model;
    }
    
    // endregion
    
    // region Public methods
    
    /**
     * Updating the data game_play according to the player.
     *
     * @param player the player object for which we collect the owned territories
     */
    public void updateMapTableModel(Player player, GamePlayModel gamePlayModel) {
        /* clears the game_entities data and reinitialize it with new values */
        model.setRowCount(0);
        columns.clear();
        columns.add("Territory");
        columns.add("Armies to place");
        GameMap gameMap = gamePlayModel.getGameMap();
        /* The rows. */
        Object[][] rows = new Object[gameMap.getTerritoriesOfPlayer(player).size()][columns.size()];
        
        int i = 0;
        for (Territory territory : gameMap.getTerritoriesOfPlayer(player).values()) {
            rows[i][0] = territory.getName();
            rows[i][1] = 0;
            i++;
        }
        this.model.setColumnIdentifiers(columns);
        for (Object[] row : rows) {
            this.model.addRow(row);
        }
    }
    
    // endregion
    
}