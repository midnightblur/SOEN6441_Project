package view.screens;

import model.ui_models.GamePlayModel;
import model.ui_models.MapTableModel;
import view.helpers.UIHelper;
import view.ui_components.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.setDivider;

public class GamePlayFrame extends JFrame implements Observer {
    // region Attributes declaration
    private static final String TITLE = "Game Play";
    private static final int WIDTH = 1600;
    private static final int HEIGHT = 800;
    
    private JSplitPane contentPane;
    private GameMapTable gameMapTable;
    private GameSetupPanel gameSetupPanel;
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
        gameMapTable = new GameMapTable();
        contentPane.setLeftComponent(new JScrollPane(gameMapTable));
        
        /* Setup control area */
        setupControlArea();
        
        /* Setup & Display frame */
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    // endregion
    
    // region Getters & Setters
    @Override
    public JSplitPane getContentPane() {
        return contentPane;
    }
    
    public GameMapTable getGameMapTable() {
        return gameMapTable;
    }
    
    public GameSetupPanel getGameSetupPanel() {
        return gameSetupPanel;
    }
    
    public StartupPanel getStartupPanel() {
        return startupPanel;
    }
    
    public ReinforcementPanel getReinforcementPanel() {
        return reinforcementPanel;
    }
    
    public FortificationPanel getFortificationPanel() {
        return fortificationPanel;
    }
    
    // endregion
    
    // region Public methods
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapTableModel) {
            gameMapTable.setModel(((MapTableModel) o).getModel());
        }
        if (o instanceof GamePlayModel) {
            gameMapTable.setModel(((GamePlayModel) o).getMapTableModel().getModel());
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
        
        gameSetupPanel = new GameSetupPanel();
        controlArea.add(gameSetupPanel, GameSetupPanel.class.getName());
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
