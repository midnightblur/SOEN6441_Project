package view;

import model.MapTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

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
    private JMenu mapMenu = new JMenu("Map");
    private JMenuItem newMap = new JMenuItem("New Map");
    private JMenuItem saveMap = new JMenuItem("Save Map");
    private JMenu statisticsMenu = new JMenu("Statistics");
    private JMenuItem viewStatistics = new JMenuItem("New menu item");
    
    /**
     * Constructor making a new MapEditor view
     */
    public MapEditor() {
        
        menuBar.add(mapMenu);
        mapMenu.add(newMap);
        mapMenu.add(saveMap);
        menuBar.add(statisticsMenu);
        statisticsMenu.add(viewStatistics);
        
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
    
    /**
     * Displays the frame
     */
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
    
    public JTable getMyTable() {
        return myTable;
    }
    
    public JMenuItem getSaveMap() {
        return saveMap;
    }
    
    public JButton getLoadMap() {
        return loadMap;
    }
    
    /* Public methods */
    
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
     * Open a popup that contains the error message passed
     *
     * @param errorMessage
     */
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
    
    /**
     * Changes the model when the model notifies its subscribers
     *
     * @param o   the observable model
     * @param arg additional arguments (not used)
     */
    @Override
    public void update(Observable o, Object arg) {
        myTable.setModel(((MapTableModel) o).getModel());
        resizeColumns(myTable);
    }
    
    /**
     * Makes a new table
     * Overrides the super class so that it colors the continent headers
     * as well as each player's territories
     *
     * Overrides the super class and makes the cells not editable
     *
     * @return a new table
     */
    
    private static JTable makeTable() {
        
        return new JTable() {
            
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
        };
    }
}