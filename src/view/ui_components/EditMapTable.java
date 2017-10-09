package view.ui_components;

import model.ui_models.MapEditorModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class EditMapTable extends JTable implements Observer {
    /* Constructors */
    public EditMapTable() {
        this.setPreferredScrollableViewportSize(this.getPreferredSize());
        this.setFillsViewportHeight(true);
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        setModel(((MapEditorModel) o).getMapTableModel().getModel());
        resizeColumns();
    }
    
    /* Private methods */
    /**
     * Resize the table columns to fit
     *
     */
    private void resizeColumns() {
        final TableColumnModel columnModel = getColumnModel();
        for (int c = 0; c < getColumnCount(); c++) {
            int width = 10; // minimum width
            for (int r = 0; r < getRowCount(); r++) {
                TableCellRenderer renderer = getCellRenderer(r, c);
                Component comp = prepareRenderer(renderer, r, c);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 400)    // cap the width
                width = 400;
            columnModel.getColumn(c).setPreferredWidth(width);
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component c = super.prepareRenderer(renderer, row, col);
        String continent = (String) getValueAt(row, 0);
        if (!"  ".equals(continent)) {
            c.setFont(new Font("Sans Serif", Font.ITALIC, 14));
            c.setBackground(Color.BLACK);
            c.setForeground(Color.WHITE);
        } else {
            c.setBackground(super.getBackground());
            c.setForeground(super.getForeground());
        }
        if (this.getColumnCount() > 3) {    // meaning we already have information for owner on 3rd column
            String owner = (String) getValueAt(row, 3);
            Color[] colors = { Color.BLUE, Color.RED, Color.MAGENTA, Color.GREEN, Color.GRAY, Color.PINK };
            for (int i = 0; i < colors.length; i++) {
                if (("Player " + Integer.toString(i + 1)).equals(owner)) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(colors[i]);
                }
            }
        }
        return c;
    }
}
