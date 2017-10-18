/**  
 * @file  GameMapTable.java 
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package view.ui_components;

import model.ui_models.MapEditorModel;
import utilities.Config;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is the model class for GameMapTable which
 * implements Observer class since it is using Observer Pattern.
 *
 * @author 
 * @version 1.0
 */
public class GameMapTable extends JTable implements Observer {
    
    /**
     * Instantiates a new game map table.
     */
    /* Constructors */
    public GameMapTable() {
        this.setPreferredScrollableViewportSize(this.getPreferredSize());
        this.setFillsViewportHeight(true);
    }

    /**
     * This is update function used by Observer Pattern.
     *
     * @param o This is Observer object type.
     * @param arg This is Object type parameter.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapEditorModel) {
            setModel(((MapEditorModel) o).getMapTableModel().getModel());
        }
        resizeColumns();
    }
    
    /* Private methods */
    
    /**
     * Resize the table columns to fit.
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
    
    /* (non-Javadoc)
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        setRowHeight(20);
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
    
}
