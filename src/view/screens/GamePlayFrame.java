package view.screens;

import model.ui_models.MapTableModel;
import view.ui_components.MapTable;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.displayJFrame;
import static view.helpers.UIHelper.setDivider;

public class GamePlayFrame extends JFrame implements Observer {
    private static final String TITLE = "Game Play";
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 800;
    
    private JSplitPane contentPane;
    private MapTable gameMapTable;
    
    /* Constructors */
    public GamePlayFrame() {
        /* Setup main container */
        setupContentPaneLayout();
        setContentPane(contentPane);
        
        /* Setup table area */
        gameMapTable = new MapTable();
        contentPane.setLeftComponent(new JScrollPane(gameMapTable));
        
        /* Setup & Display frame */
        displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    
    /* Getters & Setters */
    @Override
    public JSplitPane getContentPane() {
        return contentPane;
    }
    
    public MapTable getGameMapTable() {
        return gameMapTable;
    }
    
    /* Public methods */
    
    /**
     * Displays messages on UI
     *
     * @param message message string
     */
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
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
        setDivider(contentPane);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        gameMapTable.setModel(((MapTableModel) o).getModel());
    }
}
