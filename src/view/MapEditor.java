package view;

import model.MapTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class MapEditor extends JFrame implements Observer {
    private JPanel menuPanel = new JPanel();
    
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(), new JPanel());
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel controlPane = new JPanel();
    private JLabel pathLabel = new JLabel("Chose map: ");
    private JTextField path = new JTextField("World.map");
    private JButton loadMap = new JButton("Load Map");
    private JTable myTable = new JTable();
    
    public MapEditor() {
        
        /* put together the elements */
        controlPane.add(pathLabel);
        controlPane.add(path);
        controlPane.add(loadMap);
        scrollPane.add(myTable);
        splitPane.setResizeWeight(.85d);
        splitPane.setDividerLocation(1200);
        splitPane.setRightComponent(controlPane);
        splitPane.setLeftComponent(new JScrollPane(myTable));
        splitPane.setSize(1600, 900);
        myTable.setAutoCreateRowSorter(false);
        
        this.display();
    }
    
    private void display() {
        JFrame myFrame = new JFrame("Map Editor");
        myFrame.add(menuPanel);
        myFrame.add(splitPane);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setSize(1600, 1200);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
    
    /* Getters and Setters */
    public String getPath() {
        return path.getText();
    }
    
    /**
     * Sets the table model (data source)
     *
     * @param tableModel
     */
    public void setModel(DefaultTableModel tableModel) {
        myTable.setModel(tableModel);
        resizeColumns(myTable);
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
     * When the button is clicked execute a method in the Controller named loadGameMap
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
        myTable.setModel(((MapTableModel) o).getModel());
    }
    
}
