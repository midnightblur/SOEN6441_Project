package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ScrollableTable extends JScrollPane {
    private JTable table;
    
    public ScrollableTable() {
        this.table = getTable();
        this.table.setAutoCreateRowSorter(false);
        this.add(table);
    }
    
    private static JTable getTable() {
        
        return new JTable() {
            
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                String continent = (String) getValueAt(row, 0);
                String owner = (String) getValueAt(row, 0);
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
}
