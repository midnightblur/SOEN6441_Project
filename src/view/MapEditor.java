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
    private JMenuBar menuBar;
    private JSplitPane mainSplit;
    private JPanel controlArea = new JPanel(new GridLayout(20, 1, 5, 5));
    private JScrollPane scrollPane;
    private JPanel mainPane = new JPanel(new GridLayout(0, 1));
    private JLabel pathLabel = new JLabel("Chose map: ");
    public JComboBox mapsDropdown = new JComboBox();
    private JButton loadMap = new JButton("Load Map");
    private JTable myTable = makeTable();    // gets a table that changes row colors depending on cell content
    
    public MapEditor() {
        
        JMenu mnNewMenu = new JMenu("Map");
        menuBar.add(mnNewMenu);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Load Map");
        mnNewMenu.add(mntmNewMenuItem);
        
        JMenuItem mntmNewMenuItem_2 = new JMenuItem("New Map");
        mnNewMenu.add(mntmNewMenuItem_2);
        
        JMenuItem mntmNewMenuItem_3 = new JMenuItem("Edit Map");
        mnNewMenu.add(mntmNewMenuItem_3);
        
        JMenuItem mntmNewMenuItem_4 = new JMenuItem("Save Map");
        mnNewMenu.add(mntmNewMenuItem_4);
        
        JMenu mnNewMenu_1 = new JMenu("Player");
        menuBar.add(mnNewMenu_1);
        
        JMenuItem mntmNewMenuItem_5 = new JMenuItem("New menu item");
        mnNewMenu_1.add(mntmNewMenuItem_5);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
        mnNewMenu_1.add(mntmNewMenuItem_1);
        
        JMenu mnNewMenu_2 = new JMenu("Play");
        menuBar.add(mnNewMenu_2);
        
        JMenuItem mntmNewMenuItem_6 = new JMenuItem("New menu item");
        mnNewMenu_2.add(mntmNewMenuItem_6);
        
        JMenu mnNewMenu_3 = new JMenu("Statics");
        menuBar.add(mnNewMenu_3);
        
        JMenuItem mntmNewMenuItem_7 = new JMenuItem("New menu item");
        mnNewMenu_3.add(mntmNewMenuItem_7);
        /* put together the elements */
        myTable.setAutoCreateRowSorter(false);
        scrollPane.add(myTable);
        
        controlArea.add(pathLabel);
        controlArea.add(mapsDropdown);
        controlArea.add(loadMap);
        controlArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        mainSplit.setResizeWeight(.85d);
        mainSplit.setDividerLocation(1200);
        mainSplit.setRightComponent(controlArea);
        mainSplit.setLeftComponent(new JScrollPane(myTable));
        
        mainPane.add(mainSplit);
        
        this.display();
    }
    
    private void display() {
        JFrame myFrame = new JFrame("Map Editor");
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setJMenuBar(menuBar);
        myFrame.add(mainPane);
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
    
    public JTable getMyTable() {
        return myTable;
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
    public void resizeColumns(JTable table) {
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
    
    /* change color depending on continent and player ID */
    private static final int CONTINENT_COL = 0;
    private static final int OWNER_COL = 3;
    
    private static JTable makeTable() {
        
        return new JTable() {
            
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                String continent = (String) getValueAt(row, CONTINENT_COL);
                String owner = (String) getValueAt(row, OWNER_COL);
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