package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class Table extends JFrame{
    protected JTable table;

    protected MyTableModel tableModel;

    public Table() {
        super("Stocks Table");
        setSize(900, 800);

        tableModel = new MyTableModel();

        table = new JTable();
        table.setModel(tableModel);

        DefaultTableCellRenderer renderer = new ColoredTableCellRenderer();
        TableColumn column = new TableColumn(0,190, renderer, null);
        table.addColumn(column);

        JScrollPane ps = new JScrollPane();
        ps.getViewport().add(table);
        getContentPane().add(ps, BorderLayout.CENTER);

        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        addWindowListener(wndCloser);
        setVisible(true);
    }

    public static void main(String argv[]) {
        new Table();
    }
}

class MyTableModel extends AbstractTableModel {
    protected Vector vector;

    protected int columnsCount = 1;

    public MyTableModel() {
        vector = new Vector();
        vector.addElement("2146400");
        vector.addElement("554000");
        vector.addElement("24230");
    }

    public int getRowCount() {
        return vector == null ? 0 : vector.size();
    }

    public int getColumnCount() {
        return columnsCount;
    }

    public String getColumnName(int column) {
        return "data";
    }

    public boolean isCellEditable(int nRow, int nCol) {
        return false;
    }

    public Object getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow >= getRowCount())
            return "";
        return vector.elementAt(nRow);
    }

    public String getTitle() {
        return "data ";
    }

}

class ColoredTableCellRenderer extends DefaultTableCellRenderer {
    public void setValue(Object value) {
        // apply user's color
        setForeground(Color.red);
        super.setValue(value);
    }
}