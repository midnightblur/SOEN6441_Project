/*
  @file  GamePlayFrame.java
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package view.screens;

import model.ui_models.GamePlayModel;
import view.helpers.UIHelper;
import view.ui_components.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.setDivider;

/**
 * The Class GamePlayFrame.
 */
public class GamePlayFrame extends JFrame implements Observer {
    
    /** The Constant TITLE. */
    // region Attributes declaration
    private static final String TITLE = "Game Play";
    
    /** The Constant WIDTH. */
    private static final int WIDTH = 1600;
    
    /** The Constant HEIGHT. */
    private static final int HEIGHT = 800;
    
    /** The content pane. */
    private JSplitPane contentPane;
    
    /** The game map table. */
    private GameMapTable gameMapTable;
    
    /** The control area. */
    private JPanel controlArea;
    
    /** The game setup panel. */
    private GameSetupPanel gameSetupPanel;
    
    /** The startup panel. */
    private StartupPanel startupPanel;
    
    /** The reinforcement panel. */
    private ReinforcementPanel reinforcementPanel;
    
    /** The fortification panel. */
    private FortificationPanel fortificationPanel;
    // endregion
    
    /**
     * Instantiates a new game play frame.
     */
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
    
    /* (non-Javadoc)
     * @see javax.swing.JFrame#getContentPane()
     */
    // region Getters & Setters
    @Override
    public JSplitPane getContentPane() {
        return contentPane;
    }
    
    /**
     * Gets the game map table.
     *
     * @return the game map table
     */
    public GameMapTable getGameMapTable() {
        return gameMapTable;
    }
    
    /**
     * Gets the game setup panel.
     *
     * @return the game setup panel
     */
    public GameSetupPanel getGameSetupPanel() {
        return gameSetupPanel;
    }
    
    /**
     * Gets the startup panel.
     *
     * @return the startup panel
     */
    public StartupPanel getStartupPanel() {
        return startupPanel;
    }
    
    /**
     * Gets the reinforcement panel.
     *
     * @return the reinforcement panel
     */
    public ReinforcementPanel getReinforcementPanel() {
        return reinforcementPanel;
    }
    
    /**
     * Gets the fortification panel.
     *
     * @return the fortification panel
     */
    public FortificationPanel getFortificationPanel() {
        return fortificationPanel;
    }
    
    // endregion
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    // region Public methods
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            gameMapTable.setModel(gamePlayModel.getMapTableModel().getModel());
            
            CardLayout cardLayout = (CardLayout) controlArea.getLayout();
            switch (gamePlayModel.getGameState()) {
                case SETUP:
                    cardLayout.show(controlArea, GameSetupPanel.class.getName());
                    break;
                case STARTUP:
                    cardLayout.show(controlArea, StartupPanel.class.getName());
                    break;
                case REINFORCEMENT:
                    cardLayout.show(controlArea, ReinforcementPanel.class.getName());
                    break;
                case ATTACKING:
//                    cardLayout.show(controlArea, AttackPanel.class.getName());
                    break;
                case FORTIFICATION:
                    cardLayout.show(controlArea, FortificationPanel.class.getName());
                    break;
                default:
                    break;
            }
        }
    }
    // endregion
    
    /**
     * Setup content pane layout.
     */
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
    
    /**
     * Setup control area.
     */
    private void setupControlArea() {
        /* Use CardLayout to let different panels share the same space */
        controlArea = new JPanel(new CardLayout());
        
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
