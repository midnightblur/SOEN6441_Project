package view.screens;

import model.ui_models.MapTableModel;
import view.ui_components.FortificationPanel;
import view.ui_components.GameMapTable;
import view.ui_components.ReinforcementPanel;
import view.ui_components.StartupPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.displayJFrame;
import static view.helpers.UIHelper.setDivider;

public class GamePlayFrame extends JFrame implements Observer {
    // region Attributes declaration
    private static final String TITLE = "Game Play";
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 800;
    
    private JSplitPane contentPane;
    private GameMapTable gameGameMapTable;
    private StartupPanel startupPanel;
    private ReinforcementPanel reinforcementPanel;
    private FortificationPanel fortificationPanel;
    // endregion
    
    // region Constructors
    public GamePlayFrame() {
        /* Setup main container */
        setupContentPaneLayout();
        setContentPane(contentPane);
        
        /* Setup table area */
        gameGameMapTable = new GameMapTable();
        contentPane.setLeftComponent(new JScrollPane(gameGameMapTable));
        
        /* Setup control area */
        setupControlArea();
        
        /* Setup & Display frame */
        displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    // endregion
    
    // region Getters & Setters
    @Override
    public JSplitPane getContentPane() {
        return contentPane;
    }
    
    public GameMapTable getGameGameMapTable() {
        return gameGameMapTable;
    }
    // endregion
    
    // region Public methods
    
    /**
     * Displays messages on UI
     *
     * @param message message string
     */
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapTableModel) {
            gameGameMapTable.setModel(((MapTableModel) o).getModel());
        }
    }
    // endregion
    
    // region Private methods
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
    
    private void setupControlArea() {
        JPanel controlArea = new JPanel(new CardLayout());
        
        startupPanel = new StartupPanel();
        controlArea.add(startupPanel, StartupPanel.class.getName());
        reinforcementPanel = new ReinforcementPanel();
        controlArea.add(reinforcementPanel, ReinforcementPanel.class.getName());
        fortificationPanel = new FortificationPanel();
        controlArea.add(fortificationPanel, FortificationPanel.class.getName());
        
        contentPane.setRightComponent(controlArea);
    }
    // endregion
}
