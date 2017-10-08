package view.Panels;

import model.MapEditorModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Observable;

public class MapTablePanel extends TablePanel {
    /* Constructors */
    public MapTablePanel() {
        table = buildTable();
        table.setAutoCreateRowSorter(false);
    }
    
    private static JTable buildTable() {
        
        return new JTable() {
            
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                String continent = (String) getValueAt(row, 0);
                String owner = (String) getValueAt(row, 3);
                if (!"  ".equals(continent)) {
                    c.setFont(new Font("Sans Serif", Font.ITALIC, 14));
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(super.getBackground());
                    c.setForeground(super.getForeground());
                }
                
                Color[] colors = { Color.BLUE, Color.RED, Color.MAGENTA, Color.GREEN, Color.GRAY, Color.PINK };
                for (int i = 0; i < colors.length; i++) {
                    if (("Player " + Integer.toString(i + 1)).equals(owner)) {
                        c.setBackground(Color.WHITE);
                        c.setForeground(colors[i]);
                    }
                }
                
                return c;
            }
        };
    }
    
    @Override
    public void update(Observable o, Object arg) {
        updateTable(((MapEditorModel) o).getMapTableModel().getModel());
    }
}
