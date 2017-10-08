package view.Panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Observer;

public abstract class TablePanel extends Panel implements Observer {
    protected JTable table;
    
    public JTable getTable() {
        return table;
    }
    
    /**
     * Sets the table model (data source)
     *
     * @param tableModel
     */
    public void updateTable(DefaultTableModel tableModel) {
        table.setModel(tableModel);
    }
}
