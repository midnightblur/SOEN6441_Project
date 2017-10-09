package view.screens;

import model.ui_models.DropDownModel;
import view.helpers.UIHelper;
import view.ui_components.EditMapControlPanel;
import view.ui_components.EditMapTable;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class MapEditorFrame extends JFrame implements Observer {
    private static final String TITLE = "Map Editor";
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 800;
    
    private JSplitPane contentPane;
    private EditMapTable editMapTable;
    private EditMapControlPanel editMapControlPanel;
    
    /* Constructors */
    public MapEditorFrame() {
        /* Setup main container */
        setupContentPaneLayout();
        setContentPane(contentPane);
        
        /* Setup table area */
        editMapTable = new EditMapTable();
        contentPane.setLeftComponent(new JScrollPane(editMapTable));
        
        /* Setup control panel area */
        editMapControlPanel = new EditMapControlPanel();
        contentPane.setRightComponent(editMapControlPanel);
        
        /* Setup & Display frame */
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    
    /* Getters & Setters */
    @Override
    public JSplitPane getContentPane() {
        return contentPane;
    }
    
    public EditMapTable getEditMapTable() {
        return editMapTable;
    }
    
    public EditMapControlPanel getEditMapControlPanel() {
        return editMapControlPanel;
    }
    
    /* Public methods */
    
    /**
     * Displays error messages on UI
     *
     * @param errorMessage Error message string
     */
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
    
    /**
     *
     * @param dropDownModel
     */
    public void loadMapsList(DropDownModel dropDownModel) {
    }
    
    /* Private methods */
    
    private void setupContentPaneLayout() {
        contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {
            private final int location = 1100;
            
            {
                setDividerLocation(location);
            }
            
            @Override
            public int getDividerLocation() {
                return location;
            }
            
            @Override
            public int getLastDividerLocation() {
                return location;
            }
        };
        contentPane.setDividerLocation(1100);
        contentPane.setResizeWeight(.75d);
    }
    
    @Override
    public void update(Observable o, Object arg) {
//        if (o instanceof DropDownModel)
    }
}
