package view;

import model.DropDownModel;
import model.MapTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static util.Config.DEFAULT_MAP;

public class MapEditor extends JFrame implements Observer {
    private JPanel menuPanel = new JPanel();
    
    private JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(), new JPanel());
    private JSplitPane controlSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), new JPanel(new GridLayout(0, 1)));
    
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel controlPane = new JPanel(new GridLayout(0, 1));
    
    private JLabel pathLabel = new JLabel("Chose map: ");
    public JComboBox mapsDropdown = new JComboBox();
    private JButton loadMap = new JButton("Load Map");
    private JTable myTable = getTable();    // gets a table that changes row colors depending on cell content
    
    public MapEditor() {
        /* put together the elements */
        controlPane.add(pathLabel);
        controlPane.add(mapsDropdown);
        controlPane.add(loadMap);
        myTable.setAutoCreateRowSorter(false);
        scrollPane.add(myTable);
        
        mainSplit.setResizeWeight(.85d);
        mainSplit.setDividerLocation(1200);
        mainSplit.setRightComponent(controlSplit);
        mainSplit.setLeftComponent(new JScrollPane(myTable));
        mainSplit.setSize(1600, 900);
        
        controlSplit.setTopComponent(controlPane);
        controlSplit.setBorder(new EmptyBorder(10, 10, 10, 10));
        controlSplit.setDividerLocation(200);
        
        this.display();
    }
    
    private void display() {
        JFrame myFrame = new JFrame("Map Editor");
        myFrame.add(menuPanel);
        myFrame.add(mainSplit);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setSize(1600, 1200);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
    
    /* Getters and Setters */
    public String getMap() {
        return mapsDropdown.getSelectedItem().toString();
    }
    
    /**
     * Sets the table model (data source)
     *
     * @param tableModel
     */
    public void setTableModel(DefaultTableModel tableModel) {
        myTable.setModel(tableModel);
        resizeColumns(myTable);
    }
    
    public void setDropdownModel(DropDownModel dropdownModel) {
        mapsDropdown.setModel(dropdownModel);
        mapsDropdown.setSelectedItem(DEFAULT_MAP);
    }
    
    /**
     * Resize the table columns to fit
     *
     * @param table
     */
    private void resizeColumns(JTable table) {
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
    
    /**
     * Inform controller of changes
     *
     * @param
     */
    public void addActionListener(ActionListener MapEditorController) {
        loadMap.addActionListener(MapEditorController);
    }
    
    /**
     * Open a popup that contains the error message passed
     *
     * @param errorMessage
     */
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        setTableModel(((MapTableModel) o).getModel());
    }
    
    /* change color depending on owner */
    private static final int STATUS_COL = 0;
    
    private static JTable getTable() {
        
        return new JTable() {
            
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                String status = (String) getValueAt(row, STATUS_COL);
                if (!"  ".equals(status)) {
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(super.getBackground());
                    c.setForeground(super.getForeground());
                }
                return c;
            }
        };
    }
}