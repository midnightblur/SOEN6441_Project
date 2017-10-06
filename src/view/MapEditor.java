package view;

import model.MapTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class MapEditor extends JFrame implements Observer {
    private JPanel menuPanel = new JPanel();
    
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(), new JPanel());
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel controlPane = new JPanel();
    private JLabel pathLabel = new JLabel("Map location: ");
    private JTextField path = new JTextField("World.map");
    private JButton loadMap = new JButton("Load Map");
    private JTable myTable = new JTable();
    
    public MapEditor() {
        
        /* put together the elements */
        controlPane.add(pathLabel);
        controlPane.add(path);
        controlPane.add(loadMap);
        scrollPane.add(myTable);
        splitPane.setRightComponent(controlPane);
        splitPane.setLeftComponent(new JScrollPane(myTable));
        splitPane.setSize(1600, 900);
        myTable.setFont(new Font("Verdana", Font.PLAIN, 20));
        myTable.setAutoCreateRowSorter(true);
        myTable.setRowHeight(24);
        myTable.setBackground(Color.orange);
        myTable.setForeground(Color.blue);
        
        this.display();
    }
    
    private void display() {
        JFrame myFrame = new JFrame("Map Editor");
        myFrame.add(menuPanel);
        myFrame.add(splitPane);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
    
    public void setModel(DefaultTableModel tableModel) {
        myTable.setModel(tableModel);
    }
    
    public String getPath() {
        return path.getText();
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
