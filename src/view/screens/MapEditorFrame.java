package view.screens;

import model.DropDownModel;
import view.panels.EditMapControlPanel;
import view.panels.EditMapTable;

import javax.swing.*;
import java.awt.*;

public class MapEditorFrame extends JFrame {
    private JPanel contentPane;
    private EditMapTable editMapTable;
    private EditMapControlPanel editMapControlPanel;
    
    private static final String TITLE = "Map Editor";
    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private static final int GRIDLAYOUT_ROWS = 1;
    private static final int GRIDLAYOUT_COLS = 2;
    
    /* Constructors */
    public MapEditorFrame() {
        /* Setup main container */
        contentPane = new JPanel(new GridLayout(GRIDLAYOUT_ROWS, GRIDLAYOUT_COLS));
        contentPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setContentPane(contentPane);
        
        /* Setup table area */
        editMapTable = new EditMapTable();
        contentPane.add(new JScrollPane(editMapTable));
        
        /* Setup control panel area */
        editMapControlPanel = new EditMapControlPanel();
        contentPane.add(editMapControlPanel);
        
        /* Setup & Display frame */
        display();
    }
    
    /* Getters & Setters */
    @Override
    public JPanel getContentPane() {
        return contentPane;
    }
    
    public EditMapTable getEditMapTable() {
        return editMapTable;
    }
    
    public EditMapControlPanel getEditMapControlPanel() {
        return editMapControlPanel;
    }
    
    /* Public methods */
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
    
    public void loadMapsList(DropDownModel dropDownModel) {
        editMapControlPanel.getChooseMapDropdown().setModel(dropDownModel);
    }
    
    /* Private methods */
    private void display() {
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
