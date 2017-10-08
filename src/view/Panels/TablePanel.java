package view.Panels;

import model.MapEditorModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public abstract class TablePanel extends Panel implements Observer {
    protected JTable table;
    
    /**
     * Sets the table model (data source)
     *
     * @param tableModel
     */
    public void updateTable(DefaultTableModel tableModel) {
        table.setModel(tableModel);
        resizeColumns();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        updateTable(((MapEditorModel) o).getMapTableModel().getModel());
    }
    
    /**
     * Resize the table columns to fit
     *
     */
    private void resizeColumns() {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int c = 0; c < table.getColumnCount(); c++) {
            int width = 10; // minimum width
            for (int r = 0; r < table.getRowCount(); r++) {
                TableCellRenderer renderer = table.getCellRenderer(r, c);
                Component comp = table.prepareRenderer(renderer, r, c);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 400)    // cap the width
                width = 400;
            columnModel.getColumn(c).setPreferredWidth(width);
        }
    }
}
