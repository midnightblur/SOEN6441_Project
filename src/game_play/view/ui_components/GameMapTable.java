/* 
 * Risk Game Team 2
 * GameMapTable.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.ui_components;

import map_editor.model.MapEditorModel;
import shared_resources.utilities.Config;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * GameMapTable is responsible for displaying the GameMap object in a table form
 * This class display information inputted from a MapTableModel
 *
 * @author Team 2
 * @version 1.0
 */
public class GameMapTable extends JTable implements Observer {
    
    // region Constructors
    
    /**
     * Instantiates a new game map table.
     */
    public GameMapTable() {
        this.setPreferredScrollableViewportSize(this.getPreferredSize());
        this.setFillsViewportHeight(true);
        this.setRowHeight(20);
    }
    // endregion
    
    // region Public methods
    
    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapEditorModel) {
            setModel(((MapEditorModel) o).getMapTableModel().getModel());
        }
        resizeColumns();
    }
    
    /**
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    /**
     * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        getColumn("Continent").setPreferredWidth(5);
        getColumn("Territory").setPreferredWidth(10);
        getColumn("Neighbors").setPreferredWidth(500);
        Component c = super.prepareRenderer(renderer, row, col);
        String continent = (String) getValueAt(row, 0);
        if (!"  ".equals(continent)) {
            c.setFont(new Font("Sans Serif", Font.ITALIC, 16));
            c.setBackground(Color.ORANGE.brighter());
            c.setForeground(Color.BLACK);
        } else {
            c.setBackground(super.getBackground());
            c.setForeground(super.getForeground());
        }
        if (getColumnCount() > 3) {    // meaning we already have information for owner on 3rd column
            String owner = (String) getValueAt(row, 3);
            for (int i = 0; i < Config.PLAYER_COLOR.length; i++) {
                if (("Player " + Integer.toString(i + 1)).compareTo(owner) == 0) {
                    c.setFont(new Font("Sans Serif", Font.ITALIC, 16));
                    c.setForeground(Config.PLAYER_COLOR[i]);
                }
            }
            getColumn("Owner").setPreferredWidth(10);
            getColumn("Armies").setPreferredWidth(5);
        }
        if (col == 2) {   // keep the neighbors black
            c.setForeground(Color.BLACK);
        }
        
        // do not allow reordering of columns
        getTableHeader().setReorderingAllowed(false);
        
        return c;
    }
    // endregion
    
    // region Private methods
    /**
     * Resize the table columns to fit with the data.
     */
    private void resizeColumns() {
        final TableColumnModel columnModel = getColumnModel();
        for (int c = 0; c < getColumnCount(); c++) {
            int width = 5; // minimum width
            for (int r = 0; r < getRowCount(); r++) {
                TableCellRenderer renderer = getCellRenderer(r, c);
                Component comp = prepareRenderer(renderer, r, c);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 500) {    // cap the width
                width = 500;
            }
            columnModel.getColumn(c).setPreferredWidth(width);
        }
    }
    // endregion
}
