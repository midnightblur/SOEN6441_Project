package model.ui_models;

import model.RiskGame;
import model.game_entities.GameMap;
import model.game_entities.Player;
import model.game_entities.Territory;

import javax.swing.table.DefaultTableModel;
import java.util.Observable;
import java.util.Vector;

/**
 * Model to hold the gameMap data in order to displayJFrame it within a JTable
 */
public class PlayerTerritoriesModel extends Observable {
    private DefaultTableModel model;
    private String[][] rows;
    private Vector<String> columns;
    
    /* Constructors */
    public PlayerTerritoriesModel(Player player) {
        model = new DefaultTableModel();
        columns = new Vector<>();
        updateMapTableModel(player);
    }
    
    /**
     * Updating the table model and notifying the subscribers
     *
     * @param player the player object for which we collect the owned territories
     *
     * @return a table model to be used to generate the view
     */
    public DefaultTableModel updateMapTableModel(Player player) {
        /* clears the model data and reinitialize it with new values */
        model.setRowCount(0);
        columns.clear();
        columns.add("Territory");
        columns.add("Armies to place");
        GameMap gameMap = RiskGame.getInstance().getGameMap();
        rows = new String[gameMap.getTerritoriesOfPlayer(player).size()][columns.size()];
        int i = 0;
        for (Territory territory : gameMap.getTerritoriesOfPlayer(player).values()) {
            rows[i][0] = territory.getName();
            rows[i][1] = "0";
            i++;
        }
        this.model.setColumnIdentifiers(columns);
        for (String[] row : rows) {
            this.model.addRow(row);
        }
        return model;
    }
    
    /* Getters & setters */
    
    public DefaultTableModel getModel() {
        return model;
    }
    
    /* Public methods */
    
    
}