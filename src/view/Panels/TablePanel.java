package view.Panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class TablePanel extends Panel {
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
